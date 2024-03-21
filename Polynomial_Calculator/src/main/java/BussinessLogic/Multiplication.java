package BussinessLogic;
import DataModels.Coefficient;
import DataModels.Polynomial;

import java.util.ArrayList;
import java.util.Map;
public class Multiplication {
    private Polynomial polynomial1;
    private Polynomial polynomial2;
    private Polynomial polynomial3;

    public Multiplication(Polynomial polynomial1, Polynomial polynomial2) {
        this.polynomial1 = polynomial1;
        this.polynomial2 = polynomial2;
        this.polynomial3 = new Polynomial();
    }

    public Polynomial getPolynomial3() {
        return polynomial3;
    }

    public void computeMultiplication() {
        for (Map.Entry<Integer, Coefficient> entry1 : polynomial1.getMap().entrySet()) {
            for (Map.Entry<Integer, Coefficient> entry2 : polynomial2.getMap().entrySet()) {

                Integer power = entry1.getKey() + entry2.getKey();
                Integer numeratorProduct = entry1.getValue().getNumerator() * entry2.getValue().getNumerator();
                Integer denominatorProduct = entry1.getValue().getDenominator() * entry2.getValue().getDenominator();

                polynomial3.addMonomial(power, numeratorProduct, denominatorProduct);
            }
        }
        polynomial3.removeNullTerms();
    }


}