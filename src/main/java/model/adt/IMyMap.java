package model.adt;
import exception.MyException;
import java.util.List;
import java.util.Map;

public interface IMyMap<K, V> {
    V get(K key) throws MyException;
    void insert(K key, V value);
    boolean contains(K key);
    void remove(K key) throws MyException;
    String toString();
    void update(K id, V value);
    int getSize();
    List<V> values();

    IMyMap<K,V> deepCopy();
    Map<K, V> getContent();
}