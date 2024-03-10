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
        for (Map.Entry<Integer, Integer> entry1 : polynomial1.getMap().entrySet()) {
            for (Map.Entry<Integer, Integer> entry2 : polynomial2.getMap().entrySet()) {
                Integer power;
                Integer number;
                power = entry1.getKey() + entry2.getKey();
                number = entry1.getValue() * entry2.getValue();

                if (polynomial3.getMap().containsKey(power))
                    number = number + polynomial3.getMap().get(power);
                polynomial3.addMonomial(power, number);
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
