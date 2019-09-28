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
    public void test1() throws Exception {
        Test t1 = new Test() ;
        int[] nums ={};
        int[] s1 = t1.getSort(nums, 1);
        Assert.assertEquals(s1, null); 
    }
    @Test
    public void test1() throws Exception {
        Test t1 = new Test() ;
        int[] nums ={1};
        int[] s1 = t1.getSort(nums, 2);
        Assert.assertEquals(s1, null); 
    }
    @Test
    public void test1() throws Exception {
        Test t1 = new Test() ;
        int[] nums ={1,2};
        int[] s1 = t1.getSort(nums, 3);
        Assert.assertEquals(s1, null); 
    }
    @Test
    public void test1() throws Exception {
        Test t1 = new Test() ;
        int[] nums ={1,2,3};
        int[] s1 = t1.getSort(nums, 4);
        Assert.assertEquals(s1, null); 
    }
    @Test
    public void test1() throws Exception {
        Test t1 = new Test() ;
        int[] nums ={1,2,3,4};
        int[] s1 = t1.getSort(nums, 5);
        Assert.assertEquals(s1, null); 
    }

    @Test
    public void test1() throws Exception {
        Test t1 = new Test() ;
        int[] nums ={1,2,3,4,5};
        int[] s1 = t1.getSort(nums, 6);
        Assert.assertEquals(s1, null); 
    }
    @Test
    public void test1() throws Exception {
        Test t1 = new Test() ;
        int[] nums ={1,2,3,4,5,6};
        int[] s1 = t1.getSort(nums, 7);
        Assert.assertEquals(s1, null); 
    }
    @Test
    public void test1() throws Exception {
        Test t1 = new Test() ;
        int[] nums ={1,2,3,4,5,6,7};
        int[] s1 = t1.getSort(nums, 8);
        Assert.assertEquals(s1, null); 
    }
     @Test
    public void test1() throws Exception {
        Test t1 = new Test() ;
        int[] nums ={1,2,3,4,5,6,7,8};
        int[] s1 = t1.getSort(nums, 9);
        Assert.assertEquals(s1, null); 
    }
}
