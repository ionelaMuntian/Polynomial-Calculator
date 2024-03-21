package BussinessLogic;

import DataModels.Coefficient;
import DataModels.Polynomial;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class IntegralTest {
    @Test
    void computeIntegral() {
        Polynomial p1 = new Polynomial();
        p1.addMonomial(2, 3, 1);

        Polynomial pResult = new Polynomial();
        pResult.addMonomial(3, 3, 3);

        Integral integral = new Integral(p1);
        integral.computeIntegral();

        for (Map.Entry<Integer, Coefficient> entry : pResult.getMap().entrySet()) {
            Integer power = entry.getKey();
            Integer expectedNumerator = entry.getValue().getNumerator();
            Integer expectedDenominator = entry.getValue().getDenominator();

            assertTrue(integral.getPolynomial2().getMap().containsKey(power), "Power " + power + " missing in the result polynomial");

            Coefficient actualCoefficient = integral.getPolynomial2().getMap().get(power);
            Integer actualNumerator = actualCoefficient.getNumerator();
            Integer actualDenominator = actualCoefficient.getDenominator();

            assertEquals(expectedNumerator, actualNumerator, "Incorrect numerator for power " + power);
            assertEquals(expectedDenominator, actualDenominator, "Incorrect denominator for power " + power);
        }
    }

    @Test
    void computeIntegral2() {
        Polynomial p1 = new Polynomial();
        p1.addMonomial(0, 3, 1);

        Polynomial pResult = new Polynomial();
        pResult.addMonomial(1, 3, 1);

        Integral integral = new Integral(p1);
        integral.computeIntegral();

        for (Map.Entry<Integer, Coefficient> entry : pResult.getMap().entrySet()) {
            Integer power = entry.getKey();
            Integer expectedNumerator = entry.getValue().getNumerator();
            Integer expectedDenominator = entry.getValue().getDenominator();

            assertTrue(integral.getPolynomial2().getMap().containsKey(power), "Power " + power + " missing in the result polynomial");

            Coefficient actualCoefficient = integral.getPolynomial2().getMap().get(power);
            Integer actualNumerator = actualCoefficient.getNumerator();
            Integer actualDenominator = actualCoefficient.getDenominator();

            assertEquals(expectedNumerator, actualNumerator, "Incorrect numerator for power " + power);
            assertEquals(expectedDenominator, actualDenominator, "Incorrect denominator for power " + power);
        }
    }
}