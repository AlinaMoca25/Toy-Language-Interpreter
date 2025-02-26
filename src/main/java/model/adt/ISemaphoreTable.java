package model.adt;

import exception.MyException;

import java.util.Map;

public interface ISemaphoreTable<K, V> {
    V get(int address) throws MyException;
    boolean contains(int key);
    int allocate(V value);
    void deallocate(int address) throws MyException;
    void update(int address, V value) throws MyException;
    void setContent(Map<K, V> newContent);
    Map<Integer, V> getContent();
}
