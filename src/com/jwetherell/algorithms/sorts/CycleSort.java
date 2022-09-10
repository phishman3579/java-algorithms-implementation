// java program to check implement cycle sort
import java.util.*;
public class MissingNumber {
    public static void main(String[] args)
    {
        int[] arr = { 3, 2, 4, 5, 1 };
        int n = arr.length;
        System.out.println("Before sort :");
        System.out.println(Arrays.toString(arr));
        CycleSort(arr, n);
         
    }
 
    static void CycleSort(int[] arr, int n)
    {
        int i = 0;
        while (i < n) {
            // as array is of 1 based indexing so the
            // correct position or index number of each
            // element is element-1 i.e. 1 will be at 0th
            // index similarly 2 correct index will 1 so
            // on...
            int correctpos = arr[i] - 1;
            if (arr[i] < n && arr[i] != arr[correctpos]) {
                // if array element should be lesser than
                // size and array element should not be at
                // its correct position then only swap with
                // its correct position or index value
                swap(arr, i, correctpos);
            }
            else {
                // if element is at its correct position
                // just increment i and check for remaining
                // array elements
                i++;
            }
        }
            System.out.println("After sort :  ");
            System.out.print(Arrays.toString(arr));
         
         
    }
 
    static void swap(int[] arr, int i, int correctpos)
    {
    // swap elements with their correct indexes
        int temp = arr[i];
        arr[i] = arr[correctpos];
        arr[correctpos] = temp;
    }
}
