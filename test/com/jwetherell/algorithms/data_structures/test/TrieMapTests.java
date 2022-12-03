package com.jwetherell.algorithms.data_structures.test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.jwetherell.algorithms.data_structures.TrieMap;
import com.jwetherell.algorithms.data_structures.test.common.JavaMapTest;
import com.jwetherell.algorithms.data_structures.test.common.MapTest;
import com.jwetherell.algorithms.data_structures.test.common.Utils;
import com.jwetherell.algorithms.data_structures.test.common.Utils.TestData;

public class TrieMapTests {

    TrieMap<String,Integer> map = new TrieMap<String,Integer>();

    @Test
    public void testTrieMap() {
        TestData data = Utils.generateTestData(1000);
        TrieMap<String,Integer> map = new TrieMap<String,Integer>();
        String mapName = "TrieMap";
        java.util.Map<String,Integer> jMap = map.toMap();

        assertTrue(MapTest.testMap(map, String.class, mapName,
                                   data.unsorted, data.invalid));
        assertTrue(JavaMapTest.testJavaMap(jMap, String.class, mapName,
                                           data.unsorted, data.sorted, data.invalid));
    }
    
    @Test
    public void getTest() {
    	TrieMap<String,Integer> actual = new TrieMap<String,Integer>();
    	String key = "TrieMap";
    	Integer value = 10;
    	actual.put(key, value);
    	assertTrue(actual.get(key)!=null);
    	assertTrue(actual.get("null")==null);
    	
    }
    
    @Test 
    public void clearTest() {
    	java.util.Map<String,Integer> jMap = map.toMap();
    	List.ArrayList<Integer> mapList = new List.ArrayList<Integer>();
    	mapList.clear();

        jMap.clear();
     
    }
    
    @Test
    public void toStringTest() {
    	TrieMap<String,Integer> actual = new TrieMap<String,Integer>();
    	java.util.Map<String,Integer> jMap = map.toMap();
    	actual.put("Map", 1);
    	map.put("Map", 1);
    	StringBuilder builder = new StringBuilder();
        builder.append(map.toString());
        assertEquals(map.toString(), actual.toString());
        assertNotNull(jMap.toString());
        
    }
    
    @Test

    public void testValidate() {

        TrieMap<String,String> validateTest = new TrieMap<String,String>();
        String key = "book";
        String val = null;
        map_test.put(key, val);
        assertTrue(!map_test.validate());

    }                          data.unsorted, data.sorted, data.invalid));
    }
}
