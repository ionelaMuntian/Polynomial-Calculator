package BussinessLogic;

import DataModels.Coefficient;
import DataModels.Polynomial;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class SubtractionTest {
    @Test
    void computeSubtraction() {
        Polynomial p1 = new Polynomial();
        p1.addMonomial(2, 3, 1);
        Polynomial p2 = new Polynomial();
        p2.addMonomial(2, 4, 1);
        Polynomial pResult = new Polynomial();
        pResult.addMonomial(2, -1, 1);
        Subtraction subtraction = new Subtraction(p1, p2);
        subtraction.computeSubtraction();

        for (Map.Entry<Integer, Coefficient> entry : pResult.getMap().entrySet()) {
            Integer power = entry.getKey();
            Integer expectedNumerator = entry.getValue().getNumerator();
            Integer expectedDenominator = entry.getValue().getDenominator();
            assertTrue(subtraction.getPolynomial3().getMap().containsKey(power), "Power " + power + " missing in the result polynomial");
            Coefficient actualCoefficient = subtraction.getPolynomial3().getMap().get(power);
            Integer actualNumerator = actualCoefficient.getNumerator();
            Integer actualDenominator = actualCoefficient.getDenominator();
            assertEquals(expectedNumerator, actualNumerator, "Incorrect numerator for power " + power);
            assertEquals(expectedDenominator, actualDenominator, "Incorrect denominator for power " + power);
        }
        }
    @Test
    void computeSubtraction2() {
        Polynomial p1 = new Polynomial();
        p1.addMonomial(2, 3, 1);
        Polynomial p2 = new Polynomial();
        p2.addMonomial(2, -4, 1);
        Polynomial pResult = new Polynomial();
        pResult.addMonomial(2, 7, 1);
        Subtraction subtraction = new Subtraction(p1, p2);
        subtraction.computeSubtraction();

        for (Map.Entry<Integer, Coefficient> entry : pResult.getMap().entrySet()) {
            Integer power = entry.getKey();
            Integer expectedNumerator = entry.getValue().getNumerator();
            Integer expectedDenominator = entry.getValue().getDenominator();
            assertTrue(subtraction.getPolynomial3().getMap().containsKey(power), "Power " + power + " missing in the result polynomial");
            Coefficient actualCoefficient = subtraction.getPolynomial3().getMap().get(power);
            Integer actualNumerator = actualCoefficient.getNumerator();
            Integer actualDenominator = actualCoefficient.getDenominator();
            assertEquals(expectedNumerator, actualNumerator, "Incorrect numerator for power " + power);
            assertEquals(expectedDenominator, actualDenominator, "Incorrect denominator for power " + power);
        }
    }

    @Test
    void computeSubtraction3() {
        Polynomial p1 = new Polynomial();
        p1.addMonomial(2, 3, 1);
        Polynomial p2 = new Polynomial();
        p2.addMonomial(1, 4, 1);
        Polynomial pResult = new Polynomial();
        pResult.addMonomial(2, 3, 1);
        pResult.addMonomial(1, -4, 1);
        Subtraction subtraction = new Subtraction(p1, p2);
        subtraction.computeSubtraction();

        for (Map.Entry<Integer, Coefficient> entry : pResult.getMap().entrySet()) {
            Integer power = entry.getKey();
            Integer expectedNumerator = entry.getValue().getNumerator();
            Integer expectedDenominator = entry.getValue().getDenominator();
            assertTrue(subtraction.getPolynomial3().getMap().containsKey(power), "Power " + power + " missing in the result polynomial");
            Coefficient actualCoefficient = subtraction.getPolynomial3().getMap().get(power);
            Integer actualNumerator = actualCoefficient.getNumerator();
            Integer actualDenominator = actualCoefficient.getDenominator();
            assertEquals(expectedNumerator, actualNumerator, "Incorrect numerator for power " + power);
            assertEquals(expectedDenominator, actualDenominator, "Incorrect denominator for power " + power);
        }
    }

}