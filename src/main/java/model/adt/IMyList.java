package model.adt;
import java.util.List;

public interface IMyList<T> {
    List<T> getAll();
    void add(T item);
    String toString();
}