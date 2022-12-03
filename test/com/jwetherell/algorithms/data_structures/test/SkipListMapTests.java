package com.jwetherell.algorithms.data_structures.test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.jwetherell.algorithms.data_structures.SkipListMap;
import com.jwetherell.algorithms.data_structures.test.common.JavaMapTest;
import com.jwetherell.algorithms.data_structures.test.common.MapTest;
import com.jwetherell.algorithms.data_structures.test.common.Utils;
import com.jwetherell.algorithms.data_structures.test.common.Utils.TestData;

public class SkipListMapTests {

    @Test
    public void testSkipListMap() {
        TestData data = Utils.generateTestData(1000);

        String mapName = "SkipListMap";
        SkipListMap<String,Integer> map = new SkipListMap<String,Integer>();
        java.util.Map<String,Integer> jMap = map.toMap();

        assertTrue(MapTest.testMap(map, String.class, mapName,
                                   data.unsorted, data.invalid));
        assertTrue(JavaMapTest.testJavaMap(jMap, Integer.class, mapName,
                                           data.unsorted, data.sorted, data.invalid));
    }
    
    @Test
        public void testGet() {
            SkipListMap<String, Integer> map = new SkipListMap<String, Integer>();
            Integer value = 1;
            String key = "SkipListMap";
            SkipListMap<String, Integer> expected = new SkipListMap<String, Integer>();
            expected.put(key, value);
            map.put(key, value);

            assertTrue(map.contains(key));
            assertTrue(map.get(key) != null);
            assertNull(map.get("NotKey"));
        }

    @Test
    public void testClearList() {
        SkipListMap<String, Integer> map = new SkipListMap<String, Integer>();
        List.ArrayList<Integer> mapList = new List.ArrayList<Integer>();
        java.util.Map<String, Integer> jMap = map.toMap();
        mapList.add(201);
        mapList.clear();
        map.clear();
        jMap.clear();
    }

    @Test
    public void testToMap() {
        SkipListMap<String, Integer> mapTest = new SkipListMap<String, Integer>();
        java.util.Map<String, Integer> jMap = mapTest.toMap();

        assertNotNull(jMap.toString());
        assertNotNull(mapTest.toString());
    }

    @Test
    public void testToString() {
        SkipListMap<String, Integer> mapTest = new SkipListMap<String, Integer>();
        mapTest.put("abc", 1234);
        String actual = mapTest.toString();
        String expected = "abc=1234";

        assertNotNull(mapTest.toString());
        assertTrue(expected.equals(actual));
    }

    @Test
    public void testValidate() {
        SkipListMap<String, String> mapTest = new SkipListMap<String, String>();
        String key = "abc";
        String val = null;
        mapTest.put(key, val);
        mapTest.validate();

        assertTrue(!mapTest.validate());

        }
}
