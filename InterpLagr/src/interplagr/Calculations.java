package interplagr;

public class Calculations {

    private Func baseFunction;

    public Calculations(Func bf){
        this.baseFunction = bf;
    }

    public Func Interpolate(double[] args){
        int n = args.length;
        Func function = (x) -> {
            double res = 0;
            for (int i = 0; i < n; i++) {
                double pol = 1;
                for (int j = 0; j < n; j++) {
                    if (i != j) {
                        double a = x - args[j];
                        double b = args[i] - args[j];
                        pol *= a / b;
                    }
                }
                res += baseFunction.getValue(args[i]) * pol;
            }
            return res;
        };
        return function;
    }

}