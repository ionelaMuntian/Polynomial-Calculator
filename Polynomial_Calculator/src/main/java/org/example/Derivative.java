package org.example;
import java.util.Map;
public class Derivative {
    private Polynomial polynomial1;
    private Polynomial polynomial2;

    public Derivative(Polynomial polynomial1) {
        this.polynomial1 = polynomial1;
        this.polynomial2 = new Polynomial();
    }

    public Polynomial getPolynomial2() {
        return polynomial2;
    }

    public void computeDerivative() {
        for (Map.Entry<Integer, Integer> entry : polynomial1.getMap().entrySet()) {
            Integer power= entry.getKey();
            Integer number= entry.getValue();
            if (power > 0) {
                number = number * power;
                power = power - 1;
                polynomial2.addMonomial(power, number);
            } else {
                polynomial2.addMonomial(0, 0);
            }

        }
    }

}
