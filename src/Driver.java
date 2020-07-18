import java.sql.SQLOutput;

public class Driver {
    public static void main(String[] args) {


        Matrix red = Matrix.generateIdentity(3);
        red.appendColumn(new Fraction[] {Fraction.fromInt(25), Fraction.fromInt(15), Fraction.fromInt(21)});

        red.obfuscateMatrix();

        System.out.println(red);

        red.makeReducedEchelonForm();
        System.out.println(red);

        Matrix op = new Matrix(new Fraction[][]{
                new Fraction[] { Fraction.fromInt(1), Fraction.fromInt(2), Fraction.fromInt(3) },
                new Fraction[] { Fraction.fromInt(-4), Fraction.fromInt(1), Fraction.fromInt(3)  },
                new Fraction[] { Fraction.fromInt(-2), Fraction.fromInt(0), Fraction.fromInt(-2) }
        });
        op.appendColumn(new Fraction[] {Fraction.fromInt(118), Fraction.fromInt(-22), Fraction.fromInt(-92)});
        op.makeReducedEchelonForm();
        System.out.println(op);
    }
}
