package com.jwetherell.algorithms.mathematics;

import java.util.LinkedList;


/*
 * Recursive permutations generating
 * @author Lucjan Roslanowski <lucjanroslanowski@gmail.com>
 * 
 * Example of usage:
 * int numbers[] = {7,5,3};
 * LinkedList<LinkedList<Integer>> result = new LinkedList<LinkedList<Integer>>();
 * getAllPermutations(numbers,result);
 */

public class Permutations {	
	
	public static LinkedList<LinkedList<Integer>> getAllPermutations(final int[] numbers,  LinkedList<LinkedList<Integer>> result){
		//numbers given in an array are also a permutation
		LinkedList<Integer> firstPermutation = new LinkedList<>();
		for(int el : numbers){
			firstPermutation.add(new Integer(el));
		}
		result.add(firstPermutation);
		//let's permute all elements in array starting from index 0
		return permute(numbers, 0, result);
	}
	
	public static LinkedList<LinkedList<Integer>> permute(final int[] numbers, 
			int currentElementIndex,  LinkedList<LinkedList<Integer>> result){
		if(currentElementIndex == numbers.length - 1){
			return result;
		}
		
		for(int i = currentElementIndex; i < numbers.length; ++i){
			//swapping two elements
			int temp = numbers[i];
			numbers[i] = numbers[currentElementIndex];
			numbers[currentElementIndex] = temp;
			
			permute(numbers, currentElementIndex + 1,result);
			
			//all next permutation found
			if(i != currentElementIndex){
				LinkedList<Integer> nextPermutation = new LinkedList<>();
				for(int j = 0; j < numbers.length; j++)
					nextPermutation.add(new Integer(numbers[j]));
				result.add(nextPermutation);
			}
			
			//swapping back two elements 
			temp = numbers[i];
			numbers[i] = numbers[currentElementIndex];
			numbers[currentElementIndex] = temp;
		}	
		return result;
	}
	
	
}
