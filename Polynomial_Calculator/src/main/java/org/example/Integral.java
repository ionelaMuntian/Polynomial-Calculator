package org.example;

import java.util.Map;

public class Integral {
    private Polynomial polynomial1;
    private Polynomial polynomial2;

    public Integral(Polynomial polynomial1) {
        this.polynomial1 = polynomial1;
        this.polynomial2 = new Polynomial();
    }

    public Polynomial getPolynomial2() {
        return polynomial2;
    }
    public void computeIntegral() {
        for (Map.Entry<Integer, Polynomial.Coefficient> entry : polynomial1.getMap().entrySet()) {
            Integer power =  entry.getKey() + 1;
            Integer numerator = entry.getValue().getNumerator();
            Integer denominator = entry.getValue().getDenominator() * power;

            polynomial2.addMonomial(power, numerator, denominator);
        }
    }
}
