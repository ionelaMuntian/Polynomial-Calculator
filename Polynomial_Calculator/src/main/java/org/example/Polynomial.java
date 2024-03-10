package org.example;

import java.util.*;


public class Polynomial {
    private HashMap<Integer, Integer> map;

    public Polynomial() {
        this.map = new HashMap<>();
    }

    public HashMap<Integer, Integer> getMap() {
        return map;
    }

    public void addMonomial(Integer power, Integer number) {
        map.put(power, number);
    }

    /*firstMonomial finds the monomial with the greatest power from the polynomial*/
    public Map.Entry<Integer, Integer> firstMonomial() {
        Map.Entry<Integer, Integer> maxEntry = null;
        for (Map.Entry<Integer, Integer> entry : this.map.entrySet()) {
            if (maxEntry == null || ((entry.getKey() > maxEntry.getKey()) && entry.getValue() != 0)) {
                maxEntry = entry;
            }
        }

        return maxEntry;
    }

    public void display() {
        TreeMap<Integer, Integer> reverseMap = new TreeMap<>(java.util.Collections.reverseOrder());
        reverseMap.putAll(this.map);

        int ok = 1;
        for (Map.Entry<Integer, Integer> entry : reverseMap.entrySet()) {
            if (ok == 0 && entry.getValue() >= 0)
                Application.appendText("+", false);
            ok = 0;
            Application.appendText(String.valueOf(entry.getValue()), false);
            Application.appendText("x", false);
            Application.appendText(String.valueOf(entry.getKey()), true);

            if (Objects.equals(Application.operation, "integral")) {
                Application.appendText("/", false);
                Application.appendText(String.valueOf(entry.getKey()), false);
            }
        }
    }

}

