package org.example;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Division {
    private Polynomial divident;
    private Polynomial divisor;
    private Polynomial quotient;
    private Polynomial reminder;

    public Division(Polynomial polynomial1, Polynomial polynomial2) {
        this.divident = polynomial1;
        this.divisor = polynomial2;
        this.quotient = new Polynomial();
        this.reminder = new Polynomial();
    }

    public Polynomial getQuotient() {
        return quotient;
    }

    public Polynomial getReminder() {
        return reminder;
    }

    public Polynomial divisonMonomyals(Map.Entry<Integer, Integer> a, Map.Entry<Integer, Integer> b) {
        Polynomial term = new Polynomial();
        term.addMonomial(a.getKey() - b.getKey(), a.getValue() / b.getValue());
        return term;
    }

    public void computeDivision() {
        Map.Entry<Integer, Integer> maxDivident = divident.firstMonomial();
        Map.Entry<Integer, Integer> maxDivisor = divisor.firstMonomial();
        if (divisor.firstMonomial() == null) {
            Application.appendText("division by 0 is not possible!", false);
            return;
        }

        while (maxDivident != null && maxDivident.getKey() >= maxDivisor.getKey()) {
            Polynomial term = divisonMonomyals(maxDivident, maxDivisor);
            quotient.addMonomial(maxDivident.getKey() - maxDivisor.getKey(), maxDivident.getValue() / maxDivisor.getValue());
            System.out.println(maxDivident.getKey() - maxDivisor.getKey() + " " + maxDivident.getValue() / maxDivisor.getValue());

            Multiplication multiplication = new Multiplication(divisor, term);
            multiplication.computeMultiplication();
            Polynomial multiplicationPolynom = multiplication.getPolynomial3();

            for (Map.Entry<Integer, Integer> entry1 : multiplicationPolynom.getMap().entrySet()) {
                System.out.println("Multiplication result:");
                System.out.println(entry1.getValue() + " with power " + entry1.getKey());
            }

            // Subtract the multiplicationPolynom from the dividend
            Subtraction subtraction = new Subtraction(divident, multiplicationPolynom);
            subtraction.computeSubtraction();
            divident = subtraction.getPolynomial3();

            maxDivident = divident.firstMonomial();
        }

        // Set the reminder to the remaining dividend after the division process
        reminder = divident;
    }
}
