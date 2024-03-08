package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import javax.swing.text.*;
import javax.swing.text.html.HTMLDocument;


public class Application extends JFrame implements ActionListener {
    HashMap<Integer, Integer> map = new HashMap<>();
    HashMap<Integer, Integer> map2 = new HashMap<>();
    JTextPane textPane;
    boolean power = false;
    boolean enterPressed = false;
    Integer currentNumber = 0;  //the current number until ^,_,+,-,= appear
    Integer currentPower = 0;
    String lastInsertedCh = "0";
    String operation;
    JTextArea textArea;
    StyledDocument doc;
    SimpleAttributeSet sup;

    public Application() {
        initWindow();
    }

    public void initWindow() {
        setTitle("Polynomial Computer");
        setSize(new Dimension(500, 800));
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //set background
        getContentPane().setBackground(Color.BLACK);

        //set title
        JLabel label1 = new JLabel("Polynomial Calculator");
        label1.setForeground(Color.WHITE);
        label1.setFont(new Font("Times New Roman", Font.BOLD, 25));
        label1.setHorizontalAlignment(SwingConstants.CENTER);
        label1.setBounds(0, 30, getWidth(), 30);
        getContentPane().add(label1);

        setPane();
        setButtons();
    }

    public void setPane() {
        textPane = new JTextPane();
        textPane.setBackground(Color.LIGHT_GRAY);
        textPane.setEditable(false);
        textPane.setFont(new Font("Arial", Font.PLAIN, 20));

        // scroll pane => the text might exceed the textPane
        JScrollPane scrollPane = new JScrollPane(textPane);
        scrollPane.setBounds(90, 100, getWidth() - 180, getHeight() - 700); // Position and size of text area
        getContentPane().add(scrollPane);
    }

    public void setButtons() {
        String[] buttonLabels = {"9", "8", "7", "6", "5", "4", "3", "2", "1", "0", "+", "-", "*", "/", "^", "_", "integration", "derivative", "=", "Enter", "x", "Clear"};
        int numCols = 3;
        int buttonWidth = 100;
        int buttonHeight = 50;

        int padding = 10;
        int startX = 90;
        int startY = getHeight() - 570;


        for (int i = 0; i < buttonLabels.length; i++) {
            JButton button = new JButton(buttonLabels[i]);
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
                buttonNumber_onAction(0);
                break;
            case "1":
                buttonNumber_onAction(1);
                break;
            case "2":
                buttonNumber_onAction(2);
                break;
            case "3":
                buttonNumber_onAction(3);
                break;
            case "4":
                buttonNumber_onAction(4);
                break;
            case "5":
                buttonNumber_onAction(5);
                break;
            case "6":
                buttonNumber_onAction(6);
                break;
            case "7":
                buttonNumber_onAction(7);
                break;
            case "8":
                buttonNumber_onAction(8);
                break;
            case "9":
                buttonNumber_onAction(9);
                break;
            case "^":
                if (lastInsertedCh.equals("x")) {
                    power = true;
                }
                break;
            case "_":
                appendText(String.valueOf("_"), false);
                buttonSymbol_onAction();
                break;
            case "+":
                appendText(String.valueOf("+"), false); // Insert as normal text
                buttonSymbol_onAction();
                break;
            case "-":
                appendText(String.valueOf(" - "), false);
                buttonSymbol_onAction();
                break;
            case "*":
                appendText(String.valueOf(" * "), false);
                buttonSymbol_onAction();
                break;
            case "/":
                appendText(String.valueOf(" / "), false);
                buttonSymbol_onAction();
                break;
            case "integration":
                appendText(String.valueOf("integral"), false);
                buttonSymbol_onAction();
                break;
            case "derivative":
                appendText(String.valueOf("derivative"), false);
                buttonSymbol_onAction();
                break;
            case "=":
                appendText(String.valueOf("="), false);
                buttonSymbol_onAction();
                break;
            case "x":
                appendText(String.valueOf("x"), false);
                currentPower = 1;
                break;
            case "Enter":
                appendText(String.valueOf("\n"), false);
                operation = lastInsertedCh;
                buttonSymbol_onAction();
                enterPressed = true;
                break;
            case "Clear":
                textPane.setText(""); // Clear the text pane
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


    private void appendText(String text, boolean isSuperscript) {
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

    public void buttonSymbol_onAction() {
        //if =,_,+,-,* are pressed, it means that we must get to the next term
        power = false;
        StyleConstants.setSuperscript(sup, false);
        //StyleConstants.setSuperscript(sup, true);
        saveInHash(currentNumber, currentPower);
        currentPower = 0;
        currentNumber = 0;
    }

    public void saveInHash(int currentNumber, int currentPower) {
        if (enterPressed = false)
            map.put(currentPower, currentNumber);
        else
            map2.put(currentPower, currentNumber);
    }

    public static void main(String[] args) {
        Application a = new Application();
        a.setVisible(true);
    }
}