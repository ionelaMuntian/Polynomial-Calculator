package BussinessLogic;

import DataModels.Coefficient;
import DataModels.Polynomial;

import java.util.ArrayList;
import java.util.Map;

public class Subtraction {
    private Polynomial polynomial1;
    private Polynomial polynomial2;
    private Polynomial polynomial3;

    public Subtraction(Polynomial polynomial1, Polynomial polynomial2) {
        this.polynomial1 = polynomial1;
        this.polynomial2 = polynomial2;
        this.polynomial3 = new Polynomial();
    }

    public Polynomial getPolynomial3() {
        return polynomial3;
    }

    public void computeSubtraction() {
        for (Map.Entry<Integer, Coefficient> entry1 : polynomial1.getMap().entrySet()) {
            Integer power = entry1.getKey();
            Coefficient coefficient1 = entry1.getValue();

            if (polynomial2.getMap().containsKey(power)) {
                Coefficient coefficient2 = polynomial2.getMap().get(power);
                int divisor = 1, dividendDiff = 0;

                //Check if the 2 coefficients have the same denominator
                if (coefficient1.getDenominator() == coefficient2.getDenominator()) {
                    dividendDiff = coefficient1.getNumerator() - coefficient2.getNumerator();
                    divisor = coefficient1.getDenominator();
                } else {
                    dividendDiff = coefficient1.getNumerator() * coefficient2.getDenominator() - coefficient2.getNumerator() * coefficient1.getDenominator();
                    divisor = coefficient1.getDenominator()*coefficient2.getDenominator();
                }
                polynomial3.addMonomial(power, dividendDiff, divisor);
            } else {
                polynomial3.addMonomial(power, coefficient1.getNumerator(), coefficient1.getDenominator());
            }
        }

        // Add remaining terms from polynomial2
        for (Map.Entry<Integer, Coefficient> entry2 : polynomial2.getMap().entrySet()) {
            Integer power = entry2.getKey();
            Coefficient coefficient = entry2.getValue();

            if (!polynomial3.getMap().containsKey(power)) {
                polynomial3.addMonomial(power,-coefficient.getNumerator(),coefficient.getDenominator());
            }
        }

        // Remove the terms with coefficient 0
        ArrayList<Integer> unnecessaryTerms = new ArrayList<>();
        for (Map.Entry<Integer, Coefficient> entry : polynomial3.getMap().entrySet()) {
            if (entry.getValue().getNumerator() == 0) {
                unnecessaryTerms.add(entry.getKey());
            }
        }
        for (Integer key : unnecessaryTerms) {
            polynomial3.getMap().remove(key);
        }
    }
}
