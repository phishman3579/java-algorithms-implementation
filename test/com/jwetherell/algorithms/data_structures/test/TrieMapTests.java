package com.jwetherell.algorithms.data_structures.test;

import static org.junit.Assert.assertTrue;

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
	public void testclear() {
		TrieMap<String, Integer> map = new TrieMap<String, Integer>();
		java.util.Map<String, Integer> map1 = map.toMap();
		List.ArrayList<Integer> mapList = new List.ArrayList<Integer>();
		mapList.clear();
		map1.clear();

	}

	@Test
	public void testget() {
		TrieMap<String, String> map = new TrieMap<String, String>();
		String key = "abc";
		String value = "val";
		map.put(key, value);

		assertTrue(map.get(key) != null);
		assertTrue(map.get("null") == null);
	}

	@Test
	public void testValidate() {
		TrieMap<String, String> map_test = new TrieMap<String, String>();
		String key = "abc";
		String val = null;
		map_test.put(key, val);

		assertTrue(!map_test.validate());
	}

	@Test
	public void testToString() {
		TrieMap<String, String> map = new TrieMap<String, String>();
		java.util.Map<String, String> map1 = map.toMap();
		map.put("Map", "val1");
		map1.put("Map", "val2");

		assertEquals(map.toString(), map.toString());
		assertNotNull(map1.toString());

	}
}
