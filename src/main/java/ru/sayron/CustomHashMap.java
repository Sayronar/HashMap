package ru.sayron;

import java.util.*;

/**
 * Простая реализация HashMap.
 *
 * @param <K> - тип ключа.
 * @param <V> - тип значения.
 */
public class CustomHashMap<K, V> implements Map<K, V> {

    private static final int DEFAULT_CAPACITY = 16;
    private static final double LOAD_FACTOR = 0.75;

    private Node<K, V>[] table;
    private int size;

    /**
     * Узел (Node) для хранения пары ключ-значение в связанном списке.
     *
     * @param <K> - тип ключа.
     * @param <V> - тип значения.
     */
    private static class Node<K, V> {
        private final K key;
        private V value;
        private Node<K, V> next;

        /**
         * Конструктор для создания узла с заданным ключом и значением.
         *
         * @param key   - ключ узла.
         * @param value - значение узла.
         */
        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    /**
     * Конструктор по умолчанию, создающий HashMap с начальной емкостью.
     */
    public CustomHashMap() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Конструктор с заданной начальной емкостью.
     *
     * @param capacity - начальная емкость HashMap.
     */
    public CustomHashMap(int capacity) {
        table = new Node[capacity];
    }

    /**
     * Возвращает количество элементов в HashMap.
     *
     * @return количество элементов в HashMap.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Проверяет, является ли HashMap пустой.
     *
     * @return true, если HashMap пуста, иначе false.
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Проверяет наличие ключа в HashMap.
     *
     * @param key - ключ для проверки.
     * @return true, если ключ присутствует, иначе false.
     */
    @Override
    public boolean containsKey(Object key) {
        int index = hash(key);
        Node<K, V> node = table[index];

        while (node != null) {
            if (Objects.equals(node.key, key)) {
                return true;
            }
            node = node.next;
        }
        return false;
    }

    /**
     * Проверяет наличие значения в HashMap.
     *
     * @param value - значение для проверки.
     * @return true, если значение присутствует, иначе false.
     */
    @Override
    public boolean containsValue(Object value) {
        for (Node<K, V> node : table) {
            while (node != null) {
                if (Objects.equals(node.value, value)) {
                    return true;
                }
                node = node.next;
            }
        }
        return false;
    }

    /**
     * Возвращает значение по заданному ключу.
     *
     * @param key - ключ для поиска значения.
     * @return значение, связанное с заданным ключом, или null, если ключ не найден.
     */
    @Override
    public V get(Object key) {
        int index = hash(key);
        Node<K, V> node = table[index];

        while (node != null) {
            if (Objects.equals(node.key, key)) {
                return node.value;
            }
            node = node.next;
        }
        return null;
    }

    /**
     * Добавляет ключ-значение в HashMap.
     *
     * @param key   - ключ для добавления.
     * @param value - значение для добавления.
     * @return предыдущее значение, связанное с заданным ключом, или null, если ключа не существовало.
     */
    @Override
    public V put(K key, V value) {
        if (shouldResize()) {
            resize();
        }

        int index = hash(key);
        Node<K, V> newNode = new Node<>(key, value);
        Node<K, V> existingNode = table[index];

        if (existingNode == null) {
            table[index] = newNode;
        } else {
            while (existingNode.next != null) {
                if (Objects.equals(existingNode.key, key)) {
                    V oldValue = existingNode.value;
                    existingNode.value = value;
                    return oldValue;
                }
                existingNode = existingNode.next;
            }
            existingNode.next = newNode;
        }

        size++;
        return value;
    }

    /**
     * Удаляет ключ и связанное с ним значение из HashMap.
     *
     * @param key - ключ для удаления.
     * @return значение, связанное с заданным ключом, или null, если ключ не найден.
     */
    @Override
    public V remove(Object key) {
        int index = hash(key);
        Node<K, V> node = table[index];
        Node<K, V> prevNode = null;

        while (node != null) {
            if (Objects.equals(node.key, key)) {
                if (prevNode == null) {
                    table[index] = node.next;
                } else {
                    prevNode.next = node.next;
                }
                size--;
                return node.value;
            }
            prevNode = node;
            node = node.next;
        }

        return null;
    }

    /**
     * Добавляет все элементы из заданного отображения в этот HashMap.
     *
     * @param m - отображение для добавления.
     */
    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        for (Map.Entry<? extends K, ? extends V> entry : m.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    /**
     * Очищает все элементы в HashMap.
     */
    @Override
    public void clear() {
        Arrays.fill(table, null);
        size = 0;
    }

    /**
     * Возвращает множество ключей в HashMap.
     *
     * @return множество ключей в HashMap.
     */
    @Override
    public Set<K> keySet() {
        Set<K> keySet = new HashSet<>();
        for (Node<K, V> node : table) {
            while (node != null) {
                keySet.add(node.key);
                node = node.next;
            }
        }
        return keySet;
    }

    /**
     * Возвращает коллекцию значений в HashMap.
     *
     * @return коллекция значений в HashMap.
     */
    @Override
    public Collection<V> values() {
        Collection<V> values = new ArrayList<>();
        for (Node<K, V> node : table) {
            while (node != null) {
                values.add(node.value);
                node = node.next;
            }
        }
        return values;
    }

    /**
     * Возвращает множество записей (ключ-значение) в HashMap.
     *
     * @return множество записей (ключ-значение) в HashMap.
     */
    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        Set<Map.Entry<K, V>> entrySet = new HashSet<>();
        for (Node<K, V> node : table) {
            while (node != null) {
                entrySet.add(new AbstractMap.SimpleEntry<>(node.key, node.value));
                node = node.next;
            }
        }
        return entrySet;
    }

    /**
     * Вычисляет хэш для ключа.
     *
     * @param key - ключ.
     * @return - хэш ключа.
     */
    private int hash(Object key) {
        return key == null ? 0 : Math.abs(key.hashCode() % table.length);
    }

    /**
     * Проверяет, нужно ли увеличивать размер HashMap.
     *
     * @return true, если нужно увеличивать размер, иначе false.
     */
    private boolean shouldResize() {
        return size > LOAD_FACTOR * table.length;
    }

    /**
     * Увеличивает размер HashMap и перехэширует элементы.
     */
    private void resize() {
        Node<K, V>[] newTable = new Node[table.length * 2];
        for (Node<K, V> node : table) {
            while (node != null) {
                int newIndex = hash(node.key);
                Node<K, V> newNode = new Node<>(node.key, node.value);
                newNode.next = newTable[newIndex];
                newTable[newIndex] = newNode;
                node = node.next;
            }
        }
        table = newTable;
    }
}
