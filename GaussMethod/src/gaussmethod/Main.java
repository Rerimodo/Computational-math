package gaussmethod;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Hashtable;
import java.util.Random;
import java.util.Scanner;

public class Main {
//    private static Gauss g;
    public static void main(String args[]){
        Gauss g = new Gauss();
        modeSelect(g);
    }
    
    public static void modeSelect(Gauss g){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Выберите способ ввода данных: 1 - файл; 2 - консоль; 3 - случайная матрица");
        String line = scanner.nextLine();
        line = line.trim();
        try{
            int temp = Integer.parseInt(line);
            if(temp==1){
                fileMode(g);
            }
            else if(temp==2){
                consoleMode(g);
            }
            else if(temp==3){
                randomMode(g);
            }
            else{throw new Exception();}
        }catch(Exception e){
            System.out.println("Неправильный способ!");
            modeSelect(g);
        }
    }
    
    public static void fileMode(Gauss g){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Выберите полный путь до файла");
        String path = scanner.nextLine();
        path = path.trim();
        if(path.indexOf(' ')>0){
            path = path.substring(0, path.indexOf(' '));
        }
        g.useSizeFile(path, "UTF-8");
        System.out.println("Определитель равен " + g.determinant());
        if(g.triangulate()){
            try{
                System.out.println("\nТреугольная матрица\n"+g);
                double[] sol = g.solve();
                double[] err = g.errors();
                for(int i = 0; i < sol.length; i++){
//                            System.out.println("X" + i + " = " + sol[i] + "\tError is " + err[i]);
                    System.out.printf("X%1$d = %2$.10f \tПогрешность равна %3$.40f\n",i+1,sol[i],err[i]);
                }
            } catch(Exception e){
                System.out.println("Матрица не заполнена");
            }
        }else{
            System.out.println("Решения нет");
        }
    }
    
    public static void randomMode(Gauss g){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите размер матрицы");
        String size = scanner.nextLine();
        size = size.trim();
        
        try{
            g.setSize(Integer.parseInt(size));
        }catch(Exception e){
            System.out.println("Неправильно задан размер!");
            randomMode(g);
        }
        
        g.randomize();
        System.out.println("\nМатрица\n"+g);
        System.out.println("Определитель равен " + g.determinant() + "\n");
        if(g.triangulate()){
            try{
                System.out.println("\nТреугольная матрица\n"+g);
                double[] sol = g.solve();
                double[] err = g.errors();
                for(int i = 0; i < sol.length; i++){
//                            System.out.println("X" + i + " = " + sol[i] + "\tError is " + err[i]);
                    System.out.printf("X%1$d = %2$.10f \tПогрешность равна %3$.40f\n",i+1,sol[i],err[i]);
                }
            } catch(Exception e){
                System.out.println("Матрица не заполнена");
            }
        }else{
            System.out.println("Решения нет");
        }
    }
    
    public static void consoleMode(Gauss g){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите размер матрицы");
        String size = scanner.nextLine();
        size = size.trim();
        
        try{
            g.setSize(Integer.parseInt(size));
        }catch(Exception e){
            System.out.println("Неправильно задан размер!");
            consoleMode(g);
        }
        g.fill(0,0);
        
        System.out.println("\nМатрица\n"+g);
        System.out.println("Определитель равен " + g.determinant() + "\n");
        if(g.triangulate()){
            try{
                System.out.println("\nТреугольная матрица\n"+g);
                double[] sol = g.solve();
                double[] err = g.errors();
                for(int i = 0; i < sol.length; i++){
//                            System.out.println("X" + i + " = " + sol[i] + "\tError is " + err[i]);
                    System.out.printf("X%1$d = %2$.10f \tПогрешность равна %3$.40f\n",i+1,sol[i],err[i]);
                }
            } catch(Exception e){
                System.out.println("Матрица не заполнена");
            }
        }else{
            System.out.println("Решения нет");
        }
    }
    
}

