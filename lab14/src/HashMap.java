import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.NoSuchElementException;

public class HashMap<K, V> implements Map61BL<K, V>{
    private int size;
    @Override
    public void clear() {
        buckets = (LinkedList<Entry<K, V>>[]) new LinkedList[initialCapacity];
        for (int i =0; i < initialCapacity; i++) {
            buckets[i] = new LinkedList<Entry<K, V>>();
        }
        size = 0;
    }
    @Override
    public boolean containsKey(K key) {
        int index = containsKeyHelper(key);
        LinkedList<Entry<K, V>> bucket = buckets[index];
        for (Entry<K, V> entry : bucket) {
            if (entry.key.equals(key)) {
                return true;
            }
        }
        return false;
    }
    public int containsKeyHelper(K key) {
        return Math.floorMod(key.hashCode(), initialCapacity);
    }

    @Override
    public V get(K key) {
        int index = containsKeyHelper(key);
        LinkedList<Entry<K, V>> bucket = buckets[index];
        for (Entry<K, V> entry : bucket) {
            if (entry.key.equals(key)) {
                return entry.value;
            }
        }
        return null;
    }

    @Override
    public void put(K key, V value) {
        int index = containsKeyHelper(key);
        LinkedList<Entry<K, V>> bucket = buckets[index];
        for (Entry<K, V> entry : bucket) {
            if (entry.key.equals(key)) {
                entry.value = value;
                return;
            }
        }
        bucket.add(new Entry<>(key, value));
        size++;
        if ((double) size / initialCapacity > loadFactor) {
            resize();
        }
    }


    @Override
    public V remove(K key) {
        int index = containsKeyHelper(key);
        LinkedList<Entry<K, V>> bucket = buckets[index];
        for (Entry<K, V> entry : bucket) {
            if (entry.key.equals(key)) {
                V oldValue = (V) entry.value;
                bucket.remove(entry);
                size--;
                return oldValue;
            }
        }
        return null;
    }

    @Override
    public boolean remove(K key, V value) {
        int index = containsKeyHelper(key);
        LinkedList<Entry<K, V>> bucket = buckets[index];
        for (Entry<K, V> entry : bucket) {
            if (entry.key.equals(key)) {
                bucket.remove(entry);
                size--;
                return true;
            }
        }
        return false;
    }

    @Override
    public int size() {
        if ((double) size / initialCapacity > loadFactor ) {
            resize();
        }
        return size;
    }
    public void resize() {
        int newCapacity = initialCapacity * 2;
        LinkedList<Entry<K, V>>[] oldBuckets = buckets;
        buckets = (LinkedList<Entry<K, V>>[]) new LinkedList[newCapacity];
        for (int i =0; i < newCapacity; i++) {
            buckets[i] = new LinkedList<>();
        }
        initialCapacity = newCapacity;
        int oldSize = size;
        size = 0;
        for (LinkedList<Entry<K, V>> bucket : oldBuckets) {
            for (Entry<K, V> entry : bucket) {
                put((K) entry.key, (V) entry.value);
            }
        }
    }
    public int capacity() {
        return initialCapacity;
    }

    @Override
    public Iterator<K> iterator() {
        return new hashMapIterator();
    }
    private class hashMapIterator implements Iterator<K> {
        private int bucketIndex;
        private Iterator<Entry<K, V>> bucketIterator;
        public hashMapIterator() {
            bucketIndex = 0;
            bucketIterator = null;
            advanceToNextNonEmptyBucket();
        }
        private void advanceToNextNonEmptyBucket() {
            while (bucketIndex < initialCapacity
            && (bucketIterator == null || !bucketIterator.hasNext())) {
                if (buckets[bucketIndex] != null && !buckets[bucketIndex].isEmpty()) {
                    bucketIterator = buckets[bucketIndex].iterator();

                }
                else {
                    bucketIndex++;
                }
            }
        }
        @Override
        public boolean hasNext() {
            return bucketIndex < initialCapacity && bucketIterator != null && bucketIterator.hasNext();

        }
        @Override
        public K next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            K key = bucketIterator.next().key;
            if (!bucketIterator.hasNext()) {
                bucketIndex++;
                advanceToNextNonEmptyBucket();
            }
            return key;
        }
    }

    /* TODO: Instance variables here */
    private LinkedList<Entry<K, V>>[] buckets;
    private static final int CAPACITY = 16;
    private static final double LOAD_FACTOR = 0.75;
    private double loadFactor;
    private int initialCapacity;
    /* TODO: Constructors here */
    public HashMap() {
        this(CAPACITY,LOAD_FACTOR);
    }
    public HashMap(int initialCapacity) {
        this(initialCapacity,LOAD_FACTOR);
    }
    public HashMap(int initialCapacity, double loadFactor) {
        this.initialCapacity = initialCapacity;
        this.loadFactor = loadFactor;
        buckets = (LinkedList<Entry<K, V>>[]) new LinkedList[initialCapacity];
        for (int i = 0; i < initialCapacity; i++) {
            buckets[i] = new LinkedList<Entry<K, V>>();
        }
    }
    /* TODO: Interface methods here */

    private static class Entry<K, V> {

        private K key;
        private V value;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        /* Returns true if this key matches with the OTHER's key. */
        public boolean keyEquals(Entry other) {
            return key.equals(other.key);
        }

        /* Returns true if both the KEY and the VALUE match. */
        @Override
        public boolean equals(Object other) {
            return (other instanceof Entry
                    && key.equals(((Entry) other).key)
                    && value.equals(((Entry) other).value));
        }

        @Override
        public int hashCode() {
            return super.hashCode();
        }
    }
}
