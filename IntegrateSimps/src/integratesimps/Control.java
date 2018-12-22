package integratesimps;

import java.util.Scanner;

public class Control {
    private double highLim, lowLim, accuracy;
    private Func function;
    
    public void ChooseFunction(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Выберите функцию для интегрирования:\n1: y=x\n2: y=x^2\n3: y=1/x\n4: y=log(x)\n5: y=sin(x)");
        String line = scanner.nextLine().trim();
        try{
            switch(Integer.parseInt(line)){
                case 1: function = x -> x; 
                        break;
                case 2: function = x -> x*x;
                        break;
                case 3: function = x -> 1/x;
                        break;
                case 4: function = x -> Math.log(x);
                        break;
                case 5: function = x -> Math.sin(x);
                        break;
                default: System.out.println("Такой функции нет!");
                        ChooseFunction();
            }
        }catch(Exception e){
            System.out.println("Сделан неправильный выбор!");
            ChooseFunction();
        }
    }
    
    public void SetAccuracy(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите точность интегрирования");
        String line = scanner.nextLine().trim();
        try{
            accuracy = Double.parseDouble(line);
            if(accuracy == 0.0) throw new Exception();
        }
        catch(Exception e){
            System.out.println("Неправильно задана точность!");
            SetAccuracy();
        }
    }
    
    public void SetLimits(int mode){
        if(mode==1){
            Scanner scanner = new Scanner(System.in);
            System.out.println("Введите нижний предел интегрирования");
            String line = scanner.nextLine().trim();
            try{
                lowLim = Double.parseDouble(line);
                SetLimits(2); //вызываем метод для верхнего предела
            }catch(Exception e){
                System.out.println("Неправильно задан нижний предел!");
                SetLimits(mode);
            }
        }
        else{
            Scanner scanner = new Scanner(System.in);
            System.out.println("Введите верхний предел интегрирования");
            String line = scanner.nextLine().trim();
            try{
                highLim = Double.parseDouble(line);
                if(lowLim > highLim){
                    double temp = lowLim;
                    lowLim = highLim;
                    highLim = temp;
                }
            }catch(Exception e){
                System.out.println("Неправильно задан верхний предел!");
                SetLimits(mode);
            }
        }
    }
    
    public void showResults(SimpsMethod simps){
        System.out.println("Значение интеграла: " + simps.getResult() + "\n" +
                           "Количество разбиений: " + simps.getDivNum() + "\n" +
                           "Погрешность равна "+ simps.getError());
    }
    
    public double getAccur(){
        return accuracy;
    }
    public double getHighLim(){
        return highLim;
    }
    public double getLowLim(){
        return lowLim;
    }
    public Func getFunction(){
        return function;
    } 
    
}
