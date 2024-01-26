package ru.sayron;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CustomHashMapTest {
    CustomHashMap<Integer, String> map;

    @Before
    public void setUp() {
        map = new CustomHashMap<>();
        for (int i = 1; i <= 10000; i++) {
            map.put(i, "Value " + i);
        }
    }

    @Test
    public void testSize() {
        Assert.assertEquals(10000, map.size());
    }

    @Test
    public void testGet() {
        Assert.assertEquals("Value 5000", map.get(5000));
        Assert.assertNull(map.get(10001));
    }

    @Test
    public void testPut() {
        map.put(10_001, "Value 10001");
        Assert.assertEquals("Value 10001", map.get(10001));
        Assert.assertEquals(10001, map.size());
    }

    @Test
    public void testRemove() {
        Assert.assertEquals("Value 5000", map.remove(5000));
        Assert.assertNull(map.get(5000));
        Assert.assertEquals(9999, map.size());
    }

    @Test
    public void testContainsKey() {
        Assert.assertTrue(map.containsKey(5000));
        Assert.assertFalse(map.containsKey(10001));
    }

    @Test
    public void testContainsValue() {
        Assert.assertTrue(map.containsValue("Value 5000"));
        Assert.assertFalse(map.containsValue("Value 10001"));
    }

    @Test
    public void testKeySet() {
        Assert.assertTrue(map.keySet().contains(5000));
        Assert.assertFalse(map.keySet().contains(10001));
    }

    @Test
    public void testValues() {
        Assert.assertTrue(map.values().contains("Value 5000"));
        Assert.assertFalse(map.values().contains("Value 10001"));
    }

    @Test
    public void testClear() {
        map.clear();
        Assert.assertTrue(map.isEmpty());
    }
}