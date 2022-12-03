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
    public void getTest() {
    	 Integer value = 1;
    	 String key = "SkipListMap";
    	 SkipListMap<String, Integer> expected = new SkipListMap<String, Integer>();
    	 expected.put(key, value);
    	 map.put(key, value);
         assertTrue(map.contains(key)); 
         assertTrue(map.get(key)!= null);
         assertNull(map.get("NotKey"));
    	 }
    
    @Test
    public void clearListTest() {
    	 List.ArrayList<Integer> mapList = new List.ArrayList<Integer>();
         java.util.Map<String, Integer> jMap = map.toMap();

         mapList.add(201);
         mapList.clear();
         map.clear();
         jMap.clear();
    }
    
    @Test
    public void validate() {
    	List.ArrayList<Integer> mapList = new List.ArrayList<Integer>();
        String test = "Work";
        java.util.Map<String, Integer> jMap = map.toMap();
        StringBuilder builder = new StringBuilder();
        builder.append(test);
        assertNotNull(jMap.toString()); 
        assertNotNull(map.toString()); 
        
        
    }
    
    @Test
    public void toStringtest() {

        map.put("map", 1234)
        String expected= "1234";

        assertNotNull(map.toString());

    }
    
    @Test

    public void testValidate() {
        SkipListMap<String, String> mapTest = new SkipListMap<String, String>();
        String key = "book";
        String val = null;
        mapTest.put(key, val);
        assertTrue(!mapTest.validate());

    }


}
