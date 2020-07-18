public class Fraction {
    private int numerator;
    private int denominator;

    public int getNumerator() {
        return numerator;
    }

    public void setNumerator(int numerator) {
        this.numerator = numerator;
        simplify();
    }

    public int getDenominator() {
        return denominator;
    }

    public void setDenominator(int denominator) {
        this.denominator = denominator;
        simplify();
    }

    public Fraction(int numerator, int denominator) {
        this.numerator = numerator;
        this.denominator = denominator;
        simplify();
    }

    public Fraction multiply(Fraction op) {
        return new Fraction(this.numerator * op.getNumerator(),
                this.denominator * op.getDenominator());
    }

    public Fraction divideBy(Fraction op) {
        return new Fraction(this.numerator * op.getDenominator(),
                this.denominator * op.getNumerator());
    }

    public Fraction add(Fraction op) {
        return new Fraction(this.numerator * op.denominator + this.denominator * op.numerator,
                this.denominator * op.denominator);
    }

    public Fraction subtractFrom(Fraction op) {
        return new Fraction(this.numerator * op.denominator - this.denominator * op.numerator,
                this.denominator * op.denominator);
    }

    // duh
    public static Fraction fromInt(int num) {
        return new Fraction(num, 1);
    }

    public Fraction inverse() {
        return new Fraction(denominator, numerator);
    }

    public Fraction negative() {
        return new Fraction(0 - numerator, denominator);
    }

    private void simplify() {
        boolean modified = true;

        if (numerator == denominator){
            this.numerator = 1;
            this.denominator = 1;
        }

        while (modified) {
            modified = false;
            if (numerator % 2 == 0 && denominator % 2 == 0) {
                numerator /= 2;
                denominator /= 2;
                modified = true;
            }

            for (int i = 3; i <= numerator && i <= denominator; i += 2) {
                if (numerator % i == 0 && denominator % i == 0) {
                    numerator /= i;
                    denominator /= i;
                    modified = true;
                    break; // shorten this because numbers are likely to have smaller prime factors
                }
            }
        }
        if (numerator < 0 && denominator < 0) {
            numerator *= -1;
            denominator *= -1;
        }


    }

    @Override
    public String toString() {

        if (this.numerator == 0){
            return "0";
        } else if (this.denominator == 1){
            return this.numerator + "";
        } else {
            return this.numerator + " / " + this.denominator;
        }
    }
}
