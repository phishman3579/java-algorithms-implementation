package com.jwetherell.algorithms.mathematics.test;

import com.jwetherell.algorithms.mathematics.Division;
import org.junit.Test;
import static org.junit.Assert.*;
public class DivisionTest {
    @Test
    // Kiểm thử giá trị biên chọn giá trị bình thường cho cả hai biến
    public void testDivisionUsingLoop1() {
        long testresult1 = 1;
        assertEquals(testresult1, Division.divisionUsingLoop(-1,-1));
    }  
    @Test
    // Kiểm thử giá trị biên chọn giá trị max cho x và bình thường cho y 
    public void testDivisionUsingLoop2() {
        long testresult2 = -Integer.MAX_VALUE;
        assertEquals(testresult2, Division.divisionUsingLoop(Integer.MAX_VALUE,-1));
    }
    @Test
    // Kiểm thử giá trị biên chọn giá trị ngay trên max cho x và bình thường cho y 
    public void testDivisionUsingLoop3() {
        long testresult3 = -(Integer.MAX_VALUE + 1);
        assertEquals(testresult3, Division.divisionUsingLoop(Integer.MAX_VALUE + 1,-1));
    }
    @Test
    // Kiểm thử giá trị biên chọn giá trị ngay dưới max cho x và bình thường cho y 
    public void testDivisionUsingLoop4() {
        long testresult4 = -(Integer.MAX_VALUE - 1);
        assertEquals(testresult4, Division.divisionUsingLoop(Integer.MAX_VALUE - 1,-1));
    }
    @Test
    // Kiểm thử giá trị biên chọn giá trị min cho x và bình thường cho y 
    public void testDivisionUsingLoop5() {
        long testresult5 = - Integer.MIN_VALUE ;
        assertEquals(testresult5, Division.divisionUsingLoop((Integer.MIN_VALUE),-1));
    }
    @Test
    // Kiểm thử giá trị biên chọn giá trị ngay trên min cho x và bình thường cho y 
    public void testDivisionUsingLoop6() {
        long testresult6 = -(Integer.MIN_VALUE + 1);
        assertEquals(testresult6, Division.divisionUsingLoop((Integer.MIN_VALUE) + 1,-1));
    }
    @Test
    // Kiểm thử giá trị biên chọn giá trị ngay dưới min cho x và bình thường cho y 
    public void testDivisionUsingLoop7() {
        long testresult7 = -(Integer.MIN_VALUE -1);
        assertEquals(testresult7, Division.divisionUsingLoop((Integer.MIN_VALUE) - 1,-1));
    }
    @Test
    // Kiểm thử giá trị biên chọn giá trị bình thường cho x và max cho y 
    public void testDivisionUsingLoop8() {
        long testresult8 = 0;
        assertEquals(testresult8, Division.divisionUsingLoop(-1,Integer.MAX_VALUE));
    }
    @Test
    // Kiểm thử giá trị biên chọn giá trị bình thường cho x và ngay trên max cho y 
    public void testDivisionUsingLoop9() {
        long testresult9 = 0;
        assertEquals(testresult9, Division.divisionUsingLoop(-1,Integer.MAX_VALUE + 1));
    }
    @Test
    // Kiểm thử giá trị biên chọn giá trị bình thường cho x và ngay dưới max cho y 
    public void testDivisionUsingLoop10() {
        long testresult10 = 0;
        assertEquals(testresult10, Division.divisionUsingLoop(-1,Integer.MAX_VALUE - 1));
    }
    @Test
    // Kiểm thử giá trị biên chọn giá trị bình thường cho x và min cho y 
    public void testDivisionUsingLoop11() {
        long testresult11 = 0;
        assertEquals(testresult11, Division.divisionUsingLoop(-1,Integer.MIN_VALUE));
    }
    @Test
    // Kiểm thử giá trị biên chọn giá trị bình thường cho x và ngay trên min cho y 
    public void testDivisionUsingLoop12() {
        long testresult12 = 0;
        assertEquals(testresult12, Division.divisionUsingLoop(-1,Integer.MIN_VALUE + 1));
    }
    @Test
    // Kiểm thử giá trị biên chọn giá trị bình thường cho x và ngay dưới min cho y 
    public void testDivisionUsingLoop13() {
        long testresult13 = 0;
        assertEquals(testresult13, Division.divisionUsingLoop(-1,Integer.MIN_VALUE - 1));
    }
}
