package com.jwetherell.algorithms.data_structures.test;

import org.junit.Test;
import static org.junit.Assert.*;

import com.jwetherell.algorithms.data_structures.SkipListMap;
import com.jwetherell.algorithms.data_structures.test.common.JavaMapTest;
import com.jwetherell.algorithms.data_structures.test.common.MapTest;
import com.jwetherell.algorithms.data_structures.test.common.Utils;
import com.jwetherell.algorithms.data_structures.test.common.Utils.TestData;

import com.jwetherell.algorithms.data_structures.List;

public class SkipListMapTests {

    @Test
    public void testSkipListMap() {
        TestData data = Utils.generateTestData(1000);

        String mapName = "SkipListMap";
        SkipListMap<String, Integer> map = new SkipListMap<String, Integer>();
        java.util.Map<String, Integer> jMap = map.toMap();

        assertTrue(MapTest.testMap(map, String.class, mapName,
                data.unsorted, data.invalid));
        assertTrue(JavaMapTest.testJavaMap(jMap, Integer.class, mapName,
                data.unsorted, data.sorted, data.invalid));
    }

    @Test
    public void putAndGet() {
        SkipListMap<String, Integer> map1 = new SkipListMap<String, Integer>();
        map1.put("TestString", 1);
        assertTrue(map1.contains("TestString"));
        assertNotNull(map1.get("TestString"));
        assertNull(map1.get("NotPresent"));
        assertEquals("TestString=1", map1.toString());
    }

    @Test
    public void listTest() {
        List.ArrayList<Integer> arr = new List.ArrayList<Integer>();
        SkipListMap<String, Integer> skipList = new SkipListMap<String, Integer>();
        java.util.Map<String, Integer> map = skipList.toMap();
        arr.add(1);
        arr.clear();
        skipList.clear();
        map.clear();
        map.put("map", 1);
        map.put("map", 2);
        assertNotNull(skipList.toString());
        assertNotNull(map.toString());
    }

    @Test
    public void stringBuilder() {
        StringBuilder builder = new StringBuilder();
        builder.append("Build");
    }

    @Test
    public void validateTest() {
        SkipListMap<String, Integer> skipListMap1 = new SkipListMap<String, Integer>();
        SkipListMap<String, Integer> skipListMap2 = new SkipListMap<String, Integer>();
        skipListMap1.put("validate", null);
        // System.out.println(skipListMap2.toString());
        assertFalse(skipListMap1.validate());
        assertTrue(skipListMap2.validate());
        ;

    }

}