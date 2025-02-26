package model.adt;

import exception.MyException;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class SemaphoreTable<V> implements ISemaphoreTable<Integer, V> {
    private Map<Integer, V> semaphore;
    private int freeLocation;
    private int maxFreeLocation;

    public SemaphoreTable() {
        this.semaphore = new HashMap<>();
        this.freeLocation = 1;
        this.maxFreeLocation = 1;
    }

    @Override
    public int allocate(V value) {
        freeLocation = this.getFreeLocation();
        int address = freeLocation;
        semaphore.put(address, value);
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
        if (!semaphore.containsKey(address)) {
            throw new MyException("Invalid address: " + address);
        }
        semaphore.remove(address);
    }

    @Override
    public synchronized V get(int address) throws MyException {
        if (!semaphore.containsKey(address)) {
            throw new MyException("Invalid address: " + address);
        }
        return semaphore.get(address);
    }

    @Override
    public boolean contains(int key) {
        return semaphore.containsKey(key);
    }

    @Override
    public synchronized void update(int address, V value) throws MyException {
        if (!semaphore.containsKey(address)) {
            throw new MyException("Invalid address: " + address);
        }
        semaphore.put(address, value);
    }

    public Stream<Map.Entry<Integer, V>> stream() {
        return semaphore.entrySet().stream();
    }

    @Override
    public void setContent(Map<Integer, V> map) {
        this.semaphore = map;
    }

    public Map<Integer, V> getContent() {
        //return (IHeap<Integer, V>) map;
        return semaphore;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (int key : semaphore.keySet()) {
            str.append(key).append(" -> ").append(semaphore.get(key));
            str.append("\n");
        }
        return str.toString();
    }

    public int getFreeLocation() {
        for(int i=1;i<=this.maxFreeLocation;i++) {
            if(!this.semaphore.containsKey(i)) {
                return i;
            }
        }
        return this.maxFreeLocation++;
    }
}
