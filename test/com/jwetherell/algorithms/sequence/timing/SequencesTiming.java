package com.jwetherell.algorithms.sequence.timing;

import java.text.DecimalFormat;

import com.jwetherell.algorithms.sequence.FibonacciSequence;
import com.jwetherell.algorithms.sequence.ArithmeticProgression;

public class SequencesTiming {

    private static final DecimalFormat FORMAT = new DecimalFormat("#.######");

    public static void main(String[] args) {
        {
            // TOTAL OF A SEQUENCE OF NUMBERS
            int start = 14;
            int length = 10000;
            System.out.println("Computing sequence total using a loop.");
            long before = System.nanoTime();
            ArithmeticProgression.sequenceTotalUsingLoop(start, length);
            long after = System.nanoTime();
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.gc();

            System.out.println("Computing sequence total using Triangular Numbers.");
            before = System.nanoTime();
            ArithmeticProgression.sequenceTotalUsingTriangularNumbers(start, length);
            after = System.nanoTime();
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.out.println();
            System.gc();
        }

        {
            // COMPUTE FIBONACCI SEQUENCE
            System.out.println("Computing Fibonacci sequence total using a loop.");
            int element = 25;
            long before = System.nanoTime();
            FibonacciSequence.fibonacciSequenceUsingLoop(element);
            long after = System.nanoTime();
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.gc();

            System.out.println("Computing Fibonacci sequence total using Recursion.");
            before = System.nanoTime();
            FibonacciSequence.fibonacciSequenceUsingRecursion(element);
            after = System.nanoTime();
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.gc();

            System.out.println("Computing Fibonacci sequence total using Matrix.");
            before = System.nanoTime();
            FibonacciSequence.fibonacciSequenceUsingMatrixMultiplication(element);
            after = System.nanoTime();
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.gc();

            System.out.println("Computing Fibonacci sequence total using Binet's formula.");
            before = System.nanoTime();
            FibonacciSequence.fibonacciSequenceUsingBinetsFormula(element);
            after = System.nanoTime();
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.out.println();
            System.gc();
        }
    }
}
