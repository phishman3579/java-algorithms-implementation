package com.jwetherell.algorithms.data_structures.test;

//Modified to include all Assert methods directly
//Author: Abhishek Ahuja
import static org.junit.Assert.*;

import org.junit.Test;

import com.jwetherell.algorithms.data_structures.TrieMap;
import com.jwetherell.algorithms.data_structures.test.common.JavaMapTest;
import com.jwetherell.algorithms.data_structures.test.common.MapTest;
import com.jwetherell.algorithms.data_structures.test.common.Utils;
import com.jwetherell.algorithms.data_structures.test.common.Utils.TestData;

//Imported
//Author: Abhishek Ahuja
import com.jwetherell.algorithms.data_structures.List;

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
   
    //Tests Added
    //Author: Abhishek Ahuja
    
    @Test
    public void testPutGet() {
    	TrieMap<String,Integer> trieMap1 = new TrieMap<String,Integer>();
    	TrieMap<String,Integer> trieMap2 = new TrieMap<String,Integer>();
    	String key = "Green";
        Integer value = 1;
    	trieMap1.put(key,value);
        trieMap2.put(key,value);
        assertTrue(trieMap1.contains(key));
        assertNotNull(trieMap1.get(key));
        assertNull(trieMap1.get("Blue"));
        assertEquals(trieMap1.toString(),trieMap2.toString());
    	
   }
    
    @Test
    public void testClear() {
    	
    	List.ArrayList<Integer> newList = new List.ArrayList<Integer>();
    	TrieMap<String,Integer> trieMap3 = new TrieMap<String,Integer>();
        java.util.Map<String, Integer> jMap1 = trieMap3.toMap();
        newList.add(1);
        newList.clear();
        trieMap3.clear();
        jMap1.clear();
        assertNotNull(jMap1.toString());
        
    }
    
    @Test
    public void toStringTest() {        
        StringBuilder builder = new StringBuilder();
        builder.append("Build");
    }
    
    @Test
    public void testValidate() {
        TrieMap<String,String> trieMap4 = new TrieMap<String,String>();
        String key = "abc";
        String val = null;
        trieMap4.put(key, val);
        assertTrue(!trieMap4.validate());
    }
    
}
