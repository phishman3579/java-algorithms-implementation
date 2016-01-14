package com.jwetherell.algorithms.data_structures.test.common;

import com.jwetherell.algorithms.data_structures.interfaces.IQueue;

public class QueueTest {

    /**
     * In computer science, a queue is a particular kind of abstract data type or collection in which 
     * the entities in the collection are kept in order and the principal (or only) operations on the 
     * collection are the addition of entities to the rear terminal position, known as enqueue, and removal 
     * of entities from the front terminal position, known as dequeue. This makes the queue a First-In-First-Out 
     * (FIFO) data structure. In a FIFO data structure, the first element added to the queue will be the first 
     * one to be removed. 
     * 
     * http://en.wikipedia.org/wiki/Queue_(abstract_data_type)
     * 
     * @author Justin Wetherell <phishman3579@gmail.com>
     * 
     * @param queue Queue to test.
     * @param name Name used in debug.
     * @param data Test data.
     * @param invalid Invalid data which isn't in the data-structure.
     * @return Trus if the Queue passes all it's invariants.
     */
    public static <T extends Comparable<T>> boolean testQueue(IQueue<T> queue, String name,
                                                              T[] data, T _invalid) {
        for (int i = 0; i < data.length; i++) {
            T item = data[i];
            boolean added = queue.offer(item);
            if (!queue.validate() || (queue.size() != i+1)) {
                System.err.println(name+" YIKES!! " + item + " caused a size mismatch.");
                Utils.handleError(data,queue);
                return false;
            }
            if (!added || !queue.contains(item)) {
                System.err.println(name+" YIKES!! " + item + " doesn't exists but has been added.");
                Utils.handleError(data,queue);
                return false;
            }
        }

        boolean contains = queue.contains(_invalid);
        boolean removed = queue.remove(_invalid);
        if (contains || removed) {
            System.err.println(name+" invalidity check. contains=" + contains + " removed=" + removed);
            Utils.handleError(_invalid,queue);
            return false;
        }

        int size = queue.size();
        for (int i = 0; i < size; i++) {
            T item = queue.poll();
            T correct = data[i];
            if (item.compareTo(correct)!=0) {
                System.err.println(name+" YIKES!! " + item + " does not match FIFO item.");
                Utils.handleError(data,queue);
                return false;
            }
            if (!queue.validate() || (queue.size() != data.length-(i+1))) {
                System.err.println(name+" YIKES!! " + item + " caused a size mismatch.");
                Utils.handleError(data,queue);
                return false;
            }
            if (queue.contains(item)) {
                System.err.println(name+" YIKES!! " + item + " still exists but it has been remove.");
                Utils.handleError(data,queue);
                return false;
            }
        }

        // Add half, remove a quarter, add three-quarters
        int quarter = data.length/4;
        int half = data.length/2;
        int changeOver = half-quarter;
        for (int i = 0; i < half; i++) {
            T item = data[i];
            boolean added = queue.offer(item);
            if (!queue.validate() || (queue.size() != i+1)) {
                System.err.println(name+" YIKES!! " + item + " caused a size mismatch.");
                Utils.handleError(data,queue);
                return false;
            }
            if (!added || !queue.contains(item)) {
                System.err.println(name+" YIKES!! " + item + " doesn't exists but has been added.");
                Utils.handleError(data,queue);
                return false;
            }
        }
        for (int i = 0; i < quarter; i++) {
            T item = queue.poll();
            T correct = data[i];
            if (item.compareTo(correct)!=0) {
                System.err.println(name+" YIKES!! " + item + " does not match FIFO item.");
                Utils.handleError(data,queue);
                return false;
            }
            if (!queue.validate() || (queue.size() != (half-(i+1)))) {
                System.err.println(name+" YIKES!! " + item + " caused a size mismatch.");
                Utils.handleError(data,queue);
                return false;
            }
            if (queue.contains(item)) {
                System.err.println(name+" YIKES!! " + item + " still exists but it has been remove.");
                Utils.handleError(data,queue);
                return false;
            }
        }
        for (int i = 0; i < quarter; i++) {
            T item = data[i];
            boolean added = queue.offer(item);
            if (!queue.validate() || (queue.size() != ((half-quarter)+(i+1)))) {
                System.err.println(name+" YIKES!! " + item + " caused a size mismatch.");
                Utils.handleError(data,queue);
                return false;
            }
            if (!added || !queue.contains(item)) {
                System.err.println(name+" YIKES!! " + item + " doesn't exists but has been added.");
                Utils.handleError(data,queue);
                return false;
            }
        }
        for (int i = half; i < data.length; i++) {
            T item = data[i];
            boolean added = queue.offer(item);
            if (!queue.validate() || (queue.size() != (i+1))) {
                System.err.println(name+" YIKES!! " + item + " caused a size mismatch.");
                Utils.handleError(data,queue);
                return false;
            }
            if (!added || !queue.contains(item)) {
                System.err.println(name+" YIKES!! " + item + " doesn't exists but has been added.");
                Utils.handleError(data,queue);
                return false;
            }
        }
        for (int i = 0; i < data.length; i++) {
            T item = queue.poll();
            int idx = i;
            if (idx < changeOver) {
                idx = quarter+i;
            } else if (idx>=changeOver && idx<half) {
                idx = i-changeOver;
            }
            T correct = data[idx];
            if ((item.compareTo(correct)!=0)) {
                System.err.println(name+" YIKES!! " + item + " does not match FIFO item.");
                Utils.handleError(data,queue);
                return false;
            }
            if (!queue.validate() || (queue.size() != (data.length-(i+1)))) {
                System.err.println(name+" YIKES!! " + item + " caused a size mismatch.");
                Utils.handleError(data,queue);
                return false;
            }
            if (queue.contains(item)) {
                System.err.println(name+" YIKES!! " + item + " still exists but it has been remove.");
                Utils.handleError(data,queue);
                return false;
            }
        }

        if ((queue.size() != 0)) {
            System.err.println(name+" YIKES!! a size mismatch.");
            Utils.handleError(data,queue);
            return false;
        }

        return true;
    }
}
