package com.jwetherell.algorithms.mathematics;

import java.math.BigDecimal;
import java.math.BigInteger;

public class Operations {
    
    /* TODO: How to handle number overflow? */
    public static Number rowMultiplication(Number test, Number[] row, Number[] column, int cols){
        if (test instanceof BigDecimal) {
            BigDecimal result = BigDecimal.ZERO;
            for (int i = 0; i < cols; i++) {
                Number m1 = row[i];
                Number m2 = column[i];
                BigDecimal result2 = ((BigDecimal)m1).multiply(((BigDecimal)m2));
                result = result.add(result2);
            }
            return result;
        } else if (test instanceof BigInteger) {
            BigInteger result = BigInteger.ZERO;
            for (int i = 0; i < cols; i++) {
                Number m1 = row[i];
                Number m2 = column[i];
                BigInteger result2 = ((BigInteger)m1).multiply(((BigInteger)m2));
                result = result.add(result2);
            }
            return result;
        } else if (test instanceof Long) {
            Long result = 0l;
            for (int i = 0; i < cols; i++) {
                Number m1 = row[i];
                Number m2 = column[i];
                Long result2 = m1.longValue() * m2.longValue();
                result = result+result2;
            }
            return result;
        } else if (test instanceof Double) {
            Double result = 0d;
            for (int i = 0; i < cols; i++) {
                Number m1 = row[i];
                Number m2 = column[i];
                Double result2 = m1.doubleValue() * m2.doubleValue();
                result = result+result2;
            }
            return result;
        } else if (test instanceof Float) {
            Float result = 0f;
            for (int i = 0; i < cols; i++) {
                Number m1 = row[i];
                Number m2 = column[i];
                Float result2 = m1.floatValue() * m2.floatValue();
                result = result+result2;
            }
            return result;
        } else {
            // Integer
            Integer result = 0;
            for (int i = 0; i < cols; i++) {
                Number m1 = row[i];
                Number m2 = column[i];
                Integer result2 = m1.intValue() * m2.intValue();
                result = result+result2;
            }
            return result;
        }
    }

    /* TODO: How to handle number overflow? */
    public static Number addNumbers(Number m1, Number m2) {
        if (m1 instanceof BigDecimal || m2 instanceof BigDecimal) {
            return ((BigDecimal)m1).add((BigDecimal)m2);
        } else if (m1 instanceof BigInteger || m2 instanceof BigInteger) {
            return ((BigInteger)m1).add((BigInteger)m2);
        } else if (m1 instanceof Long || m2 instanceof Long) {
            return (m1.longValue() + m2.longValue());
        } else if (m1 instanceof Double || m2 instanceof Double) {
            return (m1.doubleValue() + m2.doubleValue());
        } else if (m1 instanceof Float || m2 instanceof Float) {
            return (m1.floatValue() + m2.floatValue());
        } else {
            // Integer
            return (m1.intValue() + m2.intValue());
        }
    }
}