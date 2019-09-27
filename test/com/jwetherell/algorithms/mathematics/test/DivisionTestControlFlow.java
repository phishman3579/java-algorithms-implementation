package com.jwetherell.algorithms.mathematics.test;

import com.jwetherell.algorithms.mathematics.Division;
import org.junit.Test;
import static org.junit.Assert.*;
public class DivisionTest{
	@Test
	public void testDivisionUsingLoop1() {
		assertEquals(0, Division.divisionUsingLoop(6,7));
		}
	@Test
	public void testDivisionUsingLoop2() {
		assertEquals(1, Division.divisionUsingLoop(7,-6));
		}
}
