package org.example;

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
        for (Map.Entry<Integer, Polynomial.Coefficient> entry1 : polynomial1.getMap().entrySet()) {
            for (Map.Entry<Integer, Polynomial.Coefficient> entry2 : polynomial2.getMap().entrySet()) {

                Integer power = entry1.getKey() + entry2.getKey();
                Integer numeratorProduct = entry1.getValue().getNumerator() * entry2.getValue().getNumerator();
                Integer denominatorProduct = entry1.getValue().getDenominator() * entry2.getValue().getDenominator();

                /*If this power already exists in the hash map, the terms with the same power are added directly,
                instead of making a linked list and then summing them*/
                if (polynomial3.getMap().containsKey(power)) {
                    Integer existingNumerator = polynomial3.getMap().get(power).getNumerator();
                    Integer existingDenominator = polynomial3.getMap().get(power).getDenominator();

                    numeratorProduct = numeratorProduct * existingDenominator + existingNumerator * denominatorProduct;
                    denominatorProduct = denominatorProduct * existingDenominator;
                }
                polynomial3.addMonomial(power, numeratorProduct, denominatorProduct);
            }
        }
        removeNullTerms();
    }
    // Remove the terms with coefficient 0
    public void removeNullTerms(){
        ArrayList<Integer> unnecessaryTerms = new ArrayList<>();
        for (Map.Entry<Integer, Polynomial.Coefficient> entry : polynomial3.getMap().entrySet()) {
            if (entry.getValue().getNumerator() == 0) {
                unnecessaryTerms.add(entry.getKey());
            }
        }
        for (Integer key : unnecessaryTerms) {
            polynomial3.getMap().remove(key);
        }
    }
}