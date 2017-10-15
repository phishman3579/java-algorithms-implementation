package com.jwetherell.algorithms.numbers;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Integers {

    private static final BigDecimal ZERO = new BigDecimal(0);
    private static final BigDecimal TWO = new BigDecimal(2);

    public static final String toBinaryUsingDivideAndModulus(int numberToConvert) {
        int integer = numberToConvert;
        if (integer<0) throw new IllegalArgumentException("Method argument cannot be negative. number="+integer);
        StringBuilder builder = new StringBuilder();
        int temp = 0;
        while (integer > 0) {
            temp = integer;
            integer = temp / 2;
            builder.append(temp % 2);
        }
        return builder.reverse().toString();
    }

    public static final String toBinaryUsingShiftsAndModulus(int numberToConvert) {
        int integer = numberToConvert;
        if (integer<0) throw new IllegalArgumentException("Method argument cannot be negative. number="+integer);
        StringBuilder builder = new StringBuilder();
        int temp = 0;
        while (integer > 0) {
            temp = integer;
            integer = (temp >> 1);
            builder.append(temp % 2);
        }
        return builder.reverse().toString();
    }

    public static final String toBinaryUsingBigDecimal(int numberToConvert) {
        int integer = numberToConvert;
        if (integer<0) throw new IllegalArgumentException("Method argument cannot be negative. number="+integer);
        StringBuilder builder = new StringBuilder();
        BigDecimal number = new BigDecimal(integer);
        BigDecimal[] decimals = null;
        while (number.compareTo(ZERO) > 0) {
            decimals = number.divideAndRemainder(TWO);
            number = decimals[0];
            builder.append(decimals[1]);
        }
        return builder.reverse().toString();
    }

    public static final String toBinaryUsingDivideAndDouble(int numberToConvert) {
        int integer = numberToConvert;
        if (integer<0) throw new IllegalArgumentException("Method argument cannot be negative. number="+integer);
        StringBuilder builder = new StringBuilder();
        double temp = 0d;
        while (integer > 0) {
            temp = integer/2d;
            integer = (int) temp;
            builder.append((temp > integer) ? 1 : 0);
        }
        return builder.reverse().toString();
    }

    public static final boolean powerOfTwoUsingLoop(int numberToCheck) {
        int number = numberToCheck;
        if (number == 0)
            return false;
        while (number % 2 == 0) {
            number /= 2;
        }
        if (number > 1)
            return false;
        return true;
    }

    public static final boolean powerOfTwoUsingRecursion(int numberToCheck) {
        int number = numberToCheck;
        if (number == 1)
            return true;
        if (number == 0 || number % 2 != 0)
            return false;
        return powerOfTwoUsingRecursion(number / 2);
    }

    public static final boolean powerOfTwoUsingLog(int numberToCheck) {
        int number = numberToCheck;
        double doubleLog = Math.log10(number) / Math.log10(2);
        int intLog = (int) doubleLog;
        if (Double.compare(doubleLog, intLog) == 0)
            return true;
        return false;
    }

    public static final boolean powerOfTwoUsingBits(int numberToCheck) {
        int number = numberToCheck;
        if (number != 0 && ((number & (number - 1)) == 0))
            return true;
        return false;
    }

    // Integer to English
    private static final Map<Integer,String> singleDigits = new HashMap<Integer,String>();
    static {
        singleDigits.put(0,"zero");
        singleDigits.put(1,"one");
        singleDigits.put(2,"two");
        singleDigits.put(3,"three");
        singleDigits.put(4,"four");
        singleDigits.put(5,"five");
        singleDigits.put(6,"six");
        singleDigits.put(7,"seven");
        singleDigits.put(8,"eight");
        singleDigits.put(9,"nine");
        singleDigits.put(10,"ten");
        singleDigits.put(11,"eleven");
        singleDigits.put(12,"twelve");
        singleDigits.put(13,"thirteen");
        singleDigits.put(14,"fourteen");
        singleDigits.put(15,"fifteen");
        singleDigits.put(16,"sixteen");
        singleDigits.put(17,"seventeen");
        singleDigits.put(18,"eighteen");
        singleDigits.put(19,"nineteen");
    }

    private static final Map<Integer,String> multiDigits = new HashMap<Integer,String>();
    static {
        multiDigits.put(10,"ten");
        multiDigits.put(20,"twenty");
        multiDigits.put(30,"thirty");
        multiDigits.put(40,"fourty");
        multiDigits.put(50,"fifty");
        multiDigits.put(60,"sixty");
        multiDigits.put(70,"seventy");
        multiDigits.put(80,"eighty");
        multiDigits.put(90,"ninty");
    }

    private static final int BILLION = 1000000000;
    private static final int MILLION = 1000000;
    private static final int THOUSAND = 1000;
    private static final int HUNDRED = 100;
    private static final int TEN = 10;

    private static final String handleUnderOneThousand(int number) {
        StringBuilder builder = new StringBuilder();
        int x = number;
        int m = x / HUNDRED;
        int r = x % HUNDRED;
        if (m > 0) {
            builder.append(singleDigits.get(m)).append("-hundred");
            x = x % HUNDRED;
        }
        if (r > 0) {
            if (m > 0) builder.append(" ");
            if (x <= 19) {
                builder.append(singleDigits.get(x));
            } else {
                m = x / TEN;
                r = x % TEN;
                if (r == 0) {
                    builder.append(multiDigits.get(x));
                } else {
                    x = x - r;
                    builder.append(multiDigits.get(x)).append("-");
                    builder.append(singleDigits.get(r));
                }
            }
        }
        return builder.toString();
    }

    public static final String toEnglish(int number) {
        int x = number;
        if (x>Integer.MAX_VALUE || x<=Integer.MIN_VALUE) throw new IllegalArgumentException("Number has to be <= Integer.MAX_VALUE and > Integer.MIN_VALUE. number="+x);
        StringBuilder builder = new StringBuilder();
        if (x==0) {
            //Zero is a special case
            builder.append(singleDigits.get(x));
            return builder.toString();
        }
        boolean billion = false;
        boolean million = false;
        boolean thousand = false;
        if (x<0) {
            builder.append("negative ");
            // Make the number positive
            x = x * -1;
        }
        int m = x / BILLION;
        if (m > 0) {
            billion = true;
            builder.append(handleUnderOneThousand(m)).append("-billion");
            x = x % BILLION;
        }
        m = x / MILLION;
        if (m > 0) {
            if (billion) builder.append(" ");
            million = true;
            builder.append(handleUnderOneThousand(m)).append("-million");
            x = x % MILLION;
        }
        m = x / THOUSAND;
        if (m > 0) {
            if (billion || million) builder.append(" ");
            thousand = true;
            builder.append(handleUnderOneThousand(m)).append("-thousand");
            x = x % THOUSAND;
        }
        if (billion || million || thousand && x!=0) builder.append(" ");
        builder.append(handleUnderOneThousand(x));
        return builder.toString();
    }
}
