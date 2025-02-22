package model.adt;
import exception.DictException;
import exception.MyException;

import java.util.*;

public class MyMap<K, V> implements IMyMap<K,V> {
    private Map<K, V> map;

    public MyMap() {
        map = new HashMap<>();
    }

    public MyMap(Map<K, V> map) {
        this.map = map;
    }

    public V get(K key) throws MyException {
        if (!map.containsKey(key)) {
            throw new DictException("Key not found!");
        }
        return map.get(key);
    }

    public void insert(K key, V value) {
        map.put(key, value);
    }

    public boolean contains(K key) {
        return map.containsKey(key);
    }

    public void remove(K key) throws MyException {
        if (!map.containsKey(key)) {
            throw new DictException("Key not found!");
        }
        map.remove(key);
    }

    public String toString() {
        StringBuilder str = new StringBuilder();
        for (K key : map.keySet()) {
            str.append(key.toString()).append(" -> ").append(map.get(key));
            str.append("\n");
        }
        return str.toString();
    }

    public int getSize() {
        return map.size();
    }

    @Override
    public void update(K id, V value) {
        map.put(id, value);
    }

    public List<V> values() {
        return new ArrayList<>(map.values());
    }

    public MyMap<K,V> deepCopy() {
        //return new MyMap<>(map);

        MyMap<K,V> copy = new MyMap<>();
        for (K key : map.keySet()) {
            copy.insert(key, map.get(key));
        }
        return copy;
    }


    @Override
    public Map<K, V> getContent() {
        return map;
    }

}