package com.jwetherell.algorithms.mathematics;

import java.util.LinkedList;
import java.util.List;

/**
 * In mathematics, the notion of permutation relates to the act of arranging all the members of a set into some sequence 
 * or order, or if the set is already ordered, rearranging (reordering) its elements, a process called permuting.
 * <p>
 * @see <a href="https://en.wikipedia.org/wiki/Permutation">Permutation (Wikipedia)</a>
 * <br>
 * @author Justin Wetherell <phishman3579@gmail.com>
 * @author Lucjan Roslanowski <lucjanroslanowski@gmail.com>
 */
public class Permutations {	

    private Permutations() { }

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
    public static final <N extends Number> List<List<N>> getAllPermutations(final N[] numbers){
        final List<List<N>> result = new LinkedList<List<N>>();
        return getAllPermutations(numbers, result);
    }

    private static final <N extends Number> List<List<N>> getAllPermutations(final N[] numbers, List<List<N>> result){
		//numbers given in an array are also a permutation
		LinkedList<N> firstPermutation = new LinkedList<N>();
		for (N el : numbers)
			firstPermutation.add(el);
		result.add(firstPermutation);
		//let's permute all elements in array starting from index 0
		return permute(numbers, 0, result);
	}

    private static final <N extends Number> List<List<N>> permute(final N[] numbers, int currentElementIndex, List<List<N>> result){
		if(currentElementIndex == numbers.length - 1)
			return result;

		for(int i = currentElementIndex; i < numbers.length; ++i){
			//swapping two elements
			N temp = numbers[i];
			numbers[i] = numbers[currentElementIndex];
			numbers[currentElementIndex] = temp;
			
			permute(numbers, currentElementIndex + 1,result);
			
			//all next permutation found
			if(i != currentElementIndex){
				LinkedList<N> nextPermutation = new LinkedList<N>();
				for(int j = 0; j < numbers.length; j++)
					nextPermutation.add(numbers[j]);
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
