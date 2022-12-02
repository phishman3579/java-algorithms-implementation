package com.jwetherell.algorithms.data_structures.test;

//modified to include all Assert methods directly
//Author: Abhishek Ahuja
import static org.junit.Assert.*;

import org.junit.Test;

import com.jwetherell.algorithms.data_structures.SkipListMap;
import com.jwetherell.algorithms.data_structures.test.common.JavaMapTest;
import com.jwetherell.algorithms.data_structures.test.common.MapTest;
import com.jwetherell.algorithms.data_structures.test.common.Utils;
import com.jwetherell.algorithms.data_structures.test.common.Utils.TestData;

//added for additional tests
//Author: Abhishek Ahuja
import com.jwetherell.algorithms.data_structures.List;


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

    //Tests added
    //Author: Abhishek Ahuja
    
    @Test
    public void testPutGet(){
        SkipListMap<String,Integer> newMap1 = new SkipListMap<String,Integer>();
        SkipListMap<String,Integer> newMap2 = new SkipListMap<String,Integer>();
        newMap1.put("Red", 1);
        newMap2.put("Red", 1);
        assertTrue(newMap1.contains("Red"));
        assertNotNull(newMap1.get("Red"));
        assertNull(newMap1.get("Blue"));
        assertEquals("Red=1",newMap1.toString());
    }
    
    @Test
    public void testLists(){
        List.ArrayList<Integer> newList = new List.ArrayList<Integer>();
        SkipListMap<String,Integer> listTest = new SkipListMap<String,Integer>();
        java.util.Map<String, Integer> jMap2 = listTest.toMap();
        newList.add(1);
        newList.clear();
        listTest.clear();
        jMap2.clear();
        assertNotNull(listTest.toString());
        assertNotNull(jMap2.toString());
    }
        
    @Test
    public void testStringBuilder(){
        StringBuilder builder = new StringBuilder();
        builder.append("Build");
    }
    
    @Test
    public void testValidate() {
    	SkipListMap<String,Integer> newMap3 = new SkipListMap<String,Integer>();
        newMap3.put("Green", null);
        newMap3.validate();
    	assertTrue(!newMap3.validate());
    }
    
    
}