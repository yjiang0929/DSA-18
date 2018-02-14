import java.util.*;

public class MyHashMap<K, V> implements Map<K, V> {

    // average number of entries per bucket before we grow the map
    private static final double ALPHA = 1.0;
    // average number of entries per bucket before we shrink the map
    private static final double BETA = .25;

    // resizing factor: (new size) = (old size) * (resize factor)
    private static final double SHRINK_FACTOR = 0.5, GROWTH_FACTOR = 2.0;

    private static final int MIN_BUCKETS = 16;

    // array of buckets
    protected LinkedList<Entry>[] buckets;
    private int size = 0;

    public MyHashMap() {
        initBuckets(MIN_BUCKETS);
    }

    public class Entry implements Map.Entry<K, V> {
        private K key;
        private V value;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V newValue) {
            value = newValue;
            return value;
        }
    }

    /**
     * given a key, return the bucket where the `K, V` pair would be stored if it were in the map.
     */
    private LinkedList<Entry> chooseBucket(Object key) {
        int code = key.hashCode();
        return buckets[code % buckets.length];
        // hint: use key.hashCode() to calculate the key's hashCode using its built in hash function
        // then use % to choose which bucket to return.
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * return true if key is in map
     */
    @Override
    public boolean containsKey(Object key) {
        LinkedList<Entry> curr_bucket = chooseBucket(key);
        for (int i=0;i<curr_bucket.size();i++) {
            if (curr_bucket.get(i).key.equals(key)) {
                return true;
            }
        }
        return false;
    }

    /**
     * return true if value is in map
     */
    @Override
    public boolean containsValue(Object value) {
        for (int i=0;i<buckets.length;i++) {
            for (int j=0;j<buckets[i].size();j++) {
                if (buckets[i].get(j).value == null) {
                    if (value == null) {
                        return true;
                    }
                } else {
                    if (buckets[i].get(j).value.equals(value)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public V get(Object key) {
        LinkedList<Entry> curr_bucket = chooseBucket(key);
        for (int i=0;i<curr_bucket.size();i++) {
            if (curr_bucket.get(i).key.equals(key)) {
                return curr_bucket.get(i).value;
            }
        }
        return null;
    }

    /**
     * add a new key-value pair to the map. Grow if needed, according to `ALPHA`.
     * @return the value associated with the key if it was previously in the map, otherwise null.
     */
    @Override
    public V put(K key, V value) {
        LinkedList<Entry> curr_bucket = chooseBucket(key);
        V temp = null;
        if (containsKey(key)) {
            temp = remove(key);
        }
//        for (int i=0;i<curr_bucket.size();i++) {
//            if (curr_bucket.get(i).key.equals((key))) {
//                V temp = curr_bucket.get(i).getValue();
//                curr_bucket.get(i).setValue(value);
//                return temp;
//            }
//        }
        curr_bucket.addLast(new Entry(key,value));
        size++;
        if (size > ALPHA * buckets.length) {
            rehash(GROWTH_FACTOR);
        }
        return temp;
        // hint: use chooseBucket() to determine which bucket to place the pair in
        // hint: use rehash() to appropriately grow the hashmap if needed
    }

    /**
     * Remove the key-value pbuckets[i].get(j).air associated with the given key. Shrink if needed, according to `BETA`.
     * Make sure the number of buckets doesn't go below `MIN_BUCKETS`. Do nothing if the key is not in the map.
     * @return the value associated with the key if it was in the map, otherwise null.
     */
    @Override
    public V remove(Object key) {
        LinkedList<Entry> curr_bucket = chooseBucket(key);
        for (int i=0;i<curr_bucket.size();i++) {
            if (curr_bucket.get(i).key.equals(key)) {
                size--;
                V temp = curr_bucket.remove(i).value;
                if (size < BETA * buckets.length && buckets.length * SHRINK_FACTOR >= MIN_BUCKETS) {
                    rehash(SHRINK_FACTOR);
                }
                return temp;
            }
        }
        return null;
        // hint: use chooseBucket() to determine which bucket the key would be
        // hint: use rehash() to appropriately grow the hashmap if needed
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        for (Map.Entry<? extends K, ? extends V> entry : m.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    /**
     * Changes the number of buckets and rehashes the existing entries.
     * If growthFactor is 2, the number of buckets doubles. If it is 0.25,
     * the number of buckets is divided by 4.
     */
    private void rehash(double growthFactor) {
        LinkedList<Entry>[] temp = buckets;
        int new_size = (int) (buckets.length * growthFactor);
        clear();
        initBuckets(new_size);
        for (LinkedList<Entry> i : temp) {
            for (Entry j : i) {
                put(j.getKey(), j.getValue());
            }
        }
        // hint: once you have removed all values from the buckets, use put(k, v) to add them back in the correct place
    }

    private void initBuckets(int size) {
        buckets = new LinkedList[size];
        for (int i = 0; i < size; i++) {
            buckets[i] = new LinkedList<>();
        }
    }

    public void clear() {
        initBuckets(buckets.length);
        size = 0;
    }

    @Override
    public Set<K> keySet() {
        Set<K> keys = new HashSet<>();
        for (Map.Entry<K, V> e : entrySet()) {
            keys.add(e.getKey());
        }
        return keys;
    }

    @Override
    public Collection<V> values() {
        Collection<V> values = new ArrayList<>();
        for (Map.Entry<K, V> e : entrySet()) {
            values.add(e.getValue());
        }
        return values;
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        Set<Map.Entry<K, V>> set = new HashSet<>();
        for (LinkedList<Entry> map : buckets) {
            set.addAll(map);
        }
        return set;
    }
}
