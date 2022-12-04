package com.jwetherell.algorithms.data_structures.test;


import static org.junit.Assert.*;
import org.junit.Test;

import com.jwetherell.algorithms.data_structures.List;
import com.jwetherell.algorithms.data_structures.SkipListMap;
import com.jwetherell.algorithms.data_structures.test.common.JavaMapTest;
import com.jwetherell.algorithms.data_structures.test.common.MapTest;
import com.jwetherell.algorithms.data_structures.test.common.Utils;
import com.jwetherell.algorithms.data_structures.test.common.Utils.TestData;

public class SkipListMapTests {
        SkipListMap<String, Integer> map = new SkipListMap<String, Integer>();

    @Test
    public void testSkipListMap() {
        TestData data = Utils.generateTestData(1000);

        String mapName = "SkipListMap";
        java.util.Map<String, Integer> jMap = map.toMap();

        assertTrue(MapTest.testMap(map, String.class, mapName,
                                   data.unsorted, data.invalid));
        assertTrue(JavaMapTest.testJavaMap(jMap, Integer.class, mapName,
                                           data.unsorted, data.sorted, data.invalid));
    }

    @Test
    public void verifyTest() {
        List.ArrayList<Integer> iList = new List.ArrayList<Integer>();
        String word = "Snap";
        java.util.Map<String, Integer> imap = map.toMap();
        StringBuilder builder = new StringBuilder();
        builder.append(word);
        assertNotNull(imap.toString());
        assertNotNull(map.toString());


    }

    @Test
    public void verifyClear() {
        List.ArrayList<Integer> iList = new List.ArrayList<Integer>();
        java.util.Map<String, Integer> iMap = map.toMap();

        iList.add(451);
        iList.clear();
        map.clear();
        iMap.clear();
    }

    @Test
    public void verifyGet() {
        Integer value = 1;
        String key = "TestGetFunctionality";
        SkipListMap<String, Integer> result = new SkipListMap<String, Integer>();
        result.put(key, value);
        map.put(key, value);
        assertTrue(map.contains(key));
        assertTrue(map.get(key)!= null);
        assertNull(map.get("Nothing"));
    }

    @Test
    public void verifyValidation() {
        SkipListMap<String, String> iTest = new SkipListMap<String, String>();
        String key = "Best";
        String val = null;
        iTest.put(key, val);
        assertTrue(!iTest.validate());

    }

    @Test
    public void verifyStringConversion() {

        map.put("test", 9999);
        String expected= "9999";

        assertNotNull(map.toString());

    }
}
