package com.jwetherell.algorithms;

import java.text.DecimalFormat;

import com.jwetherell.algorithms.numbers.Integers;
import com.jwetherell.algorithms.numbers.Longs;


public class Numbers {
    private static final DecimalFormat FORMAT = new DecimalFormat("#.######");

    public static void main(String[] args) {
        //Integers
        {
            int a=Integer.MAX_VALUE;
            System.out.println("Integer to binary string using division and modulus.");
            long before = System.nanoTime();
            String result = Integers.toBinaryUsingDivideAndModulus(a);
            long after = System.nanoTime();
            System.out.println("a="+a+" "+result);
            System.out.println("Computed in "+FORMAT.format(after-before)+" ns");
            System.gc();
            
            System.out.println("Integer to binary string using shifts and modulus.");
            before = System.nanoTime();
            result = Integers.toBinaryUsingShiftsAndModulus(a);
            after = System.nanoTime();
            System.out.println("a="+a+" "+result);
            System.out.println("Computed in "+FORMAT.format(after-before)+" ns");
            System.gc();
            
            System.out.println("Integer to binary string using BigDecimal.");
            before = System.nanoTime();
            result = Integers.toBinaryUsingBigDecimal(a);
            after = System.nanoTime();
            System.out.println("a="+a+" "+result);
            System.out.println("Computed in "+FORMAT.format(after-before)+" ns");
            System.gc();
        }
        
        //Longs
        {
            long a=Long.MAX_VALUE;
            System.out.println("Integer to binary string using division and modulus.");
            long before = System.nanoTime();
            String result = Longs.toBinaryUsingDivideAndModulus(a);
            long after = System.nanoTime();
            System.out.println("a="+a+" "+result);
            System.out.println("Computed in "+FORMAT.format(after-before)+" ns");
            System.gc();
            
            System.out.println("Integer to binary string using shifts and modulus.");
            before = System.nanoTime();
            result = Longs.toBinaryUsingShiftsAndModulus(a);
            after = System.nanoTime();
            System.out.println("a="+a+" "+result);
            System.out.println("Computed in "+FORMAT.format(after-before)+" ns");
            System.gc();
            
            System.out.println("Integer to binary string using BigDecimal.");
            before = System.nanoTime();
            result = Longs.toBinaryUsingBigDecimal(a);
            after = System.nanoTime();
            System.out.println("a="+a+" "+result);
            System.out.println("Computed in "+FORMAT.format(after-before)+" ns");
            System.gc();
        }
    }
}
