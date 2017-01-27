package com.jwetherell.algorithms.data_structures.test;

import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.junit.Test;

import com.jwetherell.algorithms.data_structures.List;
import com.jwetherell.algorithms.data_structures.test.common.JavaCollectionTest;
import com.jwetherell.algorithms.data_structures.test.common.ListTest;
import com.jwetherell.algorithms.data_structures.test.common.Utils;
import com.jwetherell.algorithms.data_structures.test.common.Utils.TestData;

public class ListTests {

    @Test
    public void testArrayList() {
        TestData data = Utils.generateTestData(1000);

        String aName = "List [array]";
        List.ArrayList<Integer> aList = new List.ArrayList<Integer>();
        Collection<Integer> aCollection = aList.toCollection();

        assertTrue(ListTest.testList(aList, aName, 
                                     data.unsorted, data.invalid));
        assertTrue(JavaCollectionTest.testCollection(aCollection, Integer.class, aName,
                                                     data.unsorted, data.sorted, data.invalid));

        // Try some array list specific tests
        {
            // adding new element at the first spot
            for (int i = 0; i < data.unsorted.length; i++) {
                Integer item = data.unsorted[i];
                boolean added = aList.add(0,item);
                if ((!aList.validate() || (aList.size() != i+1))) {
                    System.err.println(aName+" YIKES!! " + item + " caused a size mismatch.");
                    Utils.handleError(data,aList);
                    assertTrue(false);
                }
                if ((!added || !aList.contains(item))) {
                    System.err.println(aName+" YIKES!! " + item + " doesn't exists but has been added.");
                    Utils.handleError(data,aList);
                    assertTrue(false);
                }
            }
    
            boolean contains = aList.contains(data.invalid);
            boolean removed = aList.remove(data.invalid);
            if (contains || removed) {
                System.err.println(aName+" invalidity check. contains=" + contains + " removed=" + removed);
                Utils.handleError(data.invalid,aList);
                assertTrue(false);
            }
    
            int size = aList.size();
            for (int i = 0; i < size; i++) {
                Integer item = data.unsorted[i];
                removed = aList.remove(item);
                if ((!aList.validate() || (aList.size() != data.unsorted.length-(i+1)))) {
                    System.err.println(aName+" YIKES!! " + item + " caused a size mismatch.");
                    Utils.handleError(data,aList);
                    assertTrue(false);
                }
                if ((!removed || aList.contains(item))) {
                    System.err.println(aName+" YIKES!! " + item + " still exists but it has been remove.");
                    Utils.handleError(data,aList);
                    assertTrue(false);
                }
            }
        }
        {
            // adding new element at the middle spot
            for (int i = 0; i < data.unsorted.length; i++) {
                Integer item = data.unsorted[i];
                int idx = (int) Math.floor(i/2);
                boolean added = aList.add(idx,item);
                if ((!aList.validate() || (aList.size() != i+1))) {
                    System.err.println(aName+" YIKES!! " + item + " caused a size mismatch.");
                    Utils.handleError(data,aList);
                    assertTrue(false);
                }
                if ((!added || !aList.contains(item))) {
                    System.err.println(aName+" YIKES!! " + item + " doesn't exists but has been added.");
                    Utils.handleError(data,aList);
                    assertTrue(false);
                }
            }
    
            boolean contains = aList.contains(data.invalid);
            boolean removed = aList.remove(data.invalid);
            if (contains || removed) {
                System.err.println(aName+" invalidity check. contains=" + contains + " removed=" + removed);
                Utils.handleError(data.invalid,aList);
                assertTrue(false);
            }
    
            int size = aList.size();
            for (int i = 0; i < size; i++) {
                Integer item = data.unsorted[i];
                removed = aList.remove(item);
                if ((!aList.validate() || (aList.size() != data.unsorted.length-(i+1)))) {
                    System.err.println(aName+" YIKES!! " + item + " caused a size mismatch.");
                    Utils.handleError(data,aList);
                    assertTrue(false);
                }
                if ((!removed || aList.contains(item))) {
                    System.err.println(aName+" YIKES!! " + item + " still exists but it has been remove.");
                    Utils.handleError(data,aList);
                    assertTrue(false);
                }
            }
        }
        {
            // adding new element at the end spot
            for (int i = 0; i < data.unsorted.length; i++) {
                Integer item = data.unsorted[i];
                boolean added = aList.add(i,item);
                if ((!aList.validate() || (aList.size() != i+1))) {
                    System.err.println(aName+" YIKES!! " + item + " caused a size mismatch.");
                    Utils.handleError(data,aList);
                    assertTrue(false);
                }
                if ((!added || !aList.contains(item))) {
                    System.err.println(aName+" YIKES!! " + item + " doesn't exists but has been added.");
                    Utils.handleError(data,aList);
                    assertTrue(false);
                }
            }
    
            boolean contains = aList.contains(data.invalid);
            boolean removed = aList.remove(data.invalid);
            if (contains || removed) {
                System.err.println(aName+" invalidity check. contains=" + contains + " removed=" + removed);
                Utils.handleError(data.invalid,aList);
                assertTrue(false);
            }
    
            int size = aList.size();
            for (int i = 0; i < size; i++) {
                Integer item = data.unsorted[i];
                removed = aList.remove(item);
                if ((!aList.validate() || (aList.size() != data.unsorted.length-(i+1)))) {
                    System.err.println(aName+" YIKES!! " + item + " caused a size mismatch.");
                    Utils.handleError(data,aList);
                    assertTrue(false);
                }
                if ((!removed || aList.contains(item))) {
                    System.err.println(aName+" YIKES!! " + item + " still exists but it has been remove.");
                    Utils.handleError(data,aList);
                    assertTrue(false);
                }
            }
        }
    }

    @Test
    public void testSinglyLinkedList() {
        TestData data = Utils.generateTestData(1000);

        String lName = "List [Singlylinked]";
        List.SinglyLinkedList<Integer> lList = new List.SinglyLinkedList<Integer>();
        Collection<Integer> lCollection = lList.toCollection();

        assertTrue(ListTest.testList(lList, lName,
                                     data.unsorted, data.invalid));
        assertTrue(JavaCollectionTest.testCollection(lCollection, Integer.class, lName,
                                                      data.unsorted, data.sorted, data.invalid));
    }

    @Test
    public void testDoublyLinkedList() {
        TestData data = Utils.generateTestData(1000);

        String lName = "List [Doublylinked]";
        List.DoublyLinkedList<Integer> lList = new List.DoublyLinkedList<Integer>();
        Collection<Integer> lCollection = lList.toCollection();

        assertTrue(ListTest.testList(lList, lName,
                                     data.unsorted, data.invalid));
        assertTrue(JavaCollectionTest.testCollection(lCollection, Integer.class, lName,
                                                      data.unsorted, data.sorted, data.invalid));
    }
}
