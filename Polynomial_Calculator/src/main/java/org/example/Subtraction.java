package org.example;

import java.util.ArrayList;
import java.util.Iterator;
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
        for (Map.Entry<Integer, Integer> entry1 : polynomial1.getMap().entrySet()) {
            Integer power = entry1.getKey();
            Integer number = entry1.getValue();

            //System.out.println(coefficient + " " + power);
            if (polynomial2.getMap().containsKey(power)) {
                Integer sum = number - polynomial2.getMap().get(power);
                polynomial3.addMonomial(power, sum);
            } else {
                polynomial3.addMonomial(power, number);
            }
        }

        // Add remaining terms from polynomial2
        for (Map.Entry<Integer, Integer> entry2 : polynomial2.getMap().entrySet()) {
            Integer power = entry2.getKey();
            Integer number = entry2.getValue();

            if (!polynomial3.getMap().containsKey(power)) {
                polynomial3.addMonomial(power, -number);
            }
        }

        //remove the terms with coefficient 0
        ArrayList<Integer> unnecesaryTerms= new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry2 : polynomial3.getMap().entrySet()) {
            if (entry2.getValue() == 0) {
                unnecesaryTerms.add(entry2.getKey());
            }
        }
        for (Integer i : unnecesaryTerms) {
           polynomial3.getMap().remove(i);
        }

    }
}
