package com.jwetherell.algorithms.mathematics;

import java.util.LinkedList;

/**
 * In mathematics, the notion of permutation relates to the act of arranging all the members of a set into some sequence 
 * or order, or if the set is already ordered, rearranging (reordering) its elements, a process called permuting.
 * <p>
 * http://en.wikipedia.org/wiki/Permutation
 * <br>
 * @author Justin Wetherell <phishman3579@gmail.com>
 * @author Lucjan Roslanowski <lucjanroslanowski@gmail.com>
 */
public class Permutations {	

    /** 
     * N! permutation of the characters in the string (in order)
     */
    public static String[] permutations(String stringToGeneratePermutationsFrom) {
        final int size = numberOfPermutations(stringToGeneratePermutationsFrom.length());
        final String[] list = new String[size];
        final char[] prefix = new char[0];
        final char[] chars = stringToGeneratePermutationsFrom.toCharArray();
        permutations(list, 0, prefix, chars, 0, chars.length);
        return list;
    }

    private static final int numberOfPermutations(int N) {
        // factorial
        int result = N;
        while (N > 1)
            result *= --N;
        return result;
    }

    private static final int permutations(String[] list, int index, char[] prefix, char[] remaining, int prefixLength, int remainingLength) {
        final int N = remainingLength-prefixLength;
        if (N == 0) {
            list[index]=new String(prefix);
            index++;
        } else {
            for (int i=0; i<N; i++) {
                final char[] prefChars = new char[prefixLength+1];
                System.arraycopy(prefix, 0, prefChars, 0, prefixLength);
                System.arraycopy(remaining, i, prefChars, prefixLength, 1);

                final char[] restChars = new char[N-1];
                System.arraycopy(remaining, 0,   restChars, 0, i);
                System.arraycopy(remaining, i+1, restChars, i, N-(i+1));

                index = permutations(list, index, prefChars, restChars, remainingLength-(N-1), remainingLength);
            }
        }
        return index;
    }

    /**
     * Permutations of numbers in an array using recursion
     * <br>
     * int numbers[] = {7,5,3};
     * LinkedList<LinkedList<Integer>> result = getAllPermutations(numbers);
     */
    public static final LinkedList<LinkedList<Integer>> getAllPermutations(final int[] numbers){
        final LinkedList<LinkedList<Integer>> result = new LinkedList<LinkedList<Integer>>();
        return getAllPermutations(numbers, result);
    }

	private static final LinkedList<LinkedList<Integer>> getAllPermutations(final int[] numbers,  LinkedList<LinkedList<Integer>> result){
		//numbers given in an array are also a permutation
		LinkedList<Integer> firstPermutation = new LinkedList<Integer>();
		for(int el : numbers){
			firstPermutation.add(new Integer(el));
		}
		result.add(firstPermutation);
		//let's permute all elements in array starting from index 0
		return permute(numbers, 0, result);
	}
	
	private static final LinkedList<LinkedList<Integer>> permute(final int[] numbers, int currentElementIndex,  LinkedList<LinkedList<Integer>> result){
		if(currentElementIndex == numbers.length - 1)
			return result;

		for(int i = currentElementIndex; i < numbers.length; ++i){
			//swapping two elements
			int temp = numbers[i];
			numbers[i] = numbers[currentElementIndex];
			numbers[currentElementIndex] = temp;
			
			permute(numbers, currentElementIndex + 1,result);
			
			//all next permutation found
			if(i != currentElementIndex){
				LinkedList<Integer> nextPermutation = new LinkedList<Integer>();
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
