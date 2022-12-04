package com.jwetherell.algorithms.data_structures.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.Test;

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
    public void testPutTrieMap() {
        //Given
        String key = "Ironman";
        Integer value = 1;
        Integer value2 = 2;
        //When
        TrieMap<String,Integer> trieMap = new TrieMap<String,Integer>();
        trieMap.put(key,value);
        trieMap.put(key,value2);
        //Then
        assertTrue(trieMap.contains(key));
        assertEquals(value,trieMap.get(key));

    }

    @Test
    public void testClearTrieMap() {

        //When
        TrieMap<String,Integer> trieMap = new TrieMap<String,Integer>();
        trieMap.put("Ironman", 1);
        trieMap.put("Thor", 2);
        trieMap.put("Spiderman", 2);
        //Then
        assertEquals(3, trieMap.size());
        trieMap.remove("Thor");
        assertEquals(2, trieMap.size());
        trieMap.clear();
        assertNotNull(trieMap);
        assertEquals(0, trieMap.size());

    }

    @Test
    public void testValidateTrieMap() {

        TrieMap<String,Integer> trieMap = new TrieMap<String,Integer>();
        trieMap.put("Ironman", 1);
        trieMap.put("Thor", 2);
        trieMap.put("Spiderman", 2);
        assertTrue(trieMap.validate());
        trieMap.put("Thanos", null);
        assertFalse(trieMap.validate());

    }

    @Test
    public void testToMap() {

        TrieMap<String,Integer> trieMap = new TrieMap<String,Integer>();
        trieMap.put("Ironman", 1);
        trieMap.put("Thor", 2);
        trieMap.put("Spiderman", 2);
        trieMap.put("Thanos", null);
        trieMap.put("Thanos", 0);
        Map<String, Integer> map = trieMap.toMap();
        assertEquals(4, map.size());
        assertNull(map.get("Thanos"));

    }

}
