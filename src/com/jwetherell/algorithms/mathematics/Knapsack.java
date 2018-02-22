package com.jwetherell.algorithms.mathematics;

import java.util.ArrayList;
import java.util.List;

/**
 * The knapsack problem or rucksack problem is a problem in combinatorial optimization: Given a set of items, each with a weight and a value, determine the number of each item to include in a 
 * collection so that the total weight is less than or equal to a given limit and the total value is as large as possible. It derives its name from the problem faced by someone who is constrained 
 * by a fixed-size knapsack and must fill it with the most valuable items.
 * <p>
 * @see <a href="https://en.wikipedia.org/wiki/Knapsack_problem">Knapsack Problem (Wikipedia)</a>
 * <br>
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class Knapsack {

    public static final int[] zeroOneKnapsack(int[] values, int[] weights, int capacity) {
        if (weights.length != values.length)
            return null;

        int height = weights.length + 1; // weights==values
        int width = capacity + 1;
        int[][] output = new int[height][width];
        for (int i = 1; i < height; i++) {
            int index = i - 1;
            for (int j = 1; j < width; j++) {
                if (i == 0 || j == 0) {
                    output[i][j] = 0;
                } else {
                    if (weights[index] > j) {
                        output[i][j] = output[i - 1][j];
                    } else {
                        int v = values[index] + output[i - 1][j - weights[index]];
                        output[i][j] = Math.max(output[i - 1][j], v);
                    }
                }
            }
        }

        final List<Integer> list = new ArrayList<Integer>();
        int i = height - 1;
        int j = width - 1;
        while (i != 0 && j != 0) {
            int current = output[i][j];
            int above = output[i - 1][j];
            if (current == above) {
                i -= 1;
            } else {
                i -= 1;
                j -= weights[i];
                list.add(i);
            }
        }

        int count = 0;
        int[] result = new int[list.size()];
        for (Object obj : list.toArray()) {
            if (obj instanceof Integer)
                result[count++] = (Integer) obj;
        }

        return result;
    }
}
