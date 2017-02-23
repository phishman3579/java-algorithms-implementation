package com.jwetherell.algorithms.mathematics;

import java.util.ArrayList;
import java.util.Collections;

import com.jwetherell.algorithms.numbers.Complex;

public class Multiplication {

    public static final long multiplication(int a, int b) {
        long result = ((long) a) * ((long) b);
        return result;
    }

    public static final long multiplyUsingLoop(int a, int b) {
        int absB = Math.abs(b);
        long result = a;
        for (int i = 1; i < absB; i++) {
            result += a;
        }
        return (b < 0) ? -result : result;
    }

    public static final long multiplyUsingRecursion(int a, int b) {
        int absB = Math.abs(b);
        long result = a;
        if (absB == 1)
            return result;

        result += multiplyUsingRecursion(a, absB - 1);
        return (b < 0) ? -result : result;
    }

    public static final long multiplyUsingShift(int a, int b) {
        int absA = Math.abs(a);
        int absB = Math.abs(b);

        long result = 0L;
        while (absA > 0) {
            if ((absA & 1) > 0)
                result += absB; // Is odd
            absA >>= 1;
            absB <<= 1;
        }

        return (a > 0 && b > 0 || a < 0 && b < 0) ? result : -result;
    }

    public static final long multiplyUsingLogs(int a, int b) {
        long absA = Math.abs(a);
        long absB = Math.abs(b);
        long result = Math.round(Math.pow(10, (Math.log10(absA) + Math.log10(absB))));
        return (a > 0 && b > 0 || a < 0 && b < 0) ? result : -result;
    }

    public static String multiplyUsingFFT(String a, String b) {
        if (a.equals("0") || b.equals("0")) {
            return "0";
        }
        boolean negative = false;
        if ((a.charAt(0) == '-' && b.charAt(0) != '-') || (a.charAt(0) != '-' && b.charAt(0) == '-')) {
            negative = true;
        }
        if (a.charAt(0) == '-') {
            a = a.substring(1);
        }
        if (b.charAt(0) == '-') {
            b = b.substring(1);
        }
        int size = 1;
        while (size < (a.length() + b.length())) {
            size *= 2;
        }
        Complex[] aCoefficients = new Complex[size];
        Complex[] bCoefficients = new Complex[size];
        for (int i = 0; i < size; i++) {
            aCoefficients[i] = new Complex();
            bCoefficients[i] = new Complex();
        }
        for (int i = 0; i < a.length(); i++) {
            aCoefficients[i] = new Complex((double) (Character.getNumericValue(a.charAt(a.length() - i - 1))), 0.0);
        }
        for (int i = 0; i < b.length(); i++) {
            bCoefficients[i] = new Complex((double) (Character.getNumericValue(b.charAt(b.length() - i - 1))), 0.0);
        }

        FastFourierTransform.cooleyTukeyFFT(aCoefficients);
        FastFourierTransform.cooleyTukeyFFT(bCoefficients);

        for (int i = 0; i < size; i++) {
            aCoefficients[i] = aCoefficients[i].multiply(bCoefficients[i]);
        }
        for (int i = 0; i < size / 2; i++) {
            Complex temp = aCoefficients[i];
            aCoefficients[i] = aCoefficients[size - i - 1];
            aCoefficients[size - i - 1] = temp;
        }
        FastFourierTransform.cooleyTukeyFFT(aCoefficients);

        ArrayList<Integer> res = new ArrayList<Integer>();
        int pass = 0;
        for (int i = 0; i < size; i++) {
            res.add((int) (pass + Math.floor((aCoefficients[i].abs() + 1) / size)));
            if (res.get(i) >= 10) {
                pass = res.get(i) / 10;
                res.set(i, res.get(i) % 10);
            } else {
                pass = 0;
            }
        }
        Collections.reverse(res);
        StringBuilder result = new StringBuilder();
        if (negative) {
            result.append('-');
        }
        boolean startPrinting = false;
        for (Integer x : res) {
            if (x != 0) {
                startPrinting = true;
            }
            if (startPrinting) {
                result.append(x);
            }
        }
        return result.toString();
    }
}
