package cascadia;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * A type of map which is sorted based on the values.
 * The map is sorted from low to high.
 *
 * @param <K> the key
 * @param <V> the value stored at the key (must be an Integer)
 */
public class ValueSortedMap<K, V extends Integer> implements Map<K, V> {
    // obviously the performance of this won't be as good as a hashMap,
    // but it'll allow me to sort the map by values

    ArrayList<K> keys = new ArrayList<>();
    ArrayList<V> values = new ArrayList<>();

    @Override
    public int size() {
        return keys.size();
    }

    @Override
    public boolean isEmpty() {
        return keys.size() == 0;
    }

    @Override
    public boolean containsKey(Object o) {
        return keys.contains(o);
    }

    @Override
    public boolean containsValue(Object o) {
        return values.contains(o);
    }

    // realistically for our use there's no need to be able to find an element
    // by a key, so we can just get it by index
    @Override
    public V get(Object o) {
        return values.get((Integer) o);
    }

    @Override
    public V put(K k, V v) {
        V old;
        if (keys.contains(k)) {
            int idx = keys.indexOf(k);
            old = values.get(idx);
            keys.remove(idx);
            values.remove(idx);
        } else {
            old = null;
        }

        int newIdx = findIndexToInsert(v);
        keys.add(newIdx, k);
        values.add(newIdx, v);

        return old;
    }

    @Override
    public V remove(Object o) {
        V val = values.get((Integer) o);
        int idx = (Integer) o;
        values.remove(idx);
        keys.remove(idx);
        return val;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> map) {

    }

    @Override
    public void clear() {
        keys.clear();
        values.clear();
    }

    @Override
    public Set<K> keySet() {
        return new HashSet<>(keys);
    }

    @Override
    public Collection<V> values() {
        return values;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        Set<Entry<K, V>> set = new HashSet<>();
        for (int i = 0; i < size(); i++) {
            set.add(new AbstractMap.SimpleEntry<>(keys.get(i), values.get(i)));
        }
        return set;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder("{");
        for (int i = 0; i < size(); i++) {
            string.append(String.format("%s=%d", keys.get(i), values.get(i)));
            if (i < size() - 1) {
                string.append(", ");
            }
        }
        string.append("}");
        return string.toString();
    }

    private int findIndexToInsert(int target) {
        int low = 0;
        int high = values.size() - 1;

        while (low <= high) {
            int mid = (low + high) / 2;
            int midVal = values.get(mid);

            if (midVal < target) {
                low = mid + 1;
            } else if (midVal > target) {
                high = mid - 1;
            } else {
                // target found at mid
                return mid;
            }
        }
        // target not found, return index to insert
        return low;
    }

}
