package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import javax.swing.text.*;
import javax.swing.text.html.HTMLDocument;

public class Application extends JFrame implements ActionListener {
    private static JTextPane textPane;
    private JTextArea textArea;
    private static SimpleAttributeSet sup;
    private JLabel labelTitle;
    private JButton button;
    private Polynomial polynomial1 = new Polynomial();
    private Polynomial polynomial2 = new Polynomial();
    private boolean power = false;
    private boolean enterPressed = false;
    private boolean xPressed = false;
    private boolean minus = false;  /* to determine if a coefficient is negative*/
    private Integer currentNumber = 0;  //the current number until ^,_,+,-,= appear
    private Integer currentPower = 0;
    private String lastInsertedCh = "0";
    static String operation;
    private static StyledDocument doc;

    public Application() {
        setTitle("Polynomial Computer");
        setSize(new Dimension(500, 800));
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        /*set background*/
        getContentPane().setBackground(Color.BLACK);

        /*set title (in the app frame)*/
        labelTitle = new JLabel("Polynomial Calculator");
        initTitle();

        textPane = new JTextPane();
        setPane();
        setButtons();
    }

    public void initTitle() {
        labelTitle.setForeground(Color.WHITE);
        labelTitle.setFont(new Font("Times New Roman", Font.BOLD, 25));
        labelTitle.setHorizontalAlignment(SwingConstants.CENTER);
        labelTitle.setBounds(0, 30, getWidth(), 30);
        getContentPane().add(labelTitle);
    }

    public void setPane() {
        textPane.setBackground(Color.LIGHT_GRAY);
        textPane.setEditable(false);
        textPane.setFont(new Font("Arial", Font.PLAIN, 20));

        // scroll pane => the text might exceed the textPane
        JScrollPane scrollPane = new JScrollPane(textPane);
        scrollPane.setBounds(90, 100, getWidth() - 180, getHeight() - 700); // Position and size of text area
        getContentPane().add(scrollPane);
    }

    public void setButtons() {
        String[] buttonLabels = {"9", "8", "7", "6", "5", "4", "3", "2", "1", "0", "+", "-", "*", "/", "^", "_", "integral", "dx", "=", "Enter", "x", "Clear"};
        int numCols = 3;
        int buttonWidth = 100;
        int buttonHeight = 50;
        int padding = 10;
        int startX = 90;
        int startY = getHeight() - 570;

        for (int i = 0; i < buttonLabels.length; i++) {
            button = new JButton(buttonLabels[i]);
            button.setBounds(startX + (i % numCols) * (buttonWidth + padding), startY + (i / numCols) * (buttonHeight + padding), buttonWidth, buttonHeight);
            button.setBackground(Color.DARK_GRAY);
            button.setForeground(Color.WHITE);
            button.setFont(new Font("Times New Roman", Font.BOLD, 20));
            getContentPane().add(button);
            button.addActionListener((ActionListener) this);

            if (i == buttonLabels.length - 3) {  //if enter
                button.setBounds(startX + (i % numCols) * (buttonWidth + padding), startY + (i / numCols) * (buttonHeight + padding), 2 * buttonWidth + padding, buttonHeight);
            }

            if (i == buttonLabels.length - 2) {
                button.setBounds(startX + ((i + 1) % numCols) * (buttonWidth + padding), startY + ((i + 1) / numCols) * (buttonHeight + padding), buttonWidth, buttonHeight);
            }
            if (i == buttonLabels.length - 1) {
                button.setBackground(Color.red);
                button.setBounds(startX + ((i + 1) % numCols) * (buttonWidth + padding), startY + (i / numCols) * (buttonHeight + padding), 2 * buttonWidth + padding, buttonHeight);
            }
        }
    }

    public void actionPerformed(ActionEvent e) {
        // Checking which button was clicked
        switch (e.getActionCommand()) {
            case "0":
            case "1":
            case "2":
            case "3":
            case "4":
            case "5":
            case "6":
            case "7":
            case "8":
            case "9":
                buttonNumber_onAction(Integer.parseInt(e.getActionCommand()));
                break;
            case "^":
                if (lastInsertedCh.equals("x")) {
                    power = true;
                }
                break;
            case "-":
            case "_":
            case "+":
            case "*":
            case "/":
                buttonSymbol_onAction(e.getActionCommand());
                break;
            case "integral":
                operation = "integral";
                buttonSymbol_onAction(" integral");
                break;
            case "dx":
                operation = "dx";
                buttonSymbol_onAction(" dx ");
                break;
            case "=":
                buttonSymbol_onAction("=\n");
                executeOperation();
                break;
            case "x":
                xPressed = true;
                appendText(String.valueOf("x"), false);
                break;
            case "Enter":
                operation = lastInsertedCh;
                appendText("\n", false);
                enterPressed = true;
                minus = false;
                break;
            case "Clear":
                clearFields();
                break;
        }
        lastInsertedCh = e.getActionCommand();
    }

    public void buttonNumber_onAction(int digit) {
        System.out.println("Button" + digit + " was clicked!");
        if (power) {
            currentPower = currentPower * 10 + digit;
            appendText(String.valueOf(digit), true); // Insert as normal text
        } else {
            currentNumber = currentNumber * 10 + digit;
            //textPane.setText(textPane.getText() + String.valueOf(digit));
            appendText(String.valueOf(digit), false); // Insert as normal text
        }
    }

    public void buttonSymbol_onAction(String symbol) {
        /*if =,_,+,-,* are pressed, it means that we must get to the next term (power coeff. are ended)*/
        power = false;
        appendText(String.valueOf(symbol), false);
        StyleConstants.setSuperscript(sup, false);

        saveMonomial(currentNumber, currentPower);
        currentPower = 0;
        currentNumber = 0;
        xPressed = false;
        if (!symbol.equals("^"))
            minus = false;
        if (symbol.equals("-"))
            minus = true;
    }

    public void saveMonomial(Integer currentNumber, Integer currentPower) {
        if (minus)
            currentNumber *= -1;
        if (xPressed) {
            if ((currentPower == 0))
                currentPower = 1;
            if (currentNumber == 0)
                currentNumber = 1;
        }
        System.out.println(currentNumber + " " + currentPower);
        if (!enterPressed) {
            polynomial1.addMonomial(currentPower, currentNumber, 1);
        } else {
            polynomial2.addMonomial(currentPower, currentNumber, 1);
        }
    }
    static void appendText(String text, boolean isSuperscript) {
        doc = textPane.getStyledDocument();
        sup = new SimpleAttributeSet();

        if (isSuperscript) {
            StyleConstants.setSuperscript(sup, true);
        } else {
            StyleConstants.setSuperscript(sup, false);
        }
        try {
            doc.insertString(doc.getLength(), text, sup);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void executeOperation() {
        switch (operation) {
            case "+":
                Addition addition = new Addition(polynomial1, polynomial2);
                addition.computeAddition();
                addition.getPolynomial3().display();
                break;
            case "-":
                Subtraction subtraction = new Subtraction(polynomial1, polynomial2);
                subtraction.computeSubtraction();
                subtraction.getPolynomial3().display();
                break;
            case "*":
                Multiplication multiplication = new Multiplication(polynomial1, polynomial2);
                multiplication.computeMultiplication();
                multiplication.getPolynomial3().display();
                break;
            case "/":
                Division division = new Division(polynomial1, polynomial2);
                division.computeDivision();
                appendText("Q: ", false);
                division.getQuotient().display();
                appendText("\n", false);
                appendText("R: ", false);
                division.getRemainder().display();
                break;
            case "dx":
                Derivative derivative = new Derivative(polynomial1);
                derivative.computeDerivative();
                derivative.getPolynomial2().display();
                break;
            case "integral":
                Integral integral = new Integral(polynomial1);
                integral.computeIntegral();
                integral.getPolynomial2().display();
                break;
        }
    }

    public void clearFields() {
        textPane.setText("");
        minus = false;
        power = false;
        currentNumber = 0;
        currentPower = 0;
        polynomial1.getMap().clear();
        polynomial2.getMap().clear();
    }

    public static void main(String[] args) {
        Application a = new Application();
        a.setVisible(true);
    }
}