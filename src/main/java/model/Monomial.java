package model;

public class Monomial {
    private double cof;
    private int exp;

    public Monomial() {
        this(0, 0);
    }

    public Monomial(double cof, int exp) {
        this.cof = cof;
        this.exp = exp;
    }

    public double getCof() {
        return cof;
    }

    public void setCof(double cof) {
        this.cof = cof;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

}