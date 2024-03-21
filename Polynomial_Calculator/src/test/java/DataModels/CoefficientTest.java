package DataModels;

import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CoefficientTest {
    @Test
    void getNumerator() {
        Coefficient coefficient = new Coefficient(5, 2);
        assertEquals(5, coefficient.getNumerator());
    }

    @Test
    void getDenominator() {
        Coefficient coefficient = new Coefficient(5, 2);
        assertEquals(2, coefficient.getDenominator());
    }

    @Test
    void setNumerator() {
        Coefficient coefficient = new Coefficient(5,2);
        coefficient.setNumerator(7);
        assertEquals(7,coefficient.getNumerator());
    }

    @Test
    void setDenominator() {
        Coefficient coefficient = new Coefficient(5,2);
        coefficient.setDenominator(7);
        assertEquals(7,coefficient.getDenominator());
    }
}