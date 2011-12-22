package com.jwetherell.algorithms.sorts;


/**
 * Heap sort.
 * Family: Selection.
 * Space: In-place.
 * Stable: False.
 * 
 * Average case = O(n*log n)
 * Worst case = O(n*log n)
 * Best case = O(n*log n)
 * 
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class HeapSort {
    private static int[] unsorted = null;

    private HeapSort() { }

    public static int[] sort(int[] unsorted) {
        HeapSort.unsorted = unsorted;

        createHeap();
        sort();
        
        return HeapSort.unsorted;
    }

    private static void sort() {
        for (int heapsize=(unsorted.length-1); heapsize>0; heapsize--) {
            swap(0, heapsize); // swap root with the last heap element
            int i = 0; // index of the element being moved down the tree
            while (true) {
                int left = (i*2)+1;
                if (left >= heapsize) // node has no left child
                    break;
                int right = left+1;
                if (right >= heapsize) { // node has a left child, but no right child
                    if (unsorted[left] > unsorted[i]) swap(left, i);  // if left child is greater than node
                    break;
                }
                if (unsorted[left] > unsorted[i]) {
                    if (unsorted[left] > unsorted[right]) {
                        swap(left, i);
                        i = left; 
                        continue;
                    } else { // (right > left > i)
                        swap(right, i);
                        i = right; 
                        continue;
                    }
                } else { // (i > left)
                    if (unsorted[right] > unsorted[i]) {
                        swap(right, i);
                        i = right; 
                        continue;
                    } else { // (n > left) & (n > right)
                        break;
                    }
                }
            }
        }
    }
    
    private static void createHeap() {
        int size = 0;
        for (int i=0; i<unsorted.length; i++) {
            int e = unsorted[i];
            size = add(size,e);
        }
    }
    
    private static int add(int length, int element) {
        int i = length;
        unsorted[length++] = element;

        int e = unsorted[i];
        int parentIndex = ((i-1)/2);
        int parent = unsorted[parentIndex];
        while (e>parent) {
            swap(parentIndex, i);
            i = parentIndex;
            e = unsorted[i];
            parentIndex = ((i-1)/2);
            parent = unsorted[parentIndex];
        }
        return length;
    }
    
    private static void swap(int parentIndex, int childIndex) {
        int parent = unsorted[parentIndex];
        unsorted[parentIndex] = unsorted[childIndex];
        unsorted[childIndex] = parent;
    }
}
