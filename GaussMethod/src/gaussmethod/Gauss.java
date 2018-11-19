package gaussmethod;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Random;
import java.util.Scanner;

public class Gauss {
    double [][] matrix, temp;
    double [] solutions, error;
    int size;
    
    public Gauss(){
        size = 1;
        matrix = new double[size+1][size];
//        original = new double[size+1][size];
        solutions = new double[size];
        error = new double[size];
        temp = new double[size+1][];
    }
    
    public Gauss(double [][] matrix){
        this.matrix = matrix;
//        original = matrix;
        size = matrix.length-1;
        solutions = new double[size];
        error = new double[size];
        temp = new double[size+1][];
        for(int i = 0; i < size + 1; i++) temp[i] = matrix[i].clone();
        
    }
    
    private double determinant(boolean excludedX[], int yLeft){
        if(yLeft == size)
            return 1;
        double res = 0;
        for(int i = 0; i < size; i++){
            if(!excludedX[i]){
                excludedX[i] = true;
                res += matrix[i][yLeft] * (i + yLeft % 2 == 0 ? 1 : -1) * determinant(excludedX, yLeft+1);
                excludedX[i] = false;
            }
        }
        return res;
    }
    
    public double determinant(){
        boolean exX[] = new boolean[size];
        return determinant(exX, 0);
    }
    
    public boolean triangulate(){
        for(int startPos = 0; startPos < size; startPos++){
            boolean found = false;
            for(int offset = startPos; offset < size; offset++){
                if(matrix[startPos][offset]!=0){
                    found = true;
                    swap(startPos,offset);
                    break;
                }
            }
            if(!found) return false;
            for(int i = startPos + 1; i<size; i++){
                double coef = matrix[startPos][i]/matrix[startPos][startPos];
                matrix[startPos][i] = 0;
                for(int j = startPos + 1; j < size+1; j++){
                    matrix[j][i]-=matrix[j][startPos]*coef;
                }
            }
        }
        return true;
    }
    
    private void swap(int a, int b){
        for(int i = 0; i < size+1; i++){
            double t = matrix[i][a];
            matrix[i][a] = matrix[i][b];
            matrix[i][b] = t;
        }
    }
    
    public double[] solve(){
        for(int startPos = size - 1; startPos >= 0; startPos--){
            solutions[startPos] = matrix[size][startPos]/matrix[startPos][startPos];
            for(int i = startPos - 1; i >= 0; i--){
                double coef = matrix[startPos][i];
                matrix[size][i]-=solutions[startPos]*coef;
            }
        }
        return solutions;
    }
    
    public double[] errors(){
        for(int y = 0; y < size; y++){
            double cur = 0;
            for(int x = 0; x < size; x++){
                cur+=temp[x][y]*solutions[x];
            }
            error[y] = temp[size][y] - cur;
        }
        return error;
    }
    
    public void randomize(){
        matrix=new double[size+1][size];
        Random r = new Random();
        for(int i=0;i<=size;i++){
            for(int j=0;j<=size-1;j++){
                double randomValue = 0.00005 + (100.0 - 0.00005) * r.nextDouble();
                setElement(i,j,randomValue);
            }
        }
        solutions = new double[size];
        error = new double[size];
        temp = new double[size+1][];
        for(int i = 0; i < size + 1; i++) temp[i] = matrix[i].clone();
    }
    
    public void setSize(int s){
        this.size = s;
        matrix=new double[size+1][size];
//        original=new double[size+1][size];
        for(int i=0;i<=size;i++){
            for(int j=0;j<=size-1;j++){
                setElement(i,j,0.0);
            }
        }
        solutions = new double[size];
        error = new double[size];
        temp = new double[size+1][];
        for(int i = 0; i < size + 1; i++) temp[i] = matrix[i].clone();
    }
    
    public int getSize(){return this.size;}
    
    public void useSizeFile(String path, String encoding){
        StringBuilder sb = new StringBuilder();
        try(InputStream inputStream = new FileInputStream(path)){
            boolean sizeNotSet = true;
            int j=0;
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, encoding);
            while(inputStreamReader.ready()){
                char nextChar = (char) inputStreamReader.read();
                if(nextChar == '\r') continue;
                if(nextChar != '\n'){sb.append(nextChar);}
                else {
                    if(sizeNotSet){
                        String[] values = sb.toString().split(";");
                        sb.setLength(0);
                        size = Integer.parseInt(values[0]);
                        matrix=new double[size+1][size];
//                        original = new double[size+1][size];
                        solutions = new double[size];
                        error = new double[size];
                        temp = new double[size+1][];
                        sizeNotSet = false;
                    }else{
                        if(j<=size-1){
                            String[] values = sb.toString().split(" ");
                            for(int i=0;i<=size;i++){
                                    try{setElement(i,j,Double.parseDouble(values[i]));}
                                    catch(ArrayIndexOutOfBoundsException e){setElement(i,j,0.0);}
                            }
                            sb.setLength(0);
                            j++;
                        }
                    }
                }
            }
            for(int i = 0; i < size + 1; i++) temp[i] = matrix[i].clone();
            System.out.println("Файл загружен");
        }
        catch (FileNotFoundException fnfe){System.out.println("Файл не найден");}
        catch (UnsupportedEncodingException uee){System.out.println("Проблемы с кодировкой");}
        catch (IOException io){System.out.println("Ошибка ввода");}
    }
    
    public void setElement(int i, int j, double e){
        matrix[i][j]=e;
//        original[i][j]=e;
    }
    
    public void fill(int x, int y){
        Scanner scanner = new Scanner(System.in);
        for(int j = y; j < getSize(); j++){
            System.out.printf("Введите значения для строки №%d\n",j+1);
            for(int i = x; i < getSize()+1; i++){
                try{
                    if(i == getSize()){System.out.printf("Введите значение свободного члена №%d: ",j+1);
                    }else{System.out.printf("Введите значение %d элемента: ",i+1);}
                    String elem = scanner.nextLine();
                    elem = elem.trim();
                    setElement(i, j, Double.parseDouble(elem));
                }catch(Exception e){
                    System.out.println("Некорректное значение элемента");
                    fill(i, j);
                    break;
                }
            }
        }
    }
    
//    public String showOriginal(){
//        StringBuilder sb = new StringBuilder();
//        for(int y = 0; y < size; y++){
//            for(int x = 0; x < size+1; x++){
//                sb.append(original[x][y]);
//                sb.append('\t');
//            }
//            sb.append('\n');
//        }
//        return sb.toString();
//    }
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for(int y = 0; y < size; y++){
            for(int x = 0; x < size+1; x++){
                sb.append(String.format("%.15f",matrix[x][y]));
                sb.append('\t');
            }
            sb.append('\n');
        }
        return sb.toString();
    }
}
