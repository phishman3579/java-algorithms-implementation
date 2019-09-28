package com.jwetherell.algorithms.sorts.test;

import static org.junit.Assert.assertTrue;

import java.util.Random;

import org.junit.Test;

import com.jwetherell.algorithms.sorts.AmericanFlagSort;
import com.jwetherell.algorithms.sorts.BubbleSort;
import com.jwetherell.algorithms.sorts.CountingSort;
import com.jwetherell.algorithms.sorts.HeapSort;
import com.jwetherell.algorithms.sorts.InsertionSort;
import com.jwetherell.algorithms.sorts.MergeSort;
import com.jwetherell.algorithms.sorts.QuickSort;
import com.jwetherell.algorithms.sorts.RadixSort;
import com.jwetherell.algorithms.sorts.ShellSort;

public class testSort {
@Test
    public void testGetTwo1_tc1() throws Exception {
        TwoSum twoSum = new TwoSum() ;
        int[] nums ={};
        int[] two1 = twoSum.getTwo1(nums, 1);
        // assertEquals(two1, new int[]{i,j}); 
        Assert.assertEquals(two1, null); 
    }
    @Test
    public void testGetTwo1_tc2() throws Exception {
        TwoSum twoSum = new TwoSum() ;
        int[] nums ={1};
        int[] two1 = twoSum.getTwo1(nums, 2);
        // assertEquals(two1, new int[]{i,j}); 
        Assert.assertArrayEquals(two1, null); 
    }
    @Test
    public void testGetTwo1_tc3() throws Exception {
        TwoSum twoSum = new TwoSum() ;
        int[] nums ={1,2};
        int[] two1 = twoSum.getTwo1(nums, 2);
        // assertEquals(two1, new int[]{i,j}); 
        Assert.assertArrayEquals(two1, null); 
    }
    @Test
    public void testGetTwo1_tc4() throws Exception {
        TwoSum twoSum = new TwoSum() ;
        int[] nums ={1,2};
        int[] two1 = twoSum.getTwo1(nums, 3);
        Assert.assertArrayEquals(two1, new int[]{1,0}); 
        // assertEquals(two1, null); 
    }
    @Test
    public void testGetTwo1_tc5() throws Exception {
        TwoSum twoSum = new TwoSum() ;
        int[] nums ={1,2,3};
        int[] two1 = twoSum.getTwo1(nums, 5);
        Assert.assertArrayEquals(two1, new int[]{2,1}); 
        // assertEquals(two1, null); 
    }

    @Test
    public void testGetTwo1_tc6() throws Exception {
        TwoSum twoSum = new TwoSum() ;
        int[] nums ={1,2,3,4,5,6,7,8,9,10};
        int[] two1 = twoSum.getTwo1(nums, 19);
        Assert.assertArrayEquals(two1, new int[]{9,8}); 
        // assertEquals(two1, null); 
    }
    @Test
    public void testGetTwo1_tc7() throws Exception {
        TwoSum twoSum = new TwoSum() ;
        int[] nums ={1};
        int[] two1 = twoSum.getTwo1(nums, 3);
        // assertEquals(two1, new int[]{0,1}); 
        Assert.assertArrayEquals(two1, null); 
    }
    @Test
    public void testGetTwo1_tc8() throws Exception {
        TwoSum twoSum = new TwoSum() ;
        int[] nums ={1,2,3};
        int[] two1 = twoSum.getTwo1(nums, 3);
        Assert.assertArrayEquals(two1, new int[]{1,0}); 
        // assertEquals(two1, null); 
    }
     @Test
    public void testGetTwo1_tc9() throws Exception {
        TwoSum twoSum = new TwoSum() ;
        int[] nums ={1,2,3,4,5,6,7,8,9,10,11};
        int[] two1 = twoSum.getTwo1(nums, 3);
        Assert.assertArrayEquals(two1, new int[]{1,0}); 
        // assertEquals(two1, null); 
    }
}
