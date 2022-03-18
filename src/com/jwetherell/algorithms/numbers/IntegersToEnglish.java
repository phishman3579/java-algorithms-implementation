package com.jwetherell.algorithms.numbers;

import java.util.HashMap;
import java.util.Map;

// Refactoring Technique used: Extract Class
public class IntegersToEnglish {
    // Integer to English
    // Refactoring Technique: Move Field.
    // 1. Moved the below fields from Integer class to InterToEnglish class
    private static final int HUNDRED = 100;
    private static final int TEN = 10;

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
        multiDigits.put(40,"forty");
        multiDigits.put(50,"fifty");
        multiDigits.put(60,"sixty");
        multiDigits.put(70,"seventy");
        multiDigits.put(80,"eighty");
        multiDigits.put(90,"ninety");
    }

    // Refactoring Technique: Move Method; Previous: Integer class ; After: IntegerToEnglish class
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

    // Refactoring Technique: Move Method; Previous: Integer class ; After: IntegerToEnglish class
    public static final String toEnglish(int number) {
        // Refactoring Technique: Move Field.F
        // 1. Moved the below fields from Integer class to InterToEnglish class
        // 2. Within IntegerToEnglish class, moving the field to local method where it is required.
        final int BILLION = 1000000000;
        final int MILLION = 1000000;
        final int THOUSAND = 1000;

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
