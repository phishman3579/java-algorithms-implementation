package com.jwetherell.algorithms.numbers.timing;

import java.text.DecimalFormat;

import com.jwetherell.algorithms.numbers.Integers;
import com.jwetherell.algorithms.numbers.Longs;

public class NumbersTiming {

    private static final DecimalFormat FORMAT = new DecimalFormat("#.######");

    public static void main(String[] args) {
        // Integers
        {
            int a = Integer.MAX_VALUE;
            System.out.println("Integer to binary string using division and modulus.");
            long before = System.nanoTime();
            Integers.toBinaryUsingDivideAndModulus(a);
            long after = System.nanoTime();
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.out.println();
            System.gc();

            System.out.println("Integer to binary string using shifts and modulus.");
            before = System.nanoTime();
            Integers.toBinaryUsingShiftsAndModulus(a);
            after = System.nanoTime();
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.out.println();
            System.gc();

            System.out.println("Integer to binary string using BigDecimal.");
            before = System.nanoTime();
            Integers.toBinaryUsingBigDecimal(a);
            after = System.nanoTime();
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.out.println();
            System.gc();

            System.out.println("Integer to binary string using divide and double.");
            before = System.nanoTime();
            Integers.toBinaryUsingDivideAndDouble(a);
            after = System.nanoTime();
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.out.println();
            System.gc();

            a = (int) Math.pow(2, 30);
            System.out.println("Power of 2 using loop.");
            before = System.nanoTime();
            Integers.powerOfTwoUsingLoop(a);
            after = System.nanoTime();
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.out.println();
            System.gc();

            System.out.println("Power of 2 using recursion.");
            before = System.nanoTime();
            Integers.powerOfTwoUsingRecursion(a);
            after = System.nanoTime();
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.out.println();
            System.gc();

            System.out.println("Power of 2 using logarithm.");
            before = System.nanoTime();
            Integers.powerOfTwoUsingLog(a);
            after = System.nanoTime();
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.out.println();
            System.gc();

            System.out.println("Power of 2 using bits.");
            before = System.nanoTime();
            Integers.powerOfTwoUsingBits(a);
            after = System.nanoTime();
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.out.println();
            System.gc();
        }

        // Longs
        {
            long a = Long.MAX_VALUE;
            System.out.println("Long to binary string using division and modulus.");
            long before = System.nanoTime();
            Longs.toBinaryUsingDivideAndModulus(a);
            long after = System.nanoTime();
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.out.println();
            System.gc();

            System.out.println("Long to binary string using shifts and modulus.");
            before = System.nanoTime();
            Longs.toBinaryUsingShiftsAndModulus(a);
            after = System.nanoTime();
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.out.println();
            System.gc();

            System.out.println("Long to binary string using BigDecimal.");
            before = System.nanoTime();
            Longs.toBinaryUsingBigDecimal(a);
            after = System.nanoTime();
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.out.println();
            System.gc();
        }
    }
}
