public interface Stack<E extends Cloneable> extends Iterable<E>, Cloneable {
    void push(E element);
    E pop();
    E peek();
    int size();
    boolean isEmpty();

    Stack<E> clone();
}


