package com.jwetherell.algorithms;

import java.util.Random;

import com.jwetherell.algorithms.data_structures.BinarySearchTree;
import com.jwetherell.algorithms.data_structures.BinaryHeap;
import com.jwetherell.algorithms.data_structures.BinaryHeap.TYPE;
import com.jwetherell.algorithms.data_structures.LinkedList;
import com.jwetherell.algorithms.data_structures.Stack;

public class DataStructures {
    private static final int SIZE = 10;
    
    private static int[] unsorted = null;
    
    public static void main(String[] args) {
        Random random = new Random();
        
        System.out.print("Array=");
        unsorted = new int[SIZE];
        int i=0;
        System.out.print("array=");
        while (i<unsorted.length) {
            int j = random.nextInt(unsorted.length*10);
            unsorted[i++] = j;
            System.out.print(j+",");
        }
        System.out.println();

        {
            // BINARY SEARCH TREE
            System.out.println("Binary search tree.");
            BinarySearchTree bst = new BinarySearchTree(unsorted);

            // Add random node
            int next = random.nextInt(unsorted.length*100);
            System.out.println("Adding a new node "+next);
            bst.add(next);
            System.out.println(bst.toString());

            // Add previously added node
            next = random.nextInt(unsorted.length);
            System.out.println("Adding a previously added node "+unsorted[next]);
            bst.add(unsorted[next]);
            System.out.println(bst.toString());

            // Remove a previously added node
            next = random.nextInt(unsorted.length);
            System.out.println("Removing a previously added node "+unsorted[next]);
            bst.remove(unsorted[next]);
            System.out.println(bst.toString());
            
            // Remove a random node (shouldn't do anything if it doesn't exist in the tree)
            next = random.nextInt(unsorted.length*100);
            System.out.println("Removing a node which isn't in the BST "+next);
            bst.remove(next);
            System.out.println(bst.toString());
            System.out.println();
        }
        
        {
            // MIN-HEAP
            System.out.println("Min-Heap.");
            BinaryHeap minHeap = new BinaryHeap(unsorted);
            System.out.println(minHeap.toString());
            
            int next = random.nextInt(unsorted.length*100);
            System.out.println("Adding a new node "+next);
            minHeap.add(next);
            System.out.println(minHeap.toString());
            
            int index = random.nextInt(unsorted.length);
            next = unsorted[index];
            System.out.println("Adding a previously added node "+next);
            minHeap.add(next);
            System.out.println(minHeap.toString());

            index = random.nextInt(unsorted.length);
            next = unsorted[index];
            System.out.println("Removing a previously added node "+next);
            minHeap.remove(next);
            System.out.println(minHeap.toString());
            
            next = random.nextInt(unsorted.length*100);
            System.out.println("Removing a node which isn't in the Heap "+next);
            minHeap.remove(next);
            System.out.println(minHeap.toString());
            System.out.println();

            // MAX-HEAP
            System.out.println("Max-Heap.");
            BinaryHeap maxHeap = new BinaryHeap(unsorted,TYPE.MAX);
            System.out.println(maxHeap.toString());
            
            next = random.nextInt(unsorted.length*100);
            System.out.println("Adding a new node "+next);
            maxHeap.add(next);
            System.out.println(maxHeap.toString());
            
            index = random.nextInt(unsorted.length);
            next = unsorted[index];
            System.out.println("Adding a previously added node "+next);
            maxHeap.add(next);
            System.out.println(maxHeap.toString());

            index = random.nextInt(unsorted.length);
            next = unsorted[index];
            System.out.println("Removing a previously added node "+next);
            maxHeap.remove(next);
            System.out.println(maxHeap.toString());
            
            next = random.nextInt(unsorted.length*100);
            System.out.println("Removing a node which isn't in the Heap "+next);
            maxHeap.remove(next);
            System.out.println(maxHeap.toString());
            System.out.println();
        }
        
        {
            // Linked List
            System.out.println("Linked List.");
            LinkedList list = new LinkedList(unsorted);
            System.out.println(list.toString());
            
            int index = 0;
            int next = unsorted[index];
            System.out.println("Removing the head of the List "+next);
            list.remove(next);
            System.out.println(list.toString());
            
            index = unsorted.length-1;
            next = unsorted[index];
            System.out.println("Removing the tail of the List "+next);
            list.remove(next);
            System.out.println(list.toString());

            next = random.nextInt(unsorted.length*100);
            System.out.println("Adding a new node "+next);
            list.add(next);
            System.out.println(list.toString());

            index = random.nextInt(unsorted.length);
            next = unsorted[index];
            System.out.println("Removing a previously added node "+next);
            list.remove(next);
            System.out.println(list.toString());
            
            next = random.nextInt(unsorted.length*100);
            System.out.println("Removing a node which isn't in the List "+next);
            list.remove(next);
            System.out.println(list.toString());

            while (list.getSize()>0) {
                int headValue = list.getHeadValue();
                list.remove(headValue);
                System.out.println("Removed the head from the list "+headValue);
                System.out.println(list.toString());
            }
            System.out.println();
        }
        
        {
            // Stack
            System.out.println("Stack.");
            Stack stack = new Stack(unsorted);
            System.out.println(stack.toString());

            int next = random.nextInt(unsorted.length*100);
            System.out.println("Pushing a new node onto the Stack "+next);
            stack.push(next);
            System.out.println(stack.toString());

            int size = stack.getSize();
            for (int j=0; j<size; j++) {
                int node = stack.pop();
                System.out.println("Popped "+node+" from the Stack.");
                System.out.println(stack.toString());
            }
            System.out.println();
        }
    }
}
