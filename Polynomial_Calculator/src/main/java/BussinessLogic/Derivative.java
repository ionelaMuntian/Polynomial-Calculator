package BussinessLogic;

import DataModels.Coefficient;
import DataModels.Polynomial;

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
        for (Map.Entry<Integer, Coefficient> entry : polynomial1.getMap().entrySet()) {
            Integer power = entry.getKey();
           Coefficient coefficient = entry.getValue();

            if (power > 0) {
                int derivativeNumerator = coefficient.getNumerator() * power;
                int derivativeDenominator = coefficient.getDenominator();
                power = power - 1;
                polynomial2.addMonomial(power, derivativeNumerator, derivativeDenominator);
            } else {
                polynomial2.addMonomial(0, 0,1);
            }
        }
    }
}

