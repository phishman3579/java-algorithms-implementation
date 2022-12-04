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
    public void testPutTrieMap() {
        String key = "Roses";
        Integer value = 1;
        Integer value2 = 2;
        TrieMap<String,Integer> trieMap = new TrieMap<String,Integer>();
        trieMap.put(key,value);
        trieMap.put(key,value2);
        assertTrue(trieMap.contains(key));
        assertEquals(value,trieMap.get(key));

    }

    @Test
    public void testClearTrieMap() {

        TrieMap<String,Integer> trieMap = new TrieMap<String,Integer>();
        trieMap.put("Roses", 1);
        trieMap.put("Tulips", 2);
        trieMap.put("Orchids", 2);
        assertEquals(3, trieMap.size());
        trieMap.remove("Tulips");
        assertEquals(2, trieMap.size());
        trieMap.clear();
        assertNotNull(trieMap);
        assertEquals(0, trieMap.size());

    }




    @Test
    public void testToMap() {

        TrieMap<String,Integer> trieMap = new TrieMap<String,Integer>();
        trieMap.put("Roses", 1);
        trieMap.put("Tulips", 2);
        trieMap.put("Orchids", 2);
        trieMap.put("Grass", null);
        trieMap.put("Grass", 0);
        Map<String, Integer> map = trieMap.toMap();
        assertEquals(4, map.size());
        assertNull(map.get("Grass"));

    }

}