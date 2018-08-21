package com.jwetherell.algorithms.sorts;

import java.util.Arrays;
import java.util.List;

/**
 * Created by rajat on 15/08/18.
 */
public class SelectionSort {

  public static <T extends Comparable<T>>  T[] sort(T[] valuesList) {

    for(int i=0;i<valuesList.length;i++) {

      int currMinValueIndex = i;

      for(int j=i+1;j<valuesList.length;j++) {

        if(valuesList[j].compareTo(valuesList[currMinValueIndex])<0) {
          currMinValueIndex = j;
        }
      }

      T tempVal = valuesList[i];
      valuesList[i] = valuesList[currMinValueIndex];
      valuesList[currMinValueIndex] = tempVal;

    }
    return valuesList;
  }

  public static void main(String[] args) {
    Integer a[] = {9,8,1,2,4,5,6};
    System.out.println(Arrays.toString(SelectionSort.sort(a)));
  }
}
