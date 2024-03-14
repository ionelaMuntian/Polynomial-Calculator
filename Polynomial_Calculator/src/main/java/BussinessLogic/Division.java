package BussinessLogic;

import DataModels.Coefficient;
import DataModels.Polynomial;
import GUI.Application;

import java.util.Map;

public class Division {
    private Polynomial dividend;
    private Polynomial divisor;
    private Polynomial quotient;
    private Polynomial remainder;

    public Division(Polynomial polynomial1, Polynomial polynomial2) {
        this.dividend = polynomial1;
        this.divisor = polynomial2;
        this.quotient = new Polynomial();
        this.remainder = new Polynomial();
    }

    public Polynomial getQuotient() {
        return quotient;
    }

    public Polynomial getRemainder() {
        return remainder;
    }

    public Polynomial divisionMonomials(Map.Entry<Integer, Coefficient> a, Map.Entry<Integer, Coefficient> b) {
        Polynomial term = new Polynomial();

        // Division between 2 fractions : (a1/a2)/(b1/b2)=(a1*b2)/(a2*b1)
        int numerator = a.getValue().getNumerator() * b.getValue().getDenominator();
        int denominator = a.getValue().getDenominator() * b.getValue().getNumerator();
        int power = a.getKey() - b.getKey();
        term.addMonomial(power, numerator, denominator);
        quotient.addMonomial(power, numerator, denominator);
        return term;
    }

    public void computeDivision() {
        Map.Entry<Integer, Coefficient> maxDividend = dividend.firstMonomial();
        Map.Entry<Integer, Coefficient> maxDivisor = divisor.firstMonomial();

        if (divisor.firstMonomial().getValue().getNumerator()==0) {
            Application.appendText("Division by 0 is not possible!\n", false);
            return;
        }

        while (maxDividend != null && maxDividend.getKey() >= maxDivisor.getKey()) {
            Polynomial term = divisionMonomials(maxDividend, maxDivisor);

            //multiply each term from the divisor with 'term'
            Multiplication multiplication = new Multiplication(divisor, term);
            multiplication.computeMultiplication();
            Polynomial multiplicationPolynomial = multiplication.getPolynomial3();

            // Subtract the multiplicationPolynomial from the dividend
            Subtraction subtraction = new Subtraction(dividend, multiplicationPolynomial);
            subtraction.computeSubtraction();
            dividend = subtraction.getPolynomial3();
            maxDividend = dividend.firstMonomial();
        }

        // Set the remainder to the remaining dividend after the division process
        remainder = dividend;
    }
}
