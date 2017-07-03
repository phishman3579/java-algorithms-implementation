package com.jwetherell.algorithms.data_structures.test.common;

import java.util.Arrays;

import com.jwetherell.algorithms.data_structures.BinaryHeap;
import com.jwetherell.algorithms.data_structures.interfaces.IHeap;

public class HeapTest {

    public static <T extends Comparable<T>> boolean testHeap(BinaryHeap.Type heapType, IHeap<T> heap, Class<T> type, String name, 
                                                             Integer[] unsorted, Integer[] sorted, Integer _invalid) {
        for (int i = 0; i < unsorted.length; i++) {
            T item = Utils.parseT(unsorted[i], type);
            boolean added = heap.add(item);
            if (!heap.validate() || (heap.size() != i+1)) {
                System.err.println(name+" YIKES!! " + item + " caused a size mismatch.");
                Utils.handleError(sorted,heap);
                return false;
            }
            if (!added || !heap.contains(item)) {
                System.err.println(name+" YIKES!! " + item + " doesn't exists but has been added.");
                Utils.handleError(sorted,heap);
                return false;
            }
        }

        T invalid = Utils.parseT(_invalid, type);
        boolean contains = heap.contains(invalid);
        T removed = heap.remove(invalid);
        if (contains || (removed!=null)) {
            System.err.println(name+" invalidity check. contains=" + contains + " removed=" + removed);
            Utils.handleError(_invalid,heap);
            return false;
        }

        int size = heap.size();
        for (int i = 0; i < size; i++) {
            T item = heap.removeHead();
            Integer _correct = ((heapType == BinaryHeap.Type.MIN)?sorted[i]:sorted[sorted.length-(i+1)]);
            T correct = Utils.parseT(_correct, type);
            if (item.compareTo(correct)!=0) {
                System.err.println(name+" YIKES!! " + item + " does not match heap item.");
                Utils.handleError(unsorted,heap);
                return false;
            }
            if (!heap.validate() || (heap.size() != unsorted.length-(i+1))) {
                System.err.println(name+" YIKES!! " + item + " caused a size mismatch.");
                Utils.handleError(unsorted,heap);
                return false;
            }
            if (heap.contains(item)) {
                System.err.println(name+" YIKES!! " + item + " still exists but it has been remove.");
                Utils.handleError(unsorted,heap);
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
            quarterArray[i] = ((heapType == BinaryHeap.Type.MIN)?halfArray[i]:halfArray[halfArray.length-(i+1)]);
            sortedQuarterArray[i] = quarterArray[i];
        }
        Arrays.sort(sortedQuarterArray);
        int idx = 0;
        Integer[] threeQuartersArray = new Integer[unsorted.length-(half-quarter)];
        for (Integer i : unsorted) {
            int index = Arrays.binarySearch(sortedQuarterArray, i);
            if (heapType == BinaryHeap.Type.MIN) {
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
            T item = Utils.parseT(unsorted[i], type);
            boolean added = heap.add(item);
            if (!heap.validate() || (heap.size() != i+1)) {
                System.err.println(name+" YIKES!! " + item + " caused a size mismatch.");
                Utils.handleError(unsorted,heap);
                return false;
            }
            if (!added || !heap.contains(item)) {
                System.err.println(name+" YIKES!! " + item + " doesn't exists but has been added.");
                Utils.handleError(unsorted,heap);
                return false;
            }
        }
        for (int i = 0; i < quarter; i++) {
            T item = heap.removeHead();
            Integer _correct = quarterArray[i];
            T correct = Utils.parseT(_correct, type);
            if (item.compareTo(correct)!=0) {
                System.err.println(name+" YIKES!! " + item + " does not match heap item.");
                Utils.handleError(unsorted,heap);
                return false;
            }
            if (!heap.validate() || (heap.size() != half-(i+1))) {
                System.err.println(name+" YIKES!! " + item + " caused a size mismatch.");
                Utils.handleError(unsorted,heap);
                return false;
            }
            if (heap.contains(item)) {
                System.err.println(name+" YIKES!! " + item + " still exists but it has been remove.");
                Utils.handleError(unsorted,heap);
                return false;
            }
        }
        for (int i = 0; i < threeQuartersArray.length; i++) {
            Integer _item = threeQuartersArray[i];
            T item = Utils.parseT(_item, type);
            boolean added = heap.add(item);
            if (!heap.validate() || (heap.size() != (half-quarter)+(i+1))) {
                System.err.println(name+" YIKES!! " + item + " caused a size mismatch.");
                Utils.handleError(unsorted,heap);
                return false;
            }
            if (!added || !heap.contains(item)) {
                System.err.println(name+" YIKES!! " + item + " doesn't exists but has been added.");
                Utils.handleError(unsorted,heap);
                return false;
            }
        }

        for (int i = 0; i < sorted.length; i++) {
            T item = heap.removeHead();
            Integer _correct = ((heapType == BinaryHeap.Type.MIN)?sorted[i]:sorted[sorted.length-(i+1)]);
            T correct = Utils.parseT(_correct, type);
            if (item.compareTo(correct)!=0) {
                System.err.println(name+" YIKES!! " + item + " does not match heap item.");
                Utils.handleError(sorted,heap);
                return false;
            }
            if (!heap.validate() || (heap.size() != sorted.length-(i+1))) {
                System.err.println(name+" YIKES!! " + item + " caused a size mismatch.");
                Utils.handleError(sorted,heap);
                return false;
            }
            if (heap.contains(item)) {
                System.err.println(name+" YIKES!! " + item + " still exists but it has been remove.");
                Utils.handleError(sorted,heap);
                return false;
            }
        }
        if (heap.size() != 0) {
            System.err.println(name+" YIKES!! a size mismatch.");
            Utils.handleError(sorted,heap);
            return false;
        }

        return true;
    }
}
