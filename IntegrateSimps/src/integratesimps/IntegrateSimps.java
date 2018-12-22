package integratesimps;

import java.util.Scanner;

public class IntegrateSimps {
    public static void main(String[] args) {
        Control control = new Control();
        control.ChooseFunction();
        control.SetAccuracy();
        control.SetLimits(1);
        SimpsMethod simps = new SimpsMethod(control.getLowLim(), 
                                control.getHighLim(), 
                                Math.abs(control.getAccur()), 
                                control.getFunction());
        simps.Solve();
        control.showResults(simps);
    }
}
