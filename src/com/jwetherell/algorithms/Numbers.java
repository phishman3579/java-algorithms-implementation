package com.jwetherell.algorithms;

import java.text.DecimalFormat;

import com.jwetherell.algorithms.numbers.Integers;
import com.jwetherell.algorithms.numbers.Longs;


public class Numbers {

    private static final DecimalFormat FORMAT = new DecimalFormat("#.######");

    public static void main(String[] args) {
        // Integers
        {
            int a = Integer.MAX_VALUE;
            System.out.println("Integer to binary string using division and modulus.");
            long before = System.nanoTime();
            String result = Integers.toBinaryUsingDivideAndModulus(a);
            long after = System.nanoTime();
            System.out.println("a=" + a + " " + result);
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.gc();

            System.out.println("Integer to binary string using shifts and modulus.");
            before = System.nanoTime();
            result = Integers.toBinaryUsingShiftsAndModulus(a);
            after = System.nanoTime();
            System.out.println("a=" + a + " " + result);
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.gc();

            System.out.println("Integer to binary string using BigDecimal.");
            before = System.nanoTime();
            result = Integers.toBinaryUsingBigDecimal(a);
            after = System.nanoTime();
            System.out.println("a=" + a + " " + result);
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.gc();

            System.out.println("Integer to binary string using divide and double.");
            before = System.nanoTime();
            result = Integers.toBinaryUsingDivideAndDouble(a);
            after = System.nanoTime();
            System.out.println("a=" + a + " " + result);
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.gc();

            System.out.println("Euclid's Greatest Common Divisor.");
            int x = 1989;
            int y = 867;
            before = System.nanoTime();
            int gcd = Integers.euclidsGreatestCommonDivsor(x, y);
            after = System.nanoTime();
            System.out.println("x=" + x + " " + "y=" + y + " " + gcd);
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.gc();

            a = (int) Math.pow(2, 30);
            System.out.println("Power of 2 using loop.");
            before = System.nanoTime();
            boolean isPowOf2 = Integers.powerOfTwoUsingLoop(a);
            after = System.nanoTime();
            System.out.println("a=" + a + " is a power of 2? " + isPowOf2);
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.gc();

            System.out.println("Power of 2 using recursion.");
            before = System.nanoTime();
            isPowOf2 = Integers.powerOfTwoUsingRecursion(a);
            after = System.nanoTime();
            System.out.println("a=" + a + " is a power of 2? " + isPowOf2);
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.gc();

            System.out.println("Power of 2 using logarithm.");
            before = System.nanoTime();
            isPowOf2 = Integers.powerOfTwoUsingLog(a);
            after = System.nanoTime();
            System.out.println("a=" + a + " is a power of 2? " + isPowOf2);
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.gc();

            System.out.println("Power of 2 using bits.");
            before = System.nanoTime();
            isPowOf2 = Integers.powerOfTwoUsingLog(a);
            after = System.nanoTime();
            System.out.println("a=" + a + " is a power of 2? " + isPowOf2);
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.gc();
            System.out.println();
        }

        // Longs
        {
            long a = Long.MAX_VALUE;
            System.out.println("Integer to binary string using division and modulus.");
            long before = System.nanoTime();
            String result = Longs.toBinaryUsingDivideAndModulus(a);
            long after = System.nanoTime();
            System.out.println("a=" + a + " " + result);
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.gc();

            System.out.println("Integer to binary string using shifts and modulus.");
            before = System.nanoTime();
            result = Longs.toBinaryUsingShiftsAndModulus(a);
            after = System.nanoTime();
            System.out.println("a=" + a + " " + result);
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.gc();

            System.out.println("Integer to binary string using BigDecimal.");
            before = System.nanoTime();
            result = Longs.toBinaryUsingBigDecimal(a);
            after = System.nanoTime();
            System.out.println("a=" + a + " " + result);
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.gc();
        }
    }
}
