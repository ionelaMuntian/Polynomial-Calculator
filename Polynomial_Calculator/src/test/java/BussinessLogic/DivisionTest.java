package BussinessLogic;

import DataModels.Coefficient;
import DataModels.Polynomial;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class DivisionTest {
    @Test
    void computeDivision() {
        Polynomial p1 = new Polynomial();
        //7x^2  +  35x +  24
        p1.addMonomial(2, 7, 1);
        p1.addMonomial(1, 35, 1);
        p1.addMonomial(0, 24, 1);

        //x+4
        Polynomial p2 = new Polynomial();
        p2.addMonomial(1, 1, 1);
        p2.addMonomial(0, 4, 1);

        // 7x+7
        Polynomial quotientResult = new Polynomial();
        quotientResult.addMonomial(1, 7, 1);
        quotientResult.addMonomial(0, 7, 1);

        //-4
        Polynomial reminderResult = new Polynomial();
        reminderResult.addMonomial(0, -4, 1);

        Division division = new Division(p1, p2);
        division.computeDivision();

        //compare quotients
        for (Map.Entry<Integer, Coefficient> entry : quotientResult.getMap().entrySet()) {
            Integer power = entry.getKey();
            Integer expectedNumerator = entry.getValue().getNumerator();
            Integer expectedDenominator = entry.getValue().getDenominator();

            assertTrue(division.getQuotient().getMap().containsKey(power), "Power " + power + " missing in the result polynomial");

            Coefficient actualCoefficient = division.getQuotient().getMap().get(power);
            Integer actualNumerator=actualCoefficient.getNumerator();
            Integer actualDenominator=actualCoefficient.getDenominator();

            assertEquals(expectedNumerator, actualNumerator, "Incorrect numerator for power " + power);
            assertEquals(expectedDenominator, actualDenominator, "Incorrect denominator for power " + power);
        }

        //compare reminder
        for (Map.Entry<Integer, Coefficient> entry : reminderResult.getMap().entrySet()) {
            Integer power = entry.getKey();
            Integer expectedNumerator = entry.getValue().getNumerator();
            Integer expectedDenominator = entry.getValue().getDenominator();

            assertTrue(division.getRemainder().getMap().containsKey(power), "Power " + power + " missing in the result polynomial");

            Coefficient actualCoefficient = division.getRemainder().getMap().get(power);
            Integer actualNumerator=actualCoefficient.getNumerator();
            Integer actualDenominator=actualCoefficient.getDenominator();

            assertEquals(expectedNumerator, actualNumerator, "Incorrect numerator for power " + power);
            assertEquals(expectedDenominator, actualDenominator, "Incorrect denominator for power " + power);
        }
    }
    }
