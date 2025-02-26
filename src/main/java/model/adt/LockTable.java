package model.adt;

import exception.MyException;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class LockTable<V> implements ILockTable<Integer, V> {

    private Map<Integer, V> map;
    private int freeLocation;
    private int maxFreeLocation;

    public LockTable() {
        this.map = new HashMap<>();
        this.freeLocation = 1;
        this.maxFreeLocation = 1;
    }

    @Override
    public synchronized int allocate(V value) {
        freeLocation = this.getFreeLocation();
        int address = freeLocation;
        map.put(address, value);
        //freeLocation = this.getFreeLocation();
        if (freeLocation == maxFreeLocation) {
            freeLocation++;
            maxFreeLocation++;
        }
        if (freeLocation > maxFreeLocation) {
            maxFreeLocation = freeLocation;
        }
        return address;
    }

    @Override
    public void deallocate(int address) throws MyException {
        if (!map.containsKey(address)) {
            throw new MyException("Invalid address: " + address);
        }
        map.remove(address);
    }

    @Override
    public synchronized V get(int address) throws MyException {
        if (!map.containsKey(address)) {
            throw new MyException("Invalid address: " + address);
        }
        return map.get(address);
    }

    @Override
    public synchronized boolean contains(int key) {
        return map.containsKey(key);
    }

    @Override
    public synchronized void update(int address, V value) throws MyException {
        if (!map.containsKey(address)) {
            throw new MyException("Invalid address: " + address);
        }
        map.put(address, value);
    }

    public Stream<Map.Entry<Integer, V>> stream() {
        return map.entrySet().stream();
    }

    @Override
    public void setContent(Map<Integer, V> map) {
        this.map = map;
    }

    public Map<Integer, V> getContent() {
        //return (IHeap<Integer, V>) map;
        return map;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (int key : map.keySet()) {
            str.append(key).append(" -> ").append(map.get(key));
            str.append("\n");
        }
        return str.toString();
    }

    public int getFreeLocation() {
        for(int i=1;i<=this.maxFreeLocation;i++) {
            if(!this.map.containsKey(i)) {
                return i;
            }
        }
        return this.maxFreeLocation++;
    }
}
