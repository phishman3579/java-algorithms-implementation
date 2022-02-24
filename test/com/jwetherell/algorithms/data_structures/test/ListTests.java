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

    /**
     * Tests that the private function grow() in the ArrayList implementation
     * in the file List.java works correctly.
     * Requirement: If the size attribute exceeds the size of the underlying array,
     * then the grow() function should be called, which should increase the size of said array.
     */
    @Test
    public void testGrowArrayList() {
        List.ArrayList<Integer> aList = new List.ArrayList<Integer>();

        // Add a large number (>1023) of elements to a list, 
        // so that the underlying array must be increased in size.
        for (int i = 0; i < 100000; i++) {
            aList.add(i);
        }

        // Assert that the first and last elements are reachable,
        // meaning that the size of the underlying array has been increased correctly.
        assertTrue(aList.get(0) == 0);
        assertTrue(aList.get(99999) == 99999);

        // Assert that the size attribute is correct
        assertTrue(aList.size() == 100000);
    }

    /**
     * Tests that the private function shrink() in the ArrayList implementation
     * in the file List.java works correctly.
     * Requirement: If the size attribute becomes smaller than half the size of the underlying array,
     * then the shrink() function should be called, which should decrease the size of said array.
     */
    @Test
    public void testShrinkArrayList() {
        List.ArrayList<Integer> aList = new List.ArrayList<Integer>();

        // Add 2000 elements, causing the underlying array go grow beyond the minimum size.
        for (int i = 0; i < 2000; i++) {
            aList.add(2);
        }

        // Remove the first 1000 elements, so that the shrink() function is called
        // from the remove function.
        for (int i = 0; i < 1000; i++) {
            aList.remove(i);
        }
        
        // Assert that the size of the list is correct
        assertTrue(aList.size() == 1000);

        // Assert that all elements in the list are reachable
        for (int i = 0; i < 1000; i++) {
            assertTrue(aList.get(i) == 2);
        }

        // Assert that trying to reach an element outside the ArrayList returns null,
        // meaning that the list has shrunk correctly.
        assertTrue(aList.get(1000) == null);
    }

    @Test
    public void testRemoveInvalidIndex() {
        List.ArrayList<Integer> aList = new List.ArrayList<Integer>();
        aList.add(2);

        // Requirement: If someone tries to remove an element at a negative (i.e. invalid) index
        // in an ArrayList, then null should be returned.
        assertTrue(aList.remove(-1) == null);
    }

    @Test
    public void testValidateListWithNullElement() {
        List.ArrayList<Integer> aList = new List.ArrayList<Integer>();

        // By adding a null value to the list, it should no longer be valid
        aList.add(null);

        // Assert the following requirement: 
        // The validate() function should return false for lists with null elements.
        assertTrue(!aList.validate());
    }
}
