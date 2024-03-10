package org.example;

import java.util.Iterator;
import java.util.Map;

public class Addition {
    private Polynomial polynomial1;
    private Polynomial polynomial2;
    private Polynomial polynomial3;

    public Addition(Polynomial polynomial1, Polynomial polynomial2) {
        this.polynomial1 = polynomial1;
        this.polynomial2 = polynomial2;
        this.polynomial3 = new Polynomial();
    }

    public Polynomial getPolynomial3() {
        return polynomial3;
    }

    public void computeAddition() {
        for (Map.Entry<Integer, Polynomial.Coefficient> entry1 : polynomial1.getMap().entrySet()) {
            Integer power = entry1.getKey();
            Polynomial.Coefficient coefficient1 = entry1.getValue();

            if (polynomial2.getMap().containsKey(power)) {
                Polynomial.Coefficient coefficient2 = polynomial2.getMap().get(power);
                int divisor = 1, dividendSum = 0;

                //Check if the 2 coefficients have the same denominator
                if (coefficient1.getDenominator() == coefficient2.getDenominator()) {
                    dividendSum = coefficient1.getNumerator() + coefficient2.getNumerator();
                    divisor = coefficient1.getDenominator();
                } else {
                    dividendSum = coefficient1.getNumerator() * coefficient2.getDenominator() + coefficient2.getNumerator() * coefficient1.getDenominator();
                    divisor = coefficient1.getDenominator()*coefficient2.getDenominator();
                }
                polynomial3.addMonomial(power, dividendSum, divisor);
            } else {
                polynomial3.addMonomial(power, coefficient1.getNumerator(), coefficient1.getDenominator());
            }
        }

        // Add remaining terms from polynomial2
        for (Map.Entry<Integer, Polynomial.Coefficient> entry2 : polynomial2.getMap().entrySet()) {
            Integer power = entry2.getKey();
            Polynomial.Coefficient coefficient = entry2.getValue();

            if (!polynomial3.getMap().containsKey(power)) {
                polynomial3.addMonomial(power, coefficient.getNumerator(), coefficient.getDenominator());
            }
        }
    }


}
