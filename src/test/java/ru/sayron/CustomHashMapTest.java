package ru.sayron;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CustomHashMapTest {

    @Test
    void testPutAndGet() {
        CustomHashMap<String, Integer> map = new CustomHashMap<>();
        map.put("one", 1);
        map.put("two", 2);
        map.put("three", 3);

        assertEquals(1, map.get("one"));
        assertEquals(2, map.get("two"));
        assertEquals(3, map.get("three"));
    }

    @Test
    void testRemove() {
        CustomHashMap<String, Integer> map = new CustomHashMap<>();
        map.put("one", 1);
        map.put("two", 2);
        map.put("three", 3);

        assertEquals(2, map.remove("two"));
        assertNull(map.get("two"));
        assertEquals(2, map.size());
    }

    @Test
    void testContainsKey() {
        CustomHashMap<String, Integer> map = new CustomHashMap<>();
        map.put("one", 1);
        map.put("two", 2);
        map.put("three", 3);

        assertTrue(map.containsKey("one"));
        assertTrue(map.containsKey("two"));
        assertFalse(map.containsKey("four"));
    }

    @Test
    void testContainsValue() {
        CustomHashMap<String, Integer> map = new CustomHashMap<>();
        map.put("one", 1);
        map.put("two", 2);
        map.put("three", 3);

        assertTrue(map.containsValue(1));
        assertTrue(map.containsValue(2));
        assertFalse(map.containsValue(4));
    }

    @Test
    void testSize() {
        CustomHashMap<String, Integer> map = new CustomHashMap<>();
        map.put("one", 1);
        map.put("two", 2);
        map.put("three", 3);

        assertEquals(3, map.size());

        map.remove("two");
        assertEquals(2, map.size());
    }

    @Test
    void testClear() {
        CustomHashMap<String, Integer> map = new CustomHashMap<>();
        map.put("one", 1);
        map.put("two", 2);
        map.put("three", 3);

        map.clear();

        assertEquals(0, map.size());
        assertNull(map.get("one"));
        assertNull(map.get("two"));
        assertNull(map.get("three"));
    }
}