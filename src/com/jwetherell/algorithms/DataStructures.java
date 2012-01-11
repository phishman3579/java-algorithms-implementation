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

        BinarySearchTree bst = new BinarySearchTree(unsorted);
        System.out.println(bst.toString());

        // Add random node
        int next = random.nextInt(unsorted.length*100);
        System.out.println("Adding "+next);
        bst.add(next);
        System.out.println(bst.toString());

        // Add previously added node
        next = random.nextInt(unsorted.length);
        System.out.println("Adding "+unsorted[next]);
        bst.add(unsorted[next]);
        System.out.println(bst.toString());

        // Remove a perviously added node
        next = random.nextInt(unsorted.length);
        System.out.println("Removing "+unsorted[next]);
        bst.remove(unsorted[next]);
        System.out.println(bst.toString());
    }
}
