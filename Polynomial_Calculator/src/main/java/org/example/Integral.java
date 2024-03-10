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
        for (Map.Entry<Integer, Integer> entry : polynomial1.getMap().entrySet()) {
            Integer power = entry.getKey();
            Integer number = entry.getValue();
            power = power + 1;
            number = number;
            polynomial2.addMonomial(power, number);
        }
    }
}
