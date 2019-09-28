/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kiem_thu;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Dung
 */
public class ControlFlowTesting {
    @Test
    public void testMultiplyUsingShift1() {
        assertEquals(90, Multiplication.multiplyUsingShift(10, 9));
    }
    @Test
    public void testMultiplyUsingShift2() {
        assertEquals(-90, Multiplication.multiplyUsingShift(10, -9));
    }


}