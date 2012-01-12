package com.jwetherell.algorithms;

import java.util.Random;

import com.jwetherell.algorithms.data_structures.BinarySearchTree;
import com.jwetherell.algorithms.data_structures.BinaryHeap;
import com.jwetherell.algorithms.data_structures.BinaryHeap.TYPE;
import com.jwetherell.algorithms.data_structures.HashMap;
import com.jwetherell.algorithms.data_structures.LinkedList;
import com.jwetherell.algorithms.data_structures.Queue;
import com.jwetherell.algorithms.data_structures.Stack;

public class DataStructures {
    private static final int SIZE = 1000;
    
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
        System.out.println();

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

            /*
            while (list.getSize()>0) {
                int headValue = list.getHeadValue();
                list.remove(headValue);
                System.out.println("Removed the head "+headValue+" from the list.");
                System.out.println(list.toString());
            }
            */
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

            int node = stack.pop();
            System.out.println("Popped "+node+" from the Stack.");
            System.out.println(stack.toString());
            
            /*
            int size = stack.getSize();
            for (int j=0; j<size; j++) {
                int node = stack.pop();
                System.out.println("Popped "+node+" from the Stack.");
                System.out.println(stack.toString());
            }
            */
            System.out.println();
        }
        
        {
            // Queue
            System.out.println("Queue.");
            Queue queue = new Queue(unsorted);
            System.out.println(queue.toString());

            int next = random.nextInt(unsorted.length*100);
            System.out.println("Pushing a new node onto the Queue "+next);
            queue.enqueue(next);
            System.out.println(queue.toString());

            int node = queue.dequeue();
            System.out.println("Dequeued "+node+" from the Queue.");
            System.out.println(queue.toString());

            /*
            int size = queue.getSize();
            for (int j=0; j<size; j++) {
                int node = queue.dequeue();
                System.out.println("Dequeued "+node+" from the Queue.");
                System.out.println(queue.toString());
            }
            */
            System.out.println();
        }

        {
            // HashMap
            System.out.println("Hash Map.");
            HashMap hash = new HashMap(unsorted);
            System.out.println(hash.toString());

            int next = random.nextInt(unsorted.length*100);
            System.out.println("Putting a new node into the HashMap "+next);
            hash.put(next,next);
            System.out.println(hash.toString());

            hash.remove(next);
            System.out.println("Removed key="+next+" from the HashMap.");
            System.out.println(hash.toString());

            /*
            for (int j=0; j<unsorted.length; j++) {
                int key = unsorted[j];
                hash.remove(key);
                System.out.println("Removed key="+key+" from the HashMap.");
                System.out.println(hash.toString());
            }
            */
            System.out.println();
        }

        {
            // BINARY SEARCH TREE
            System.out.println("Binary search tree.");
            BinarySearchTree bst = new BinarySearchTree(unsorted);
            System.out.println(bst.toString());

            // Add random node
            int next = random.nextInt(unsorted.length*100);
            System.out.println("Adding a new node "+next);
            bst.add(next);
            System.out.println(bst.toString());

            // Remove a previously added node
            next = random.nextInt(unsorted.length);
            System.out.println("Removing a previously added node "+unsorted[next]);
            bst.remove(unsorted[next]);
            System.out.println(bst.toString());
            System.out.println();
        }
        
        {
            // MIN-HEAP
            System.out.println("Min-Heap.");
            BinaryHeap minHeap = new BinaryHeap(unsorted);
            System.out.println(minHeap.toString());
            
            int next = minHeap.getRootValue();
            System.out.println("Removing the root "+next);
            minHeap.remove(next);
            System.out.println(minHeap.toString());
            
            next = random.nextInt(unsorted.length*100);
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
        }
        
        {

            // MAX-HEAP
            System.out.println("Max-Heap.");
            BinaryHeap maxHeap = new BinaryHeap(unsorted,TYPE.MAX);
            System.out.println(maxHeap.toString());
            
            int next = maxHeap.getRootValue();
            System.out.println("Removing the root "+next);
            maxHeap.remove(next);
            System.out.println(maxHeap.toString());
            
            next = random.nextInt(unsorted.length*100);
            System.out.println("Adding a new node "+next);
            maxHeap.add(next);
            System.out.println(maxHeap.toString());
            
            int index = random.nextInt(unsorted.length);
            next = unsorted[index];
            System.out.println("Adding a previously added node "+next);
            maxHeap.add(next);
            System.out.println(maxHeap.toString());

            index = random.nextInt(unsorted.length);
            next = unsorted[index];
            System.out.println("Removing a previously added node "+next);
            maxHeap.remove(next);
            System.out.println(maxHeap.toString());
            System.out.println();
        }
    }
}
