package DataModels;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PolynomialTest {
    @Test
    void addMonomial() {
        Polynomial polynomial = new Polynomial();
        polynomial.addMonomial(2,5,1);
        Map.Entry<Integer, Coefficient> entry = polynomial.firstMonomial();
        assertEquals(2,entry.getKey());
        assertEquals(5,entry.getValue().getNumerator());
        assertEquals(1,entry.getValue().getDenominator());
    }

    @Test
    void firstMonomial() {
        Polynomial polynomial = new Polynomial();
        polynomial.addMonomial(2,5,1);
        polynomial.addMonomial(3,2,1);
        Map.Entry<Integer, Coefficient> entry = polynomial.firstMonomial();
        assertEquals(3,entry.getKey());
        assertEquals(2,entry.getValue().getNumerator());
        assertEquals(1,entry.getValue().getDenominator());
    }

    @Test
    void removeNullTerms() {
        Polynomial polynomial = new Polynomial();
        polynomial.addMonomial(2,5,1);
        polynomial.addMonomial(3,2,1);
        polynomial.addMonomial(3,0,1);
        polynomial.removeNullTerms();
        for (Map.Entry<Integer, Coefficient> entry : polynomial.getMap().entrySet()) {
            assertFalse(entry.getValue().getNumerator()==0,"A null term is still in the map!");
        }
    }
}