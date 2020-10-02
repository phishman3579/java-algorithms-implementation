
import java.io.*;
import java.util.*;
public class DutchNationalFlagSort {
    public static void sortColors(int[] nums) {
        int start=0;
        int end=nums.length-1;
        int i=0;
        while(i<=end && start<end)
        {
            if(nums[i]==0)
            {
                nums[i]=nums[start];
                nums[start]=0;
                start++;
                i++;
            }
            else if(nums[i]==2)
            {
                nums[i]=nums[end];
                nums[end]=2;
                end--;
            }
            else
                i++;
        }
        
    }
    public static void main(String[] args)
    {
        Scanner sc=new Scanner(System.in);
        int n;
        System.out.println("Enter number of elements");
        n=sc.nextInt();
        int arr[]=new int[n];
        System.out.println("Enter elements (only 0s,1s and 2s)");
        for(int i=0;i<n;i++)
        {
            System.out.println("Enter element (0 or 1 or 2)");
            arr[i]=sc.nextInt();
        }
        sortColors(arr);
        System.out.println("The elemets after sorting are : ");
        for(int i:arr)
            System.out.println(i);
    }
}