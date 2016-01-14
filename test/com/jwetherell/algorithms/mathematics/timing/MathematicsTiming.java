package com.jwetherell.algorithms.mathematics.timing;

import java.text.DecimalFormat;

import com.jwetherell.algorithms.mathematics.Division;
import com.jwetherell.algorithms.mathematics.Multiplication;

public class MathematicsTiming {

    private static final DecimalFormat FORMAT = new DecimalFormat("#.######");

    public static void main(String[] args) {
        // MULTIPLICATION
        {
            int a = 12;
            int b = 14;
            System.out.println("Multiplication using a loop.");
            long before = System.nanoTime();
            long result = Multiplication.multiplyUsingLoop(a, b);
            long after = System.nanoTime();
            long check = Multiplication.multiplication(a, b);
            if (result != check)
                System.out.println("ERROR with a=" + a + " b=" + b + " result=" + result + " check=" + check);
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.gc();

            System.out.println("Multiplication using recursion.");
            before = System.nanoTime();
            result = Multiplication.multiplyUsingRecursion(a, b);
            after = System.nanoTime();
            check = Multiplication.multiplication(a, b);
            if (result != check)
                System.out.println("ERROR with a=" + a + " b=" + b + " result=" + result + " check=" + check);
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.gc();

            System.out.println("Multiplication using shifts.");
            before = System.nanoTime();
            result = Multiplication.multiplyUsingShift(a, b);
            after = System.nanoTime();
            check = Multiplication.multiplication(a, b);
            if (result != check)
                System.out.println("ERROR with a=" + a + " b=" + b + " result=" + result + " check=" + check);
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.gc();

            System.out.println("Multiplication using logs.");
            before = System.nanoTime();
            result = Multiplication.multiplyUsingLogs(a, b);
            after = System.nanoTime();
            check = Multiplication.multiplication(a, b);
            if (result != check)
                System.out.println("ERROR with a=" + a + " b=" + b + " result=" + result + " check=" + check);
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.out.println();
            System.gc();
        }

        {
            int a = -74;
            int b = 62;
            System.out.println("Multiplication using a loop.");
            long before = System.nanoTime();
            long result = Multiplication.multiplyUsingLoop(a, b);
            long after = System.nanoTime();
            long check = Multiplication.multiplication(a, b);
            if (result != check)
                System.out.println("ERROR with a=" + a + " b=" + b + " result=" + result + " check=" + check);
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.gc();

            System.out.println("Multiplication using recursion.");
            before = System.nanoTime();
            result = Multiplication.multiplyUsingRecursion(a, b);
            after = System.nanoTime();
            check = Multiplication.multiplication(a, b);
            if (result != check)
                System.out.println("ERROR with a=" + a + " b=" + b + " result=" + result + " check=" + check);
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.gc();

            System.out.println("Multiplication using shifts.");
            before = System.nanoTime();
            result = Multiplication.multiplyUsingShift(a, b);
            after = System.nanoTime();
            check = Multiplication.multiplication(a, b);
            if (result != check)
                System.out.println("ERROR with a=" + a + " b=" + b + " result=" + result + " check=" + check);
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.gc();

            System.out.println("Multiplication using logs.");
            before = System.nanoTime();
            result = Multiplication.multiplyUsingLogs(a, b);
            after = System.nanoTime();
            check = Multiplication.multiplication(a, b);
            if (result != check)
                System.out.println("ERROR with a=" + a + " b=" + b + " result=" + result + " check=" + check);
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.out.println();
            System.gc();
        }

        {
            int a = 84;
            int b = -79;
            System.out.println("Multiplication using a loop.");
            long before = System.nanoTime();
            long result = Multiplication.multiplyUsingLoop(a, b);
            long after = System.nanoTime();
            long check = Multiplication.multiplication(a, b);
            if (result != check)
                System.out.println("ERROR with a=" + a + " b=" + b + " result=" + result + " check=" + check);
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.gc();

            System.out.println("Multiplication using recursion.");
            before = System.nanoTime();
            result = Multiplication.multiplyUsingRecursion(a, b);
            after = System.nanoTime();
            check = Multiplication.multiplication(a, b);
            if (result != check)
                System.out.println("ERROR with a=" + a + " b=" + b + " result=" + result + " check=" + check);
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.gc();

            System.out.println("Multiplication using shifts.");
            before = System.nanoTime();
            result = Multiplication.multiplyUsingShift(a, b);
            after = System.nanoTime();
            check = Multiplication.multiplication(a, b);
            if (result != check)
                System.out.println("ERROR with a=" + a + " b=" + b + " result=" + result + " check=" + check);
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.gc();

            System.out.println("Multiplication using logs.");
            before = System.nanoTime();
            result = Multiplication.multiplyUsingLogs(a, b);
            after = System.nanoTime();
            check = Multiplication.multiplication(a, b);
            if (result != check)
                System.out.println("ERROR with a=" + a + " b=" + b + " result=" + result + " check=" + check);
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.out.println();
            System.gc();
        }

        {
            int a = -92;
            int b = -87;
            System.out.println("Multiplication using a loop.");
            long before = System.nanoTime();
            long result = Multiplication.multiplyUsingLoop(a, b);
            long after = System.nanoTime();
            long check = Multiplication.multiplication(a, b);
            if (result != check)
                System.out.println("ERROR with a=" + a + " b=" + b + " result=" + result + " check=" + check);
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.gc();

            System.out.println("Multiplication using recursion.");
            before = System.nanoTime();
            result = Multiplication.multiplyUsingRecursion(a, b);
            after = System.nanoTime();
            check = Multiplication.multiplication(a, b);
            if (result != check)
                System.out.println("ERROR with a=" + a + " b=" + b + " result=" + result + " check=" + check);
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.gc();

            System.out.println("Multiplication using shifts.");
            before = System.nanoTime();
            result = Multiplication.multiplyUsingShift(a, b);
            after = System.nanoTime();
            check = Multiplication.multiplication(a, b);
            if (result != check)
                System.out.println("ERROR with a=" + a + " b=" + b + " result=" + result + " check=" + check);
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.gc();

            System.out.println("Multiplication using logs.");
            before = System.nanoTime();
            result = Multiplication.multiplyUsingLogs(a, b);
            after = System.nanoTime();
            check = Multiplication.multiplication(a, b);
            if (result != check)
                System.out.println("ERROR with a=" + a + " b=" + b + " result=" + result + " check=" + check);
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.out.println();
            System.gc();
        }

        // DIVISION
        {
            int a = 9;
            int b = 3;
            System.out.println("Division using a loop.");
            long before = System.nanoTime();
            long result = Division.divisionUsingLoop(a, b);
            long after = System.nanoTime();
            long check = Division.division(a, b);
            if (result != check)
                System.out.println("ERROR with a=" + a + " b=" + b + " result=" + result + " check=" + check);
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.gc();

            System.out.println("Division using recursion.");
            before = System.nanoTime();
            result = Division.divisionUsingRecursion(a, b);
            after = System.nanoTime();
            check = Division.division(a, b);
            if (result != check)
                System.out.println("ERROR with a=" + a + " b=" + b + " result=" + result + " check=" + check);
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.gc();

            System.out.println("Division using shifts.");
            before = System.nanoTime();
            result = Division.divisionUsingShift(a, b);
            after = System.nanoTime();
            check = Division.division(a, b);
            if (result != check)
                System.out.println("ERROR with a=" + a + " b=" + b + " result=" + result + " check=" + check);
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.gc();

            System.out.println("Division using logs.");
            before = System.nanoTime();
            result = Division.divisionUsingLogs(a, b);
            after = System.nanoTime();
            check = Division.division(a, b);
            if (result != check)
                System.out.println("ERROR with a=" + a + " b=" + b + " result=" + result + " check=" + check);
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.gc();

            System.out.println("Division using multiplication.");
            before = System.nanoTime();
            result = Division.divisionUsingMultiplication(a, b);
            after = System.nanoTime();
            check = Division.division(a, b);
            if (result != check)
                System.out.println("ERROR with a=" + a + " b=" + b + " result=" + result + " check=" + check);
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.out.println();
            System.gc();
        }

        {
            int a = -54;
            int b = 6;
            System.out.println("Division using a loop.");
            long before = System.nanoTime();
            long result = Division.divisionUsingLoop(a, b);
            long after = System.nanoTime();
            long check = Division.division(a, b);
            if (result != check)
                System.out.println("ERROR with a=" + a + " b=" + b + " result=" + result + " check=" + check);
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.gc();

            System.out.println("Division using recursion.");
            before = System.nanoTime();
            result = Division.divisionUsingRecursion(a, b);
            after = System.nanoTime();
            check = Division.division(a, b);
            if (result != check)
                System.out.println("ERROR with a=" + a + " b=" + b + " result=" + result + " check=" + check);
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.gc();

            System.out.println("Division using shifts.");
            before = System.nanoTime();
            result = Division.divisionUsingShift(a, b);
            after = System.nanoTime();
            check = Division.division(a, b);
            if (result != check)
                System.out.println("ERROR with a=" + a + " b=" + b + " result=" + result + " check=" + check);
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.gc();

            System.out.println("Division using logs.");
            before = System.nanoTime();
            result = Division.divisionUsingLogs(a, b);
            after = System.nanoTime();
            check = Division.division(a, b);
            if (result != check)
                System.out.println("ERROR with a=" + a + " b=" + b + " result=" + result + " check=" + check);
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.gc();

            System.out.println("Division using multiplication.");
            before = System.nanoTime();
            result = Division.divisionUsingMultiplication(a, b);
            after = System.nanoTime();
            check = Division.division(a, b);
            if (result != check)
                System.out.println("ERROR with a=" + a + " b=" + b + " result=" + result + " check=" + check);
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.out.println();
            System.gc();
        }

        {
            int a = 98;
            int b = -7;
            System.out.println("Division using a loop.");
            long before = System.nanoTime();
            long result = Division.divisionUsingLoop(a, b);
            long after = System.nanoTime();
            long check = Division.division(a, b);
            if (result != check)
                System.out.println("ERROR with a=" + a + " b=" + b + " result=" + result + " check=" + check);
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.gc();

            System.out.println("Division using recursion.");
            before = System.nanoTime();
            result = Division.divisionUsingRecursion(a, b);
            after = System.nanoTime();
            check = Division.division(a, b);
            if (result != check)
                System.out.println("ERROR with a=" + a + " b=" + b + " result=" + result + " check=" + check);
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.gc();

            System.out.println("Division using shifts.");
            before = System.nanoTime();
            result = Division.divisionUsingShift(a, b);
            after = System.nanoTime();
            check = Division.division(a, b);
            if (result != check)
                System.out.println("ERROR with a=" + a + " b=" + b + " result=" + result + " check=" + check);
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.gc();

            System.out.println("Division using logs.");
            before = System.nanoTime();
            result = Division.divisionUsingLogs(a, b);
            after = System.nanoTime();
            check = Division.division(a, b);
            if (result != check)
                System.out.println("ERROR with a=" + a + " b=" + b + " result=" + result + " check=" + check);
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.gc();

            System.out.println("Division using multiplication.");
            before = System.nanoTime();
            result = Division.divisionUsingMultiplication(a, b);
            after = System.nanoTime();
            check = Division.division(a, b);
            if (result != check)
                System.out.println("ERROR with a=" + a + " b=" + b + " result=" + result + " check=" + check);
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.out.println();
            System.gc();
        }

        {
            int a = -568;
            int b = -15;
            System.out.println("Division using a loop.");
            long before = System.nanoTime();
            long result = Division.divisionUsingLoop(a, b);
            long after = System.nanoTime();
            long check = Division.division(a, b);
            if (result != check)
                System.out.println("ERROR with a=" + a + " b=" + b + " result=" + result + " check=" + check);
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.gc();

            System.out.println("Division using recursion.");
            before = System.nanoTime();
            result = Division.divisionUsingRecursion(a, b);
            after = System.nanoTime();
            check = Division.division(a, b);
            if (result != check)
                System.out.println("ERROR with a=" + a + " b=" + b + " result=" + result + " check=" + check);
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.gc();

            System.out.println("Division using shifts.");
            before = System.nanoTime();
            result = Division.divisionUsingShift(a, b);
            after = System.nanoTime();
            check = Division.division(a, b);
            if (result != check)
                System.out.println("ERROR with a=" + a + " b=" + b + " result=" + result + " check=" + check);
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.gc();

            System.out.println("Division using logs.");
            before = System.nanoTime();
            result = Division.divisionUsingLogs(a, b);
            after = System.nanoTime();
            check = Division.division(a, b);
            if (result != check)
                System.out.println("ERROR with a=" + a + " b=" + b + " result=" + result + " check=" + check);
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.gc();

            System.out.println("Division using multiplication.");
            before = System.nanoTime();
            result = Division.divisionUsingMultiplication(a, b);
            after = System.nanoTime();
            check = Division.division(a, b);
            if (result != check)
                System.out.println("ERROR with a=" + a + " b=" + b + " result=" + result + " check=" + check);
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.out.println();
            System.gc();
        }
    }
}
