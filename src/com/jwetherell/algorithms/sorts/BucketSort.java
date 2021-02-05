package com.jwetherell.algorithms.sorts;

import static java.util.stream.Collectors.toList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Stream;

/**
 * Created by rajat on 14/08/18.
 */
public class BucketSort {

  public static <T extends Number> T[] sort(T[] unsortedValues) {
   return sort(Arrays.asList(unsortedValues)).toArray(unsortedValues);
  }


  public static <T extends Number> List<T> sort(List<T> unsortedValues) {
    Map<Integer, List<T>> mapOfLists = new TreeMap<>();
    List<T> sortedValues = new ArrayList<T>();

    int listSize = unsortedValues.size();

    for (T val : unsortedValues) {
      int currBucket = (int)(listSize * val.doubleValue());
      List<T> currBucketList = mapOfLists.get(currBucket);
      if(null == currBucketList) {
        mapOfLists.put(currBucket,new ArrayList<>());
        currBucketList = mapOfLists.get(currBucket);
      }
      currBucketList.add(val);
    }

    for(Map.Entry<Integer,List<T>> individualListEntry: mapOfLists.entrySet()) {
      individualListEntry.getValue().sort(new Comparator<T>() {
        @Override
        public int compare(T o1, T o2) {
          return o1.doubleValue()<o2.doubleValue() ? -1 : 1;
        }
      });

      for(int i=0; i< individualListEntry.getValue().size();i++) {
        sortedValues.add(individualListEntry.getValue().get(i));
      }
    }

    return sortedValues;
  }


  public static void main(String[] args) {
    // Double Input
    List<Double> unsortedDoubleValues = Stream
        .of(4.2, 23.1, 6.4, 78.2, 1.5, 54.2, 23.4, 1.7, 9.2, 231.9, 9.3, 12.1).collect(toList());
    BucketSort bucketSort = new BucketSort();

    System.out.println("Double values before Sorting:");
    for(Double doubleValue : unsortedDoubleValues) {
      System.out.println(doubleValue);
    }

    System.out.println("Double values after Sorting:");
    List<Double> sortedDoubleValues = BucketSort.sort(unsortedDoubleValues);
    for(Double doubleValue : sortedDoubleValues) {
      System.out.println(doubleValue);
    }

  }
}
