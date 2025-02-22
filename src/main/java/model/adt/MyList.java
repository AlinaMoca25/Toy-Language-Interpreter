package model.adt;
import java.util.ArrayList;
import java.util.List;

public class MyList<T> implements IMyList<T> {
    private List<T> list;

    public MyList() {
        list = new ArrayList<T>();
    }

    public List<T> getAll() {
        return list;
    }

    public void add(T element) {
        list.add(element);
    }

    void setList(List<T> list) {
        this.list = list;
    }

    public String toString() {
        StringBuilder str = new StringBuilder();
        for (T element : list) {
            str.append(element.toString());
            str.append("\n");
        }
        return str.toString();
    }

}