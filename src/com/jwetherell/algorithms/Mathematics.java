package com.jwetherell.algorithms;

import java.text.DecimalFormat;

import com.jwetherell.algorithms.mathematics.Division;
import com.jwetherell.algorithms.mathematics.Knapsack;
import com.jwetherell.algorithms.mathematics.Multiplication;


public class Mathematics {

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
            if (result != check) System.out.println("ERROR with a=" + a + " b=" + b + " result=" + result + " check=" + check);
            else System.out.println(a + "x" + b + "=" + result);
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.gc();

            System.out.println("Multiplication using recursion.");
            before = System.nanoTime();
            result = Multiplication.multiplyUsingRecursion(a, b);
            after = System.nanoTime();
            check = Multiplication.multiplication(a, b);
            if (result != check) System.out.println("ERROR with a=" + a + " b=" + b + " result=" + result + " check=" + check);
            else System.out.println(a + "x" + b + "=" + result);
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.gc();

            System.out.println("Multiplication using shifts.");
            before = System.nanoTime();
            result = Multiplication.multiplyUsingShift(a, b);
            after = System.nanoTime();
            check = Multiplication.multiplication(a, b);
            if (result != check) System.out.println("ERROR with a=" + a + " b=" + b + " result=" + result + " check=" + check);
            else System.out.println(a + "x" + b + "=" + result);
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.gc();

            System.out.println("Multiplication using logs.");
            before = System.nanoTime();
            result = Multiplication.multiplyUsingLogs(a, b);
            after = System.nanoTime();
            check = Multiplication.multiplication(a, b);
            if (result != check) System.out.println("ERROR with a=" + a + " b=" + b + " result=" + result + " check=" + check);
            else System.out.println(a + "x" + b + "=" + result);
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
            if (result != check) System.out.println("ERROR with a=" + a + " b=" + b + " result=" + result + " check=" + check);
            else System.out.println(a + "x" + b + "=" + result);
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.gc();

            System.out.println("Multiplication using recursion.");
            before = System.nanoTime();
            result = Multiplication.multiplyUsingRecursion(a, b);
            after = System.nanoTime();
            check = Multiplication.multiplication(a, b);
            if (result != check) System.out.println("ERROR with a=" + a + " b=" + b + " result=" + result + " check=" + check);
            else System.out.println(a + "x" + b + "=" + result);
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.gc();

            System.out.println("Multiplication using shifts.");
            before = System.nanoTime();
            result = Multiplication.multiplyUsingShift(a, b);
            after = System.nanoTime();
            check = Multiplication.multiplication(a, b);
            if (result != check) System.out.println("ERROR with a=" + a + " b=" + b + " result=" + result + " check=" + check);
            else System.out.println(a + "x" + b + "=" + result);
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.gc();

            System.out.println("Multiplication using logs.");
            before = System.nanoTime();
            result = Multiplication.multiplyUsingLogs(a, b);
            after = System.nanoTime();
            check = Multiplication.multiplication(a, b);
            if (result != check) System.out.println("ERROR with a=" + a + " b=" + b + " result=" + result + " check=" + check);
            else System.out.println(a + "x" + b + "=" + result);
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
            if (result != check) System.out.println("ERROR with a=" + a + " b=" + b + " result=" + result + " check=" + check);
            else System.out.println(a + "x" + b + "=" + result);
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.gc();

            System.out.println("Multiplication using recursion.");
            before = System.nanoTime();
            result = Multiplication.multiplyUsingRecursion(a, b);
            after = System.nanoTime();
            check = Multiplication.multiplication(a, b);
            if (result != check) System.out.println("ERROR with a=" + a + " b=" + b + " result=" + result + " check=" + check);
            else System.out.println(a + "x" + b + "=" + result);
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.gc();

            System.out.println("Multiplication using shifts.");
            before = System.nanoTime();
            result = Multiplication.multiplyUsingShift(a, b);
            after = System.nanoTime();
            check = Multiplication.multiplication(a, b);
            if (result != check) System.out.println("ERROR with a=" + a + " b=" + b + " result=" + result + " check=" + check);
            else System.out.println(a + "x" + b + "=" + result);
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.gc();

            System.out.println("Multiplication using logs.");
            before = System.nanoTime();
            result = Multiplication.multiplyUsingLogs(a, b);
            after = System.nanoTime();
            check = Multiplication.multiplication(a, b);
            if (result != check) System.out.println("ERROR with a=" + a + " b=" + b + " result=" + result + " check=" + check);
            else System.out.println(a + "x" + b + "=" + result);
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
            if (result != check) System.out.println("ERROR with a=" + a + " b=" + b + " result=" + result + " check=" + check);
            else System.out.println(a + "x" + b + "=" + result);
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.gc();

            System.out.println("Multiplication using recursion.");
            before = System.nanoTime();
            result = Multiplication.multiplyUsingRecursion(a, b);
            after = System.nanoTime();
            check = Multiplication.multiplication(a, b);
            if (result != check) System.out.println("ERROR with a=" + a + " b=" + b + " result=" + result + " check=" + check);
            else System.out.println(a + "x" + b + "=" + result);
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.gc();

            System.out.println("Multiplication using shifts.");
            before = System.nanoTime();
            result = Multiplication.multiplyUsingShift(a, b);
            after = System.nanoTime();
            check = Multiplication.multiplication(a, b);
            if (result != check) System.out.println("ERROR with a=" + a + " b=" + b + " result=" + result + " check=" + check);
            else System.out.println(a + "x" + b + "=" + result);
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.gc();

            System.out.println("Multiplication using logs.");
            before = System.nanoTime();
            result = Multiplication.multiplyUsingLogs(a, b);
            after = System.nanoTime();
            check = Multiplication.multiplication(a, b);
            if (result != check) System.out.println("ERROR with a=" + a + " b=" + b + " result=" + result + " check=" + check);
            else System.out.println(a + "x" + b + "=" + result);
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
            if (result != check) System.out.println("ERROR with a=" + a + " b=" + b + " result=" + result + " check=" + check);
            else System.out.println(a + "/" + b + "=" + result);
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.gc();

            System.out.println("Division using recursion.");
            before = System.nanoTime();
            result = Division.divisionUsingRecursion(a, b);
            after = System.nanoTime();
            check = Division.division(a, b);
            if (result != check) System.out.println("ERROR with a=" + a + " b=" + b + " result=" + result + " check=" + check);
            else System.out.println(a + "/" + b + "=" + result);
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.gc();

            System.out.println("Division using shifts.");
            before = System.nanoTime();
            result = Division.divisionUsingShift(a, b);
            after = System.nanoTime();
            check = Division.division(a, b);
            if (result != check) System.out.println("ERROR with a=" + a + " b=" + b + " result=" + result + " check=" + check);
            else System.out.println(a + "/" + b + "=" + result);
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.gc();

            System.out.println("Division using logs.");
            before = System.nanoTime();
            result = Division.divisionUsingLogs(a, b);
            after = System.nanoTime();
            check = Division.division(a, b);
            if (result != check) System.out.println("ERROR with a=" + a + " b=" + b + " result=" + result + " check=" + check);
            else System.out.println(a + "/" + b + "=" + result);
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.gc();

            System.out.println("Division using multiplication.");
            before = System.nanoTime();
            result = Division.divisionUsingMultiplication(a, b);
            after = System.nanoTime();
            check = Division.division(a, b);
            if (result != check) System.out.println("ERROR with a=" + a + " b=" + b + " result=" + result + " check=" + check);
            else System.out.println(a + "/" + b + "=" + result);
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
            if (result != check) System.out.println("ERROR with a=" + a + " b=" + b + " result=" + result + " check=" + check);
            else System.out.println(a + "/" + b + "=" + result);
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.gc();

            System.out.println("Division using recursion.");
            before = System.nanoTime();
            result = Division.divisionUsingRecursion(a, b);
            after = System.nanoTime();
            check = Division.division(a, b);
            if (result != check) System.out.println("ERROR with a=" + a + " b=" + b + " result=" + result + " check=" + check);
            else System.out.println(a + "/" + b + "=" + result);
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.gc();

            System.out.println("Division using shifts.");
            before = System.nanoTime();
            result = Division.divisionUsingShift(a, b);
            after = System.nanoTime();
            check = Division.division(a, b);
            if (result != check) System.out.println("ERROR with a=" + a + " b=" + b + " result=" + result + " check=" + check);
            else System.out.println(a + "/" + b + "=" + result);
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.gc();

            System.out.println("Division using logs.");
            before = System.nanoTime();
            result = Division.divisionUsingLogs(a, b);
            after = System.nanoTime();
            check = Division.division(a, b);
            if (result != check) System.out.println("ERROR with a=" + a + " b=" + b + " result=" + result + " check=" + check);
            else System.out.println(a + "/" + b + "=" + result);
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.gc();

            System.out.println("Division using multiplication.");
            before = System.nanoTime();
            result = Division.divisionUsingMultiplication(a, b);
            after = System.nanoTime();
            check = Division.division(a, b);
            if (result != check) System.out.println("ERROR with a=" + a + " b=" + b + " result=" + result + " check=" + check);
            else System.out.println(a + "/" + b + "=" + result);
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
            if (result != check) System.out.println("ERROR with a=" + a + " b=" + b + " result=" + result + " check=" + check);
            else System.out.println(a + "/" + b + "=" + result);
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.gc();

            System.out.println("Division using recursion.");
            before = System.nanoTime();
            result = Division.divisionUsingRecursion(a, b);
            after = System.nanoTime();
            check = Division.division(a, b);
            if (result != check) System.out.println("ERROR with a=" + a + " b=" + b + " result=" + result + " check=" + check);
            else System.out.println(a + "/" + b + "=" + result);
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.gc();

            System.out.println("Division using shifts.");
            before = System.nanoTime();
            result = Division.divisionUsingShift(a, b);
            after = System.nanoTime();
            check = Division.division(a, b);
            if (result != check) System.out.println("ERROR with a=" + a + " b=" + b + " result=" + result + " check=" + check);
            else System.out.println(a + "/" + b + "=" + result);
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.gc();

            System.out.println("Division using logs.");
            before = System.nanoTime();
            result = Division.divisionUsingLogs(a, b);
            after = System.nanoTime();
            check = Division.division(a, b);
            if (result != check) System.out.println("ERROR with a=" + a + " b=" + b + " result=" + result + " check=" + check);
            else System.out.println(a + "/" + b + "=" + result);
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.gc();

            System.out.println("Division using multiplication.");
            before = System.nanoTime();
            result = Division.divisionUsingMultiplication(a, b);
            after = System.nanoTime();
            check = Division.division(a, b);
            if (result != check) System.out.println("ERROR with a=" + a + " b=" + b + " result=" + result + " check=" + check);
            else System.out.println(a + "/" + b + "=" + result);
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
            if (result != check) System.out.println("ERROR with a=" + a + " b=" + b + " result=" + result + " check=" + check);
            else System.out.println(a + "/" + b + "=" + result);
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.gc();

            System.out.println("Division using recursion.");
            before = System.nanoTime();
            result = Division.divisionUsingRecursion(a, b);
            after = System.nanoTime();
            check = Division.division(a, b);
            if (result != check) System.out.println("ERROR with a=" + a + " b=" + b + " result=" + result + " check=" + check);
            else System.out.println(a + "/" + b + "=" + result);
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.gc();

            System.out.println("Division using shifts.");
            before = System.nanoTime();
            result = Division.divisionUsingShift(a, b);
            after = System.nanoTime();
            check = Division.division(a, b);
            if (result != check) System.out.println("ERROR with a=" + a + " b=" + b + " result=" + result + " check=" + check);
            else System.out.println(a + "/" + b + "=" + result);
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.gc();

            System.out.println("Division using logs.");
            before = System.nanoTime();
            result = Division.divisionUsingLogs(a, b);
            after = System.nanoTime();
            check = Division.division(a, b);
            if (result != check) System.out.println("ERROR with a=" + a + " b=" + b + " result=" + result + " check=" + check);
            else System.out.println(a + "/" + b + "=" + result);
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.gc();

            System.out.println("Division using multiplication.");
            before = System.nanoTime();
            result = Division.divisionUsingMultiplication(a, b);
            after = System.nanoTime();
            check = Division.division(a, b);
            if (result != check) System.out.println("ERROR with a=" + a + " b=" + b + " result=" + result + " check=" + check);
            else System.out.println(a + "/" + b + "=" + result);
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.out.println();
            System.gc();
        }

        {
            int[] values = { 7, 4, 8, 6, 2, 5 };
            int[] weights = { 2, 3, 5, 4, 2, 3 };
            int capacity = 9;
            System.out.println("Knapsack problem.");
            long before = System.nanoTime();
            int[] result = Knapsack.zeroOneKnapsack(values, weights, capacity);
            long after = System.nanoTime();
            System.out.println("result=" + getIntegerString(result));
            System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
            System.gc();
        }
    }

    private static final String getIntegerString(int[] result) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < result.length; i++) {
            int v = result[i];
            builder.append(v);
            if (i != result.length - 1) builder.append(", ");
        }
        return builder.toString();
    }

}
