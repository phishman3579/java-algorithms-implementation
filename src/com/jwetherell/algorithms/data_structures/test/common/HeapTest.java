package com.jwetherell.algorithms.data_structures.test.common;

import java.util.Arrays;

import com.jwetherell.algorithms.data_structures.BinaryHeap;
import com.jwetherell.algorithms.data_structures.IHeap;

public class HeapTest {

    @SuppressWarnings("unchecked")
    public static <T extends Comparable<T>> boolean testHeap(IHeap<T> heap, String name, BinaryHeap.Type type,
                                                             Integer[] unsorted, Integer[] sorted, Integer INVALID) {
        for (int i = 0; i < unsorted.length; i++) {
            T item = (T)unsorted[i];
            boolean added = heap.add(item);
            if (!heap.validate() || (heap.size() != i+1)) {
                System.err.println(name+" YIKES!! " + item + " caused a size mismatch.");
                Utils.handleError(heap);
                return false;
            }
            if (!added || !heap.contains(item)) {
                System.err.println(name+" YIKES!! " + item + " doesn't exists but has been added.");
                Utils.handleError(heap);
                return false;
            }
        }

        boolean contains = heap.contains((T)INVALID);
        T removed = heap.remove((T)INVALID);
        if (contains || (removed!=null)) {
            System.err.println(name+" invalidity check. contains=" + contains + " removed=" + removed);
            Utils.handleError(heap);
            return false;
        }

        int size = heap.size();
        for (int i = 0; i < size; i++) {
            T item = heap.removeHead();
            T correct = (T)((type == BinaryHeap.Type.MIN)?sorted[i]:sorted[sorted.length-(i+1)]);
            if (item.compareTo(correct)!=0) {
                System.err.println(name+" YIKES!! " + item + " does not match heap item.");
                Utils.handleError(heap);
                return false;
            }
            if (!heap.validate() || (heap.size() != unsorted.length-(i+1))) {
                System.err.println(name+" YIKES!! " + item + " caused a size mismatch.");
                Utils.handleError(heap);
                return false;
            }
            if (heap.contains(item)) {
                System.err.println(name+" YIKES!! " + item + " still exists but it has been remove.");
                Utils.handleError(heap);
                return false;
            }
        }

        // Add half, remove a quarter, add three-quarters, remove all
        int quarter = unsorted.length/4;
        int half = unsorted.length/2;
        Integer[] halfArray = Arrays.copyOf(unsorted, half);
        Arrays.sort(halfArray);
        Integer[] quarterArray = new Integer[quarter];
        Integer[] sortedQuarterArray = new Integer[quarter]; //Needed for binary search
        for (int i=0; i<quarter; i++) {
            quarterArray[i] = (type == BinaryHeap.Type.MIN)?halfArray[i]:halfArray[halfArray.length-(i+1)];
            sortedQuarterArray[i] = quarterArray[i];
        }
        Arrays.sort(sortedQuarterArray);
        int idx = 0;
        Integer[] threeQuartersArray = new Integer[unsorted.length-(half-quarter)];
        for (Integer i : unsorted) {
            int index = Arrays.binarySearch(sortedQuarterArray, i);
            if (type == BinaryHeap.Type.MIN) {
                if (index>=0) {
                    threeQuartersArray[idx++] = i;  
                } else {
                    index = Arrays.binarySearch(halfArray, i);
                    if (index<0) threeQuartersArray[idx++] = i;
                }
            } else {
                if (index>=0) {
                    threeQuartersArray[idx++] = i;
                } else {
                    index = Arrays.binarySearch(halfArray, i);
                    if (index<0) threeQuartersArray[idx++] = i;
                }
            }
        }
        for (int i = 0; i < half; i++) {
            T item = (T)unsorted[i];
            boolean added = heap.add(item);
            if (!heap.validate() || (heap.size() != i+1)) {
                System.err.println(name+" YIKES!! " + item + " caused a size mismatch.");
                Utils.handleError(heap);
                return false;
            }
            if (!added || !heap.contains(item)) {
                System.err.println(name+" YIKES!! " + item + " doesn't exists but has been added.");
                Utils.handleError(heap);
                return false;
            }
        }
        for (int i = 0; i < quarter; i++) {
            T item = heap.removeHead();
            T correct = (T)quarterArray[i];
            if (item.compareTo(correct)!=0) {
                System.err.println(name+" YIKES!! " + item + " does not match heap item.");
                Utils.handleError(heap);
                return false;
            }
            if (!heap.validate() || (heap.size() != half-(i+1))) {
                System.err.println(name+" YIKES!! " + item + " caused a size mismatch.");
                Utils.handleError(heap);
                return false;
            }
            if (heap.contains(item)) {
                System.err.println(name+" YIKES!! " + item + " still exists but it has been remove.");
                Utils.handleError(heap);
                return false;
            }
        }
        for (int i = 0; i < threeQuartersArray.length; i++) {
            T item = (T)threeQuartersArray[i];
            boolean added = heap.add(item);
            if (!heap.validate() || (heap.size() != (half-quarter)+(i+1))) {
                System.err.println(name+" YIKES!! " + item + " caused a size mismatch.");
                Utils.handleError(heap);
                return false;
            }
            if (!added || !heap.contains(item)) {
                System.err.println(name+" YIKES!! " + item + " doesn't exists but has been added.");
                Utils.handleError(heap);
                return false;
            }
        }
        for (int i = 0; i < sorted.length; i++) {
            T item = heap.removeHead();
            T correct = (T)((type == BinaryHeap.Type.MIN)?sorted[i]:sorted[sorted.length-(i+1)]);
            if (item.compareTo(correct)!=0) {
                System.err.println(name+" YIKES!! " + item + " does not match heap item.");
                Utils.handleError(heap);
                return false;
            }
            if (!heap.validate() || (heap.size() != sorted.length-(i+1))) {
                System.err.println(name+" YIKES!! " + item + " caused a size mismatch.");
                Utils.handleError(heap);
                return false;
            }
            if (heap.contains(item)) {
                System.err.println(name+" YIKES!! " + item + " still exists but it has been remove.");
                Utils.handleError(heap);
                return false;
            }
        }

        if (heap.size() != 0) {
            System.err.println(name+" YIKES!! a size mismatch.");
            Utils.handleError(heap);
            return false;
        }

        return true;
    }
}
