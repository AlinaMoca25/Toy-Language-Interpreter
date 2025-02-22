package model.adt;
import exception.MyException;

import java.util.Stack;


public interface IMyStack<T> {
    T pop() throws MyException;
    public void push(T element);
    public int getSize();
    public boolean isEmpty();
    String toString();
    public Stack<T> getAll();
}