package com.jwetherell.algorithms.mathematics.test;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.jwetherell.algorithms.mathematics.Permutations;

public class PermutationsTest {

	@Test
	public void test1NumberOfPermutations() {
		final Integer[] numbers = {1,2,3,4};
		final int expectedNumberOfPermutations = 24;
		assertEquals(expectedNumberOfPermutations, (Permutations.getAllPermutations(numbers)).size());
	}
	
	@Test
	public void test2NumberOfPermutations() {
	    final Integer[] numbers = {3,4,2};
	    final int expectedNumberOfPermutations = 6;
		assertEquals(expectedNumberOfPermutations, (Permutations.getAllPermutations(numbers)).size());
	}
	
	@Test
	public void test3NumberOfPermutations() {
	    final Integer[] numbers = {3,4,2,5,4,9};
	    final int expectedNumberOfPermutations = 720;
		assertEquals(expectedNumberOfPermutations, (Permutations.getAllPermutations(numbers)).size());
	}
	
	@Test
	public void testComparePermutations() {
	    final Integer[] numbers = {4,2};

	    final LinkedList<Integer> firstPermutation = new LinkedList<Integer>();
		firstPermutation.add(4);
		firstPermutation.add(2);

		final LinkedList<Integer> secondPermutation = new LinkedList<Integer>();
		secondPermutation.add(2);
		secondPermutation.add(4);

		final LinkedList<LinkedList<Integer>> allPermutations = new LinkedList<LinkedList<Integer>>();
		allPermutations.add(firstPermutation);
		allPermutations.add(secondPermutation);

		final List<List<Integer>> result = Permutations.getAllPermutations(numbers);
		assertTrue("allPermutations="+allPermutations+" result="+result, allPermutations.equals(result));
	}

    @Test
    public void testPermutation1() {
        final String string = "abc";
        final String[] list = Permutations.permutations(string);
        Assert.assertTrue(list[0].equals("abc"));
        Assert.assertTrue(list[5].equals("cba"));
    }

    @Test
    public void testPermutation2() {
        final String string = "abcd";
        final String[] list = Permutations.permutations(string);
        Assert.assertTrue(list[0].equals("abcd"));
        Assert.assertTrue(list[5].equals("adcb"));
        Assert.assertTrue(list[11].equals("bdca"));
        Assert.assertTrue(list[17].equals("cdba"));
        Assert.assertTrue(list[23].equals("dcba"));
    }
}
