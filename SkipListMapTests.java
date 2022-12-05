package com.jwetherell.algorithms.data_structures.test;

import static org.junit.Assert.assertTrue;

import static org.junit.Assert.*;
import org.junit.Test;

import com.jwetherell.algorithms.data_structures.List;
import com.jwetherell.algorithms.data_structures.SkipListMap;
import com.jwetherell.algorithms.data_structures.test.common.JavaMapTest;
import com.jwetherell.algorithms.data_structures.test.common.MapTest;
import com.jwetherell.algorithms.data_structures.test.common.Utils;
import com.jwetherell.algorithms.data_structures.test.common.Utils.TestData;

public class SkipListMapTests {

    @Test
    public void testSkipListMap() {
        TestData data = Utils.generateTestData(1000);

        String mname = "SkipListMap";
        SkipListMap<String, Integer> map = new SkipListMap<String, Integer>();
        java.util.Map<String, Integer> jMap = map.toMap();

        assertTrue(MapTest.testMap(map, String.class, mname,
                data.unsorted, data.invalid));
        assertTrue(JavaMapTest.testJavaMap(jMap, Integer.class, mname,
                data.unsorted, data.sorted, data.invalid));
    }

    @Test
    public void testPutAndGet() {
        SkipListMap<String, Integer> map1 = new SkipListMap<String, Integer>();
        SkipListMap<String, Integer> map2 = new SkipListMap<String, Integer>();
        map1.put("Green", 3);
        map2.put("Green", 3);
        assertTrue(map1.contains("Green"));
        assertNotNull(map1.get("Green"));
        assertNull(map1.get("Blue"));
        assertEquals("Green=3", map1.toString());
    }

    @Test
    public void testListFunctions() {
        List.ArrayList<Integer> list = new List.ArrayList<Integer>();
        SkipListMap<String, Integer> skipListTest = new SkipListMap<String, Integer>();
        java.util.Map<String, Integer> jMap = skipListTest.toMap();
        list.add(1);
        list.clear();
        skipListTest.clear();
        jMap.clear();
        assertNotNull(skipListTest.toString());
        assertNotNull(jMap.toString());
    }

    @Test
    public void testToString() {
        StringBuilder sb = new StringBuilder();
        sb.append("sb");
    }

    @Test
    public void testValidate() {
        SkipListMap<String, Integer> map3 = new SkipListMap<String, Integer>();
        map3.put("Yellow", null);
        map3.validate();
        assertTrue(!map3.validate());
    }

}
