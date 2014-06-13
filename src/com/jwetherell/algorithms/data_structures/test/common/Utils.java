package com.jwetherell.algorithms.data_structures.test.common;

import java.util.Arrays;
import java.util.Random;

public class Utils {

    public static enum Type {Integer, String};

    public static void handleError(Object obj) {
        System.err.println(obj.toString());
        throw new RuntimeException("Error in test.");
    }

    private static final Random RANDOM = new Random();

    public static TestData generateTestData(int data_size) {
        TestData data = new TestData(data_size);

        StringBuilder builder = new StringBuilder();
        data.unsorted = new Integer[data_size];
        java.util.Set<Integer> set = new java.util.HashSet<Integer>();
        builder.append("Array=");
        for (int i = 0; i < data_size; i++) {
            Integer j = RANDOM.nextInt(data.random_size);
            // Make sure there are no duplicates
            boolean found = true;
            while (found) {
                if (set.contains(j)) {
                    j = RANDOM.nextInt(data.random_size);
                } else {
                    data.unsorted[i] = j;
                    set.add(j);
                    found = false;
                }
            }
            data.unsorted[i] = j;
            if (i!=data_size-1) builder.append(j).append(',');
        }
        set.clear();
        set = null;
        builder.append('\n');
        data.string = builder.toString();

        data.sorted = Arrays.copyOf(data.unsorted, data.unsorted.length);
        Arrays.sort(data.sorted);

        return data;
    }

    public static class TestData {
        public int random_size = 0;
        public int invalid = 0;
        public Integer[] unsorted = null;
        public Integer[] sorted = null;
        public String string = null;

        public TestData(int size) {
            this.random_size = 1000 * size;
            this.invalid = random_size + 10;
        }
    }
}
