package com.jwetherell.algorithms.data_structures.test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.jwetherell.algorithms.data_structures.List;
import com.jwetherell.algorithms.data_structures.TrieMap;
import com.jwetherell.algorithms.data_structures.test.common.JavaMapTest;
import com.jwetherell.algorithms.data_structures.test.common.MapTest;
import com.jwetherell.algorithms.data_structures.test.common.Utils;
import com.jwetherell.algorithms.data_structures.test.common.Utils.TestData;

public class TrieMapTests {

    @Test
    public void testTrieMap() {
        TestData data = Utils.generateTestData(1000);

        String mapName = "TrieMap";
        TrieMap<String,Integer> map = new TrieMap<String,Integer>();
        java.util.Map<String,Integer> jMap = map.toMap();

        assertTrue(MapTest.testMap(map, String.class, mapName,
                                   data.unsorted, data.invalid));
        assertTrue(JavaMapTest.testJavaMap(jMap, String.class, mapName,
                                           data.unsorted, data.sorted, data.invalid));
    }

    @Test
    public void testPutAndGet() {
    	TrieMap<String,Integer> testTrieMap1 = new TrieMap<String,Integer>();
    	TrieMap<String,Integer> testTrieMap2 = new TrieMap<String,Integer>();
    	String key = "Blue";
        Integer value = 2;
    	testTrieMap1.put(key,value);
        testTrieMap2.put(key,value);
        assertTrue(testTrieMap1.contains(key));
        assertNotNull(testTrieMap2.get(key));
        assertNull(testTrieMap1.get("Red"));
        assertEquals(testTrieMap1.toString(),testTrieMap2.toString());

   }

    @Test
    public void testClear() {

    	List.ArrayList<Integer> list = new List.ArrayList<Integer>();
    	TrieMap<String,Integer> testTrieMap3 = new TrieMap<String,Integer>();
        java.util.Map<String, Integer> map = testTrieMap3.toMap();
        list.add(1);
        list.clear();
        testTrieMap3.clear();
        map.clear();
        assertNotNull(map.toString());

    }

    @Test
    public void testToString() {        
        StringBuilder sb = new StringBuilder();
        sb.append("sb");
    }

    @Test
    public void testValidate() {
        TrieMap<String,String> testTrieMap4 = new TrieMap<String,String>();
        String key = "a";
        String val = null;
        testTrieMap4.put(key, val);
        assertTrue(!testTrieMap4.validate());
    }

}
