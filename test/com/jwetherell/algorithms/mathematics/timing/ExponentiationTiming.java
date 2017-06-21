package com.jwetherell.algorithms.mathematics.timing;

import com.jwetherell.algorithms.mathematics.Exponentiation;

/**
 * Notice that 2^1000 is out of integer range so returned result is not correct.
 * It is a reason why exponentiation modulo is useful.
 * But it does not matter when you want to compare speed of these two algorithms.
 */
public class ExponentiationTiming {
    public static void main(String[] args) {
        System.out.println("Calculating a power using a recursive function.");
        long before = System.nanoTime();
        Exponentiation.recursiveExponentiation(2, 1000);
        long after = System.nanoTime();
        System.out.println("Computed in " + (after - before) + " ns");

        System.out.println("Calculating a power using a fast recursive function.");
        before = System.nanoTime();
        Exponentiation.fastRecursiveExponentiation(2, 1000);
        after = System.nanoTime();
        System.out.println("Computed in " + (after - before) + " ns");
    }
}
