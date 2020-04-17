package calculatorTest;

import model.Polynomial;

import static org.junit.Assert.assertEquals;

public class CalculusTest {
    @org.junit.Before
    public void setUp() throws Exception {
    }

    @org.junit.Test
    public void addition() throws Exception {
        Polynomial p1 = new Polynomial("3x^3+7x^2+1x^1+5x^0");
        Polynomial p2 = new Polynomial("4x^4+2x^2-4x^1+3x^0");

        Polynomial result = p1.addition(p2);
        assertEquals("4,00x^4+3,00x^3+9,00x^2-3,00x^1+8,00x^0", result.toString());
    }

    @org.junit.Test
    public void subtraction() throws Exception {
        Polynomial p1 = new Polynomial("3x^7+7x^5+1x^1+5x^0");
        Polynomial p2 = new Polynomial("4x^7+2x^5+3x^3-4x^1+3x^0");

        Polynomial result = p1.subtraction(p2);
        assertEquals("-1,00x^7+5,00x^5-3,00x^3+5,00x^1+2,00x^0", result.toString());
    }

    @org.junit.Test
    public void derivate() throws Exception {
        Polynomial p = new Polynomial("2x^6+4x^2-1x^5");

        Polynomial result = p.derivate();
        assertEquals("12,00x^5+8,00x^1-5,00x^4", result.toString());
    }

    @org.junit.Test
    public void integrate() throws Exception {
        Polynomial p = new Polynomial("4x^6+2x^3+9x^2");

        Polynomial result = p.integrate();
        assertEquals("0,57x^7+0,50x^4+3,00x^3", result.toString());
    }

    @org.junit.Test
    public void multiplication() throws Exception {
        Polynomial p1 = new Polynomial("2x^3+3x^2");
        Polynomial p2 = new Polynomial("4x^1");

        Polynomial result = p1.multiplication(p2);
        assertEquals("8,00x^4+12,00x^3", result.toString());
    }

    @org.junit.Test
    public void division() throws Exception {
        Polynomial p1 = new Polynomial("2x^2");
        Polynomial p2 = new Polynomial("1x^1");

        String result;
        result = " Q:" + p1.division(p2)[0].toString() + " ,R: " + p1.division(p2)[1].toString();
        assertEquals(" Q:2,00x^1+0,00x^0 ,R: 0,00x^2", result);

    }
}
