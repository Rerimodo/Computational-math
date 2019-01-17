package interplagr;

public class Main {

    public final static double CHANGE = 2;

    public static void main(String[] args) {
//        Func function = (arg) -> Math.pow(Math.E, arg);
        Func function = (arg) -> Math.pow(arg, 2);
        UserInterface userInterface = new UserInterface(function);
        userInterface.draw(700, 900);
    }
}