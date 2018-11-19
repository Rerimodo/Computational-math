//
//package gaussmethod;
//
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.io.UnsupportedEncodingException;
//import java.util.Hashtable;
//import java.util.Random;
//import java.util.Scanner;
//
//public class Main {
////    private static Gauss g;
//    public static void main(String args[]){
//        
//        Gauss g = new Gauss();
////        System.out.println(g);
////        System.out.println("Determinant is " + g.determinant());
////        if(!g.triangulate()){
////            System.out.println("No solution!");
////            return;
////        }
////        System.out.println(g);
////        double[] sol = g.solve();
////        double[] err = g.errors();
////        for(int i = 0; i < sol.length; i++){
////            System.out.println("X" + i + " = " + sol[i] + "\tError is " + err[i]);
////        }
//        
//        (new Thread(){
//            @Override
//            public void run(){parseCommands(g);}
//        }).start();
//    }
//    
//    public static void parseCommands(Gauss g){
//        Scanner scanner = new Scanner(System.in);
//        for(;;){
//            System.out.println("Enter the command");
//            String lineRaw = scanner.nextLine();
//            lineRaw = lineRaw.trim().toLowerCase();
//            String line = lineRaw;
//            if(lineRaw.indexOf(' ')>0){
//                line = lineRaw.substring(0, lineRaw.indexOf(' '));
//            }
//            if(line.equals("size")){ // установка нового размера и создание пустой матрицы
//                try{
//                    String argument = lineRaw.substring(5);
//                    int size = Integer.parseInt(argument);
//                    if (size > 20) size = 20;
//                    g.setSize(size);
//                    System.out.println("");
//                }catch(Exception e) {System.out.println("Invalid argument!");}
//            }
//            else if(line.equals("random")){ //заполнение матрицы случайными значениями
//                g.randomize();
//            }
//            else if(line.equals("show")){ //выведение заполненной нетреугольной матрицы
//                try{
//                    System.out.println(g.showOriginal());
//                }catch(Exception e){System.out.println("Matrix is unfilled!");}
//            }
//            else if(line.equals("usefile")){ //использование матрицы из файла
//                try{
//                    String path = lineRaw.substring(8);
//                    g.useSizeFile(path, "UTF-8");
//                    System.out.println("");
//                }catch(Exception e) {System.out.println("Invalid path!");}
//            }
//            else if(line.equals("determinant")){ //выведение детерминанта на экран
//                System.out.println("Determinant is " + g.determinant());
//            }
//            else if(line.equals("solve")){ //решение матрицы
//                if(g.triangulate()){
//                    try{
//                        System.out.println(g);
//                        double[] sol = g.solve();
//                        double[] err = g.errors();
//                        for(int i = 0; i < sol.length; i++){
////                            System.out.println("X" + i + " = " + sol[i] + "\tError is " + err[i]);
//                            System.out.printf("X%d = %2$.10f \tError is %2$+.10f\n",i,sol[i],err[i]);
//                        }
//                    } catch(Exception e){
//                        System.out.println("Matrix is unfilled!");
//                    }
//                }else{
//                    System.out.println("No solution!");
//                }
//            }
//            else if(line.equals("q")){ //завершение работы программы
//                System.exit(0);
//            }
//            else{
//                System.out.println("Invalid command");
//                System.out.println("");
//            }
//        }
//    }
//    
////    public static void modeSelect(){
////        Scanner scanner = new Scanner(System.in);
////        System.out.println("Выберите способ ввода данных: 1 - файл; 2 - консоль");
////        try{
////            if(scanner.nextInt()==1){
////
////            }
////            else if(scanner.nextInt()==2){
////
////            }
////            else{throw new Exception();}
////        }catch(Exception e){
////            System.out.println("Неправильный способ!");
//////            modeSelect();
////        }
////    }
//}
//
