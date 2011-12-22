package com.jwetherell.algorithms.sorts;

import java.util.ArrayList;
import java.util.List;


/**
 * Shell sort.
 * Family: Exchanging.
 * Space: In-place.
 * Stable: False.
 * 
 * Average case = depends on the gap
 * Worst case = O(n * log^2 n)
 * Best case = O(n)
 * 
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public abstract class ShellSort {
    private static int[] gaps = null;
    private static int[] unsorted = null;

    private ShellSort() { }
    
    public static int[] sort(int[] shells, int[] unsorted) {
        ShellSort.unsorted = unsorted;
        ShellSort.gaps = shells;

        for (int gap : gaps) {
            //Allocate arrays
            List<List<Integer>> subarrays = new ArrayList<List<Integer>>();
            for (int i=0; i<gap; i++) {
                subarrays.add(new ArrayList<Integer>());
            }
            //Populate sub-arrays
            int i=0;
            while (i<ShellSort.unsorted.length) {
                for (int j=0; j<gap; j++) {
                    if (i>=ShellSort.unsorted.length) continue;
                    int v = ShellSort.unsorted[i++];
                    List<Integer> list = subarrays.get(j);
                    list.add(v);
                }
            }
            //Sort all sub-arrays
            sortSubarrays(subarrays);
            //Push the sub-arrays into the int array
            int k=0;
            int iter = 0;
            while (k<ShellSort.unsorted.length) {
                for (int j=0; j<gap; j++) {
                    if (k>=ShellSort.unsorted.length) continue;
                    ShellSort.unsorted[k++] = subarrays.get(j).get(iter);
                }
                iter++;
            }
        }
        
        return ShellSort.unsorted;
    }
    
    private static void sortSubarrays(List<List<Integer>> lists) {
        for (List<Integer> list : lists) {
            sort(list);
        }
    }
    
    /**
     * Insertion sort
     * 
     * @param list List to be sorted.
     */
    private static void sort(List<Integer> list) {
        for (int i=1; i<list.size(); i++) {
            for (int j=i; j>0; j--) {
                int a = list.get(j);
                int b = list.get(j-1);
                if (a < b) {
                    list.set(j-1, a);
                    list.set(j, b);
                } else {
                    break;
                }
            }
        }
    }
}
