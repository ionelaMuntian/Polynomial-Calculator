package DataModels;
import GUI.Application;

import java.util.*;
public class Polynomial {
    private HashMap<Integer, Coefficient> map;
    public Polynomial() {
        this.map = new HashMap<>();
    }
    public HashMap<Integer, Coefficient> getMap() {
        return map;
    }
    public void addMonomial(Integer power, Integer numerator, Integer denominator) {

        /*If a monomial with the same power already is in the HashMap, the monomials will be added so that
         there is no need to create a linked list*/

        if (!this.getMap().containsKey(power)) {
            map.put(power, new Coefficient(numerator, denominator));
        } else {
            Integer existingNumerator = this.getMap().get(power).getNumerator();
            Integer existingDenominator = this.getMap().get(power).getDenominator();

            numerator = numerator * existingDenominator + existingNumerator * denominator;
            denominator = denominator * existingDenominator;
            map.put(power, new Coefficient(numerator, denominator));
        }
    }

    /*firstMonomial finds the monomial with the greatest power from the polynomial*/
    public Map.Entry<Integer, Coefficient> firstMonomial() {
        Map.Entry<Integer, Coefficient> maxEntry = null;
        for (Map.Entry<Integer, Coefficient> entry : this.map.entrySet()) {
            if (maxEntry == null || ((entry.getKey() > maxEntry.getKey()) && entry.getValue().getNumerator() != 0)) {
                maxEntry = entry;
            }
        }
        return maxEntry;
    }
    public void removeNullTerms(){
        ArrayList<Integer> unnecessaryTerms = new ArrayList<>();
        for (Map.Entry<Integer, Coefficient> entry : this.getMap().entrySet()) {
            if (entry.getValue().getNumerator() == 0) {
                unnecessaryTerms.add(entry.getKey());
            }
        }
        for (Integer key : unnecessaryTerms) {
            this.getMap().remove(key);
        }
    }

    public void display() {
        TreeMap<Integer, Coefficient> reverseMap = new TreeMap<>(java.util.Collections.reverseOrder());
        reverseMap.putAll(this.map);

        int ok = 1;
        for (Map.Entry<Integer, Coefficient> entry : reverseMap.entrySet()) {
            Coefficient coefficient = entry.getValue();
            if (ok == 0 && coefficient.getNumerator() >= 0)
                Application.appendText("+", false);
            ok = 0;
            Application.appendText(String.valueOf(coefficient.getNumerator()), false);
            Application.appendText("x", false);
            Application.appendText(String.valueOf(entry.getKey()), true);

            if (coefficient.getDenominator() != 1) {
                Application.appendText("/", false);
                Application.appendText(String.valueOf(coefficient.getDenominator()), false);
            }
        }
        reverseMap.clear();
    }


}
