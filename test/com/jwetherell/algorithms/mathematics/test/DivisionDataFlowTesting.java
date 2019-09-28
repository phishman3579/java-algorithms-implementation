package com.jwetherell.algorithms.mathematics.test;

import com.jwetherell.algorithms.mathematics.Division;
import org.junit.Test;
import static org.junit.Assert.*;
public class DivisionTest{
	@Test
	public void testDivisionUsingLoop1() {
		int inputa1 = 7
		int inputb1 = 6
		long expected1 = 1
		assertEquals(expected1, Division.divisionUsingLoop(inputa1,inputb1));
		}
	@Test
	public void testDivisionUsingLoop2() {
		int inputa2 = 7
		int inputb2 = -6
		long expected2 = -1
		assertEquals(expected2, Division.divisionUsingLoop(inputa2,inputb2));
		}
	@Test
	public void testDivisionUsingLoop3() {
		int inputa3 = 6
		int inputb3 = 7
		long expected3 = 0
		assertEquals(expected3, Division.divisionUsingLoop(inputa3,inputb3));
		}
	@Test
	public void testDivisionUsingLoop4() {
		int inputa4 = 6
		int inputb4 = -7
		long expected4 = 0
		assertEquals(expected4, Division.divisionUsingLoop(inputa4,inputb4));
		}
}