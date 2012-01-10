package com.jwetherell.algorithms;

import java.util.Random;

import com.jwetherell.algorithms.data_structures.BinarySearchTree;

public class DataStructures {
    private static final int SIZE = 10;
    
    private static int[] unsorted = null;
    
    public static void main(String[] args) {
        Random random = new Random();
        unsorted = new int[SIZE];
        int i=0;
        while (i<unsorted.length) {
            int j = random.nextInt(unsorted.length*10);
            unsorted[i++] = j;
        }

        unsorted = new int[]{36,7,5,6,3,72,78,41,37,9,8,20,90,73,56};
        BinarySearchTree bst = new BinarySearchTree(unsorted);
        System.out.println(bst.toString());

        int next = random.nextInt(unsorted.length);
        System.out.println("Adding "+unsorted[next]);
        bst.add(unsorted[next]);
        System.out.println(bst.toString());

        next = random.nextInt(unsorted.length);
        System.out.println("Removing "+unsorted[next]);
        bst.remove(unsorted[next]);
        System.out.println(bst.toString());
    }
}
