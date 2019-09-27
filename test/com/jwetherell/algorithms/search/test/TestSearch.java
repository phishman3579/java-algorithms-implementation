package com.jwetherell.algorithms.search.test;

import static org.junit.Assert.assertTrue;

import com.jwetherell.algorithms.search.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class TestSearch {

	@Test
    public void testQuickSelect() {
	int index = QuickSelect.find(valueInArray, sorted);
	assertTrue("Brute force error. expected=" + valueIndex + " got=" + index, (index == valueIndex));
	index = QuickSelect.find(valueNotInArray, sorted);
	assertTrue("Brute force error. expected=" + Integer.MAX_VALUE + " got=" + index, (index == Integer.MAX_VALUE));
    }
}
