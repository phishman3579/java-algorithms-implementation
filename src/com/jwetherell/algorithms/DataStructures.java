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
        int next = random.nextInt(unsorted.length*10);
        bst.add(next);
        System.out.println(bst.toString());
        bst.remove(next);
        System.out.println(bst.toString());
    }
}
