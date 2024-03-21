package BussinessLogic;

import DataModels.Coefficient;
import DataModels.Polynomial;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MultiplicationTest {

    @Test
    void computeMultiplication() {
        Polynomial p1 = new Polynomial();
        p1.addMonomial(2, 3, 1);
        Polynomial p2 = new Polynomial();
        p2.addMonomial(2, 4, 1);
        Polynomial pResult = new Polynomial();
        pResult.addMonomial(4, 12, 1);

        Multiplication multiplication = new Multiplication(p1, p2);
        multiplication.computeMultiplication();

        for (Map.Entry<Integer, Coefficient> entry : pResult.getMap().entrySet()) {
            Integer power = entry.getKey();
            Integer expectedNumerator = entry.getValue().getNumerator();
            Integer expectedDenominator = entry.getValue().getDenominator();

            assertTrue(multiplication.getPolynomial3().getMap().containsKey(power), "Power " + power + " missing in the result polynomial");

            Coefficient actualCoefficient = multiplication.getPolynomial3().getMap().get(power);
            Integer actualNumerator=actualCoefficient.getNumerator();
            Integer actualDenominator=actualCoefficient.getDenominator();

            assertEquals(expectedNumerator, actualNumerator, "Wrong numerator for power " + power);
            assertEquals(expectedDenominator, actualDenominator, "Wrong denominator for power " + power);
        }
    }
}