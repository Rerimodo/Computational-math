package interplagr;

import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.style.markers.SeriesMarkers;

import javax.swing.*;
import java.awt.*;

public class Graphing {
    private Func baseFunction;
    private Func function;
    private double[] xData;

    public Graphing(Func bf, Func f, double[] x){
        this.baseFunction = bf;
        this.function = f;
        this.xData = x;
    }


    public JPanel getChart(int width, int height, double changeX, double changeY) {
        XYChart chart = new XYChart(width, height);
        chart.getStyler().setXAxisMin(xData[0]);
        chart.getStyler().setXAxisMax(xData[xData.length - 1]);

        double[] yData = new double[xData.length];
        for (int i = 0; i < yData.length; i++)
            yData[i] = baseFunction.getValue(xData[i]);
        XYSeries points = chart.addSeries("Points", xData, yData);
        points.setMarker(SeriesMarkers.CIRCLE);
        points.setMarkerColor(Color.RED);
        points.setLineColor(Color.WHITE);

        XYSeries changeSeries = chart.addSeries("Измененное значение",
                new double[]{changeX}, new double[]{changeY});
        changeSeries.setMarker(SeriesMarkers.CIRCLE);
        changeSeries.setLineColor(Color.WHITE);
        changeSeries.setMarkerColor(Color.BLUE);


        double step = Math.abs(xData[xData.length - 1] - xData[0]) / width;
        double[] xGraphing = new double[width];
        double[] yBaseFunction = new double[width];
        double[] yPolynomial = new double[width];
        for (int i = 0; i < yBaseFunction.length; i++) {
            double arg = xData[0] + step * i;
            yBaseFunction[i] = baseFunction.getValue(arg);
            yPolynomial[i] = function.getValue(arg);
            xGraphing[i] = arg;
        }

        chart.addSeries("Исходная функция", xGraphing, yBaseFunction).setMarker(SeriesMarkers.NONE);
        chart.addSeries("Интерполированная функция", xGraphing, yPolynomial).
                setMarker(SeriesMarkers.NONE);

        return new XChartPanel<>(chart);
    }
}
