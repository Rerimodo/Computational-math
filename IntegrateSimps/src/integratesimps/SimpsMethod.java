package integratesimps;

public class SimpsMethod {
    Func function;
    double highLim, lowLim, accuracy;
    double integral_n, integral_2n, finalError, result=0;
    int divNumber;
    
    public SimpsMethod(double low, double high, double acc, Func func){
        lowLim = low;
        highLim = high;
        accuracy = acc;
        function = func;
    }
    
    public double Integrate(int n){
        double sum=0;
        double h = (highLim-lowLim)/n; //вычисление размера шага
        for(int i = 1;i<n;i++){
            sum += 4*function.getValue(lowLim + i * h);
            i++;
            sum += 2*function.getValue(lowLim + i * h);
        }
        sum = (sum + function.getValue(lowLim) - function.getValue(highLim))*h/3;
        return sum;
    }
    
    public void Solve(){
        if(highLim != lowLim){
            for(int n = 2;n <= 10000;n += 2){
                integral_n = Integrate(n);// вычисление интеграла с количеством шагов = n
                integral_2n = Integrate(2*n);// вычисление интеграла с количеством шагов = 2n
                if(Math.abs(integral_2n-integral_n)/15 < accuracy){//оценка Рунге
                    result = integral_2n;
                    divNumber = n;
                    finalError = Math.abs(integral_2n-integral_n)/15;
                    break;
                }
                if(n == 10000){
                    System.out.println("Заданная точность не достигнута.");
                    divNumber = 0;
                }
            }
        }
        else {
            System.out.println("Пределы интегрирования совпадают, результат равен 0.");
            divNumber = 0;
        }
    }
    
    public double getResult(){
        return result;
    }
    
    public int getDivNum(){
        return divNumber;
    }
    
    public double getError(){
        return finalError;
    }
    
}
