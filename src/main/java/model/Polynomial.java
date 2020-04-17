package model;

import java.text.DecimalFormat;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Polynomial {

    private List<Monomial> polynomialList;

    private Polynomial() {
        this.polynomialList = new LinkedList<>();
    }

    public Polynomial(String userInput) {
        this.polynomialList = new LinkedList<>();
        String input = userInput.replaceAll("-", "+-");
        input = input.replaceAll("x\\+", "x^1+");
        input = input.replaceAll("x$", "x^1");
        input = input.replaceAll(" ", "");
        input = input.replaceAll("(?:^|\\+)(\\d+)(?:\\+|$)", "+$1x^0+");
        Pattern p = Pattern.compile("(-?\\b\\d+)[xX]\\^(-?\\d+\\b)");
        Matcher m = p.matcher(input);
        while (m.find()) {
            Monomial monomial = new Monomial();
            Double c = Double.parseDouble(m.group(1));
            Integer e = Integer.parseInt(m.group(2));
            monomial.setCof(c);
            monomial.setExp(e);
            this.polynomialList.add(monomial);
        }
    }

    @Override
    public String toString() {
        String result = "";
        DecimalFormat df = new DecimalFormat("0.00");
        boolean first = true;
        for (Monomial m : this.polynomialList) {
            if (first) {
                first = false;
            } else {
                result += "+";
            }
            result += df.format(m.getCof()) + "x^" + m.getExp();
            result = result.replaceAll("\\+-", "-");
            result = result.replaceAll("x\\+", "x^1+");
            result = result.replaceAll("x$", "x^1");
        }
        return result;
    }

    public Polynomial addition(Polynomial p1) {
        Polynomial result = new Polynomial();
        Hashtable<Integer, Double> ht = new Hashtable<>();
        for (Monomial mono1 : this.polynomialList) {
            ht.put(mono1.getExp(), mono1.getCof());
        }
        for (Monomial mono2 : p1.polynomialList) {
            Integer exp = mono2.getExp();
            Double cof = mono2.getCof();
            if (ht.containsKey(exp)) {
                ht.put(exp, cof + ht.get(exp));
            } else {
                ht.put(exp, cof);
            }
        }
        for (Integer exp : ht.keySet()) {
            Monomial m = new Monomial(ht.get(exp), exp);
            result.polynomialList.add(m);
        }
        return result;
    }

    public Polynomial subtraction(Polynomial p1) {
        Polynomial result = new Polynomial();
        Hashtable<Integer, Double> ht = new Hashtable<>();
        for (Monomial mono1 : this.polynomialList) {
            ht.put(mono1.getExp(), mono1.getCof());
        }
        for (Monomial mono2 : p1.polynomialList) {
            Integer exp = mono2.getExp();
            Double cof = mono2.getCof();
            if (ht.containsKey(exp)) {
                ht.put(exp, ht.get(exp) - cof);
            } else {
                ht.put(exp, -cof);
            }
        }
        for (Integer exp : ht.keySet()) {
            Monomial m = new Monomial(ht.get(exp), exp);
            result.polynomialList.add(m);
        }
        return result;
    }

    public Polynomial derivate() {
        Polynomial result = new Polynomial();
        for (Monomial mono : this.polynomialList) {
            Monomial aux = new Monomial(mono.getCof() * mono.getExp(), mono.getExp() - 1);
            result.polynomialList.add(aux);
        }
        return result;
    }

    public Polynomial integrate() {
        Polynomial result = new Polynomial();
        for (Monomial one : this.polynomialList) {
            Monomial aux = new Monomial(one.getCof() / (one.getExp() + 1), one.getExp() + 1);
            result.polynomialList.add(aux);
        }
        return result;
    }

    private void insertAndVerify(Monomial m) {
        //insertion of a monomial in the polynomial
        //verification whether in the respective polynomial there are other duplicates
        boolean found = false;
        for (Monomial mono : polynomialList)
            if (m.getExp() == mono.getExp()) {
                int i = polynomialList.indexOf(mono);
                Monomial newMonom = new Monomial(mono.getCof() + m.getCof(), mono.getExp());
                polynomialList.set(i, newMonom);
                found = true;
                break;
            }
        if (!found) {
            Monomial mono2 = new Monomial(m.getCof(), m.getExp());
            polynomialList.add(mono2);
        }
    }


    public Polynomial multiplication(Polynomial p1) {
        Polynomial result = new Polynomial();
        Polynomial newResult = new Polynomial();
        for (Monomial mono1 : this.polynomialList) {
            for (Monomial mono2 : p1.polynomialList) {
                Monomial aux = new Monomial(mono1.getCof() * mono2.getCof(), mono1.getExp() + mono2.getExp());
                result.polynomialList.add(aux);
            }
        }
        for (Monomial m : result.polynomialList) {
            newResult.insertAndVerify(m);
        }
        return newResult;
    }


    private Polynomial toCopy() {
        Polynomial newList = new Polynomial();
        for (Monomial m : this.polynomialList) {
            newList.polynomialList.add(m);
        }
        return newList;
    }

    private Monomial maxDeg() {
        int maxE = -1;
        Double maxC;
        Monomial monomNou = new Monomial();
        for (Monomial m : this.polynomialList) {
            if ((m.getCof() != 0.0) && (m.getExp() > maxE)) {
                maxE = m.getExp();
                maxC = m.getCof();
                monomNou.setCof(maxC);
                monomNou.setExp(maxE);
            }
        }
        return monomNou;
    }

    public Polynomial[] division(Polynomial p1) {
        Polynomial result[] = new Polynomial[2];
        Polynomial quotient = new Polynomial();
        quotient.insertAndVerify(new Monomial(0, 0));
        Polynomial remainder;
        remainder = this.toCopy();
        while ((remainder.maxDeg().getCof() != 0.0) && (remainder.maxDeg().getExp() >= p1.maxDeg().getExp())) {
            Double co = remainder.maxDeg().getCof() / p1.maxDeg().getCof();
            Integer ex = remainder.maxDeg().getExp() - p1.maxDeg().getExp();
            Monomial term = new Monomial(co, ex);
            Polynomial polyTerm = new Polynomial();
            polyTerm.polynomialList.add(term);
            quotient = quotient.addition(polyTerm);
            Polynomial multi = polyTerm.multiplication(p1);
            remainder = remainder.subtraction(multi);
        }
        result[0] = quotient;
        result[1] = remainder;
        return result;
    }
}