package DataModels;

public  class Coefficient {
    private int numerator;
    private int denominator;

    public Coefficient(int numerator, int denominator) {
        this.numerator = numerator;
        this.denominator = denominator;
    }

    public int getNumerator() {
        return numerator;
    }

    public int getDenominator() {
        return denominator;
    }
}
