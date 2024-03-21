package BussinessLogic;

import DataModels.Coefficient;
import DataModels.Polynomial;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class DerivativeTest {
    @Test
    void computeDerivative() {
        Polynomial p1 = new Polynomial();
        p1.addMonomial(2, 3, 1);

        Polynomial pResult = new Polynomial();
        pResult.addMonomial(1, 6, 1);

        Derivative derivative = new Derivative(p1);
        derivative.computeDerivative();

        for (Map.Entry<Integer, Coefficient> entry : pResult.getMap().entrySet()) {
            Integer power = entry.getKey();
            Integer expectedNumerator = entry.getValue().getNumerator();
            Integer expectedDenominator = entry.getValue().getDenominator();

            assertTrue(derivative.getPolynomial2().getMap().containsKey(power), "Power " + power + " missing in the result polynomial");

            Coefficient actualCoefficient = derivative.getPolynomial2().getMap().get(power);
            Integer actualNumerator = actualCoefficient.getNumerator();
            Integer actualDenominator = actualCoefficient.getDenominator();

            assertEquals(expectedNumerator, actualNumerator, "Incorrect numerator for power " + power);
            assertEquals(expectedDenominator, actualDenominator, "Incorrect denominator for power " + power);
        }
        }
}