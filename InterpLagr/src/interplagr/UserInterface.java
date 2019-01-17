package interplagr;


import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

public class UserInterface {

    private Func baseFunction;

    private Func interpolateFunction;

    private JPanel graphicPanel;

    private JPanel argsRow;

    private double[] xData;


    public UserInterface(Func baseFunction) {
        this.baseFunction = baseFunction;
    }

    public JFrame getMainFrame(int width, int height) {
        final int firstXValue = 4;
        final int xAmount = 5;
        final int graphicHeight = height * 7 / 10;

        JFrame jFrame = new JFrame("Интерполяция Лагранжа");
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setSize(width, height);
        JPanel mainPanel = new JPanel(null);
        jFrame.setContentPane(mainPanel);

        graphicPanel = new JPanel();
        graphicPanel.setLocation(0, 0);
        graphicPanel.setSize(width, graphicHeight);
        mainPanel.add(graphicPanel);

        JPanel controlPanel = new JPanel(new GridLayout(5, 1));
        controlPanel.setSize(width, height - graphicHeight);
        controlPanel.setLocation(0, graphicHeight);
        mainPanel.add(controlPanel);

        JPanel selectPanel = new JPanel();
        controlPanel.add(selectPanel);
        JLabel labelAmount = new JLabel("Выберите количество узлов интерполирования");
        selectPanel.add(labelAmount);

        JComboBox<Integer> selectedXAmount = new JComboBox<>();
        for (int i = firstXValue; i < firstXValue + xAmount; i++)
            selectedXAmount.addItem(i);
        selectPanel.add(selectedXAmount);

        JPanel argsPanel = new JPanel();

        argsRow = generateButtons(firstXValue);
        argsPanel.add(argsRow);
        controlPanel.add(argsPanel);

        selectedXAmount.addActionListener(e -> {
            argsPanel.remove(argsRow);
            argsRow = generateButtons((int)selectedXAmount.getSelectedItem());
            argsPanel.add(argsRow);
            argsPanel.updateUI();
        });

        JPanel changePanel = new JPanel();
        controlPanel.add(changePanel);

        JLabel changeLabel = new JLabel("Введите узел, в котором нужно поменять значение функции:");
        changePanel.add(changeLabel);

        JTextField changeField = new JTextField(5);
        changePanel.add(changeField);

        JButton mainButton = new JButton("Интерполировать");
        mainButton.addActionListener(e -> {
            for (int i = 0; i < xAmount - 1; i++) {
                if (((JTextField) argsRow.getComponent(i)).getText().equals("")) {
                    JOptionPane.showMessageDialog(jFrame,
                            "Все точки должны быть заданы!",
                            "Ошибка",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            Arrays.sort(xData);
            double changeX;
            try {
                changeX = Double.parseDouble(changeField.getText());
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(jFrame,
                        "Изменяемая точка задана в неправильном формате!",
                        "Ошибка",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (changeX < xData[0] || changeX > xData[xData.length - 1]) {
                JOptionPane.showMessageDialog(jFrame,
                        "Изменяемая точка должна находиться между максимальным и минимальным значениями!",
                        "Ошибка",
                        JOptionPane.ERROR_MESSAGE);
                return;
            } else {
                mainPanel.remove(graphicPanel);
                graphicPanel = getGraphicPanel(width, graphicHeight, Double.parseDouble(changeField.getText()));
                mainPanel.add(graphicPanel);
                mainPanel.updateUI();
            }
        });
        controlPanel.add(mainButton);

        JPanel findValuePanel = new JPanel();
        controlPanel.add(findValuePanel);

        JLabel findValueLabel = new JLabel("Введите значение х, в котором нужно найти значение функции");
        findValuePanel.add(findValueLabel);

        JTextField findValueField = new JTextField(3);
        findValuePanel.add(findValueField);

        JLabel valueLabel = new JLabel(String.format("f(%s)=%s", "?", "?"));


        JButton findValueButton = new JButton("Найти");
        findValuePanel.add(valueLabel);
        findValuePanel.add(findValueButton);
        findValueButton.addActionListener(e -> {
            if (interpolateFunction == null) {
                JOptionPane.showMessageDialog(jFrame,
                        "Для вычисления значения в точке должен быть построен график!",
                        "Ошибка",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            else {
                double value = interpolateFunction.getValue(Double.parseDouble(findValueField.getText()));
                valueLabel.setText(String.format("f(%s)=%.3f", findValueField.getText(), value));
            }
        });

        return jFrame;
    }

    private JPanel generateButtons(int argsAmount) {
        JPanel argsPanel = new JPanel(new GridLayout(1, argsAmount));
        xData = new double[argsAmount];
        for (int i = 0; i < argsAmount; i++) {
            int index = i;
            JTextField xValue = new JTextField(5);
            xValue.addFocusListener(new FocusAdapter() {
                @Override
                public void focusLost(FocusEvent fe) {
                    try {
                        if (!xValue.getText().equals(""))
                            xData[index] = Double.parseDouble(xValue.getText());
                    } catch (Exception e) {}
                }
            });
            argsPanel.add(xValue);
        }
        return argsPanel;
    }

    private JPanel getGraphicPanel(int width, int height, double changeX) {
        Func proxy = arg -> Double.compare(arg, changeX) == 0 ?
                baseFunction.getValue(arg) * Main.CHANGE : baseFunction.getValue(arg);
        Calculations calc = new Calculations(proxy);
        Arrays.sort(xData);
        Func interpolateFunction = calc.Interpolate(xData);
        this.interpolateFunction = interpolateFunction;
        JPanel graphicPanel = new Graphing(baseFunction, interpolateFunction, xData).
                getChart(width, height, changeX, proxy.getValue(changeX));
        graphicPanel.setLocation(0, 0);
        graphicPanel.setSize(width, height);
        return graphicPanel;
    }

    public void draw(int width, int height) {
        SwingUtilities.invokeLater(() -> {
            JFrame jFrame = getMainFrame(width, height);
            jFrame.setVisible(true);
        });
    }


}