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
    public void testSkipListMaptoString() {
    	TestData dataS = Utils.generateTestData(1000);
    	String mapName = "SkipListMap1";
    	SkipListMap<String,Integer> mapS = new SkipListMap<String,Integer>();
        String jMapS = mapS.toString();
        assertTrue(MapTest.testMap(mapS, String.class, mapName, dataS.unsorted, dataS.invalid));
        
    }
    
    @Test
    public void testSkipListMapGet() {
    	TestData dataS = Utils.generateTestData(1000);
    	String mapName = "SkipListMap2";
    	SkipListMap<String,Integer> mapS = new SkipListMap<String,Integer>();
    	mapS.clear();
    	mapS.get(mapName);
        String jMapS = mapS.toString();
        assertTrue(MapTest.testMap(mapS, String.class, mapName, dataS.unsorted, dataS.invalid));
        
    }
    
}
