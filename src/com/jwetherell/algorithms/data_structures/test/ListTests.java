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
