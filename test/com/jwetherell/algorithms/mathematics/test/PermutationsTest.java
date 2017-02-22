package com.jwetherell.algorithms.mathematics;

import com.jwetherell.algorithms.mathematics.Permutations;
import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.Test;

public class PermutationsTest {

	@Test
	public void test1NumberOfPermutations() {
		int numbers[] = {1,2,3,4};
		int expectedNumberOfPermutations = 24;
		LinkedList<LinkedList<Integer>> result = new LinkedList<LinkedList<Integer>>();
		
		assertEquals(expectedNumberOfPermutations, (Permutations.getAllPermutations(numbers,result)).size());
	}
	
	@Test
	public void test2NumberOfPermutations() {
		int numbers[] = {3,4,2};
		int expectedNumberOfPermutations = 6;
		LinkedList<LinkedList<Integer>> result = new LinkedList<LinkedList<Integer>>();
		
		assertEquals(expectedNumberOfPermutations, (Permutations.getAllPermutations(numbers,result)).size());
	}
	
	@Test
	public void test3NumberOfPermutations() {
		int numbers[] = {3,4,2,5,4,9};
		int expectedNumberOfPermutations = 720;
		LinkedList<LinkedList<Integer>> result = new LinkedList<LinkedList<Integer>>();
		
		assertEquals(expectedNumberOfPermutations, (Permutations.getAllPermutations(numbers,result)).size());
	}
	
	@Test
	public void testComparePermutations() {
		int numbers[] = {4,2};
		
		LinkedList<Integer> firstPermutation = new LinkedList<>();
		firstPermutation.add(4);
		firstPermutation.add(2);
		
		LinkedList<Integer> secondPermutation = new LinkedList<>();
		secondPermutation.add(2);
		secondPermutation.add(4);
		
		LinkedList<LinkedList<Integer>> allPermutations = new LinkedList<LinkedList<Integer>>();
		allPermutations.add(firstPermutation);
		allPermutations.add(secondPermutation);
		
		LinkedList<LinkedList<Integer>> result = new LinkedList<LinkedList<Integer>>();
		
		assertEquals(allPermutations, Permutations.getAllPermutations(numbers,result));
	}
}
