package GUI;
import BussinessLogic.*;
import DataModels.Polynomial;
import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Application extends JFrame implements ActionListener {
    private static JTextPane textPane;
    private JButton button;
    private JLabel labelTitle;
    private JLabel labelError;
    private String inputString;
    private Polynomial polynomial1 = new Polynomial();
    private Polynomial polynomial2 = new Polynomial();
    private boolean power = false;
    private boolean enterPressed = false;
    private boolean xPressed = false;
    private boolean minus = false;
    private Integer currentNumber = 0;
    private Integer currentPower = 0;
    private String lastInsertedCh = "0";
    private static String operation;
    private static StyledDocument doc;

    public Application() {
        setTitle("Polynomial Computer");
        setSize(new Dimension(500, 800));
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //set background
        getContentPane().setBackground(Color.BLACK);

        //set title (in the app frame)
        labelTitle = new JLabel("Polynomial Calculator");
        initTitle();

        textPane = new JTextPane();
        setPane();
        setButtons();
    }

    private void initTitle() {
        labelTitle.setForeground(Color.WHITE);
        labelTitle.setFont(new Font("Times New Roman", Font.BOLD, 25));
        labelTitle.setHorizontalAlignment(SwingConstants.CENTER);
        labelTitle.setBounds(0, 30, getWidth(), 30);
        getContentPane().add(labelTitle);
    }

    private void setLabelError(String text) {
        if (labelError == null) { // Create the label if it's not already created
            labelError = new JLabel();
            labelError.setForeground(Color.RED);
            labelError.setFont(new Font("Times New Roman", Font.BOLD, 17));
            labelError.setHorizontalAlignment(SwingConstants.CENTER);
            getContentPane().add(labelError); // Add it to the content pane
        }
        labelError.setText(text);
        labelError.setBounds(0, 60, getWidth(), 30); // Set its position below labelTitle
    }


    private void setPane() {
        textPane.setBackground(Color.LIGHT_GRAY);
        textPane.setEditable(false);
        textPane.setFont(new Font("Arial", Font.PLAIN, 20));

        JScrollPane scrollPane = new JScrollPane(textPane);
        scrollPane.setBounds(90, 100, getWidth() - 180, getHeight() - 700);
        getContentPane().add(scrollPane);
    }

    private void setButtons() {
        String[] buttonLabels = {"9", "8", "7", "6", "5", "4", "3", "2", "1", "0", "+", "-", "*", "/", "^", "_", "integral", "dx", "=", "NewPolynomial", "x", "Clear"};
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
        String input = e.getActionCommand();

        // Append the input to the inputString
        inputString += input;

        // Define the regex pattern to detect invalid polynomial structures
        Pattern invalidPolynomialPattern = Pattern.compile("\\d*x{2,}|x[^\\\\d+\\-*/^integraldx=]");

        // Check if the input string matches the invalid polynomial pattern
        Matcher invalidPolynomialMatcher = invalidPolynomialPattern.matcher(inputString);

        if (invalidPolynomialMatcher.find()) {
            setLabelError("Invalid polynomial syntax detected! Press Clear and try again.");
            if (Objects.equals(input, "Clear")) {
                clearFields();
            }
            return;
        }
        // Checking which button was clicked
        switch (input) {
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
            case "NewPolynomial":
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

    private void buttonNumber_onAction(int digit) {
        if (power) {
            currentPower = currentPower * 10 + digit;
            appendText(String.valueOf(digit), true);
        } else {
            currentNumber = currentNumber * 10 + digit;
            appendText(String.valueOf(digit), false);
        }
    }

    private void buttonSymbol_onAction(String symbol) {
        /*if =,_,+,-,* are pressed, it means that we must get to the next term (power coeff. are ended)*/
        power = false;
        appendText(String.valueOf(symbol), false);

        saveMonomial(currentNumber, currentPower);
        currentPower = 0;
        currentNumber = 0;
        xPressed = false;
        if (!symbol.equals("^"))
            minus = false;
        if (symbol.equals("-"))
            minus = true;
    }

    private void saveMonomial(Integer currentNumber, Integer currentPower) {

        if (xPressed) {
            if ((currentPower == 0))
                currentPower = 1;
            if (currentNumber == 0)
                currentNumber = 1;
        }
        if (minus)
            currentNumber *= -1;
        System.out.println("currentPower: "+currentPower+" coeff: "+currentNumber);
        if (!enterPressed) {
            polynomial1.addMonomial(currentPower, currentNumber, 1);
        } else {
            polynomial2.addMonomial(currentPower, currentNumber, 1);
        }
    }

    public static void appendText(String text, boolean isSuperscript) {
        doc = textPane.getStyledDocument();
        MutableAttributeSet sup = new SimpleAttributeSet();

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

    private void executeOperation() {
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
                appendText("+ C",false);
                break;
        }
    }

    private void clearFields() {
        textPane.setText("");
        setLabelError("");
        inputString = "";
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
