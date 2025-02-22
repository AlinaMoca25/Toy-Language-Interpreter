package model.adt;
import exception.MyException;
import exception.StackException;

import java.util.Stack;

public class MyStack<T> implements IMyStack<T> {
    Stack<T> stack;

    public MyStack() {
        stack  = new Stack<T>();
    }

    public T pop() throws MyException {
        if (stack.isEmpty()) {
            throw new StackException("the stack is empty!");
        }
        else
            return stack.pop();
    }

    public void push(T element) {
        stack.push(element);
    }

    public int getSize() {
        return stack.size();
    }

    public boolean isEmpty() {
        return stack.isEmpty();
    }

    public String toString() {
        StringBuilder str = new StringBuilder();
//        if(!stack.isEmpty()) {
//            T left = stack.peek();
//            str.append(left);
//            str.append("\n");
//        }
//        if(!stack.isEmpty()) {
//            stack.pop();
//            T root = stack.peek();
//            stack.pop();
//            str.append(root);
//            str.append("\n");
//        }
        for (T item: stack) {
            str.append(item);
            str.append("\n");
        }
        return str.toString();
    }

    public Stack<T> getAll() {
        return this.stack;
    }

}