import java.lang.reflect.Method;
import java.util.Iterator;

public class ArrayStack<T extends Cloneable> implements Stack<T> {
    public T[] stack;
    public int head;
    public int size;

    public ArrayStack(int size) throws StackException {
        if (size < 0) {
            throw new NegativeCapacityException();
        }
        this.stack = (T[]) new Cloneable[size];
        this.head = -1;
        this.size = size;
    }

    @Override
    public void push(T element) throws StackException {
        head++;
        if (!(head < size)) {
            head--;
            throw new StackOverflowException();
        } else {
            this.stack[head] = element;
        }
    }

    @Override
    public T pop() throws StackException {
        if (head == -1) {
            throw new EmptyStackException();
        }
        T elementToReturn = this.stack[head];
        this.stack[head] = null;
        head--;
        return elementToReturn;
    }

    @Override
    public T peek() {
        if (head == -1) {
            throw new EmptyStackException();
        }
        return this.stack[head];
    }

    @Override
    public int size() {
        return (head + 1);
    }

    public boolean isEmpty() {
        return head == -1;
    }

    @Override
    public Iterator<T> iterator() {
        return new StackIterator();
    }

    @Override
    public ArrayStack<T> clone() {
        try {
            ArrayStack<T> copy = (ArrayStack<T>) super.clone();
            copy.stack = stack.clone();
            for (int i = 0; i <= head; i++) {
                if (stack[i] instanceof Cloneable) {
                    Method cloneMethod = stack[i].getClass().getMethod("clone");
                    copy.stack[i] = (T) cloneMethod.invoke(stack[i]);
                }
            }
            return copy;
        } catch (CloneNotSupportedException | ReflectiveOperationException e) {
            return null;
        }
    }

    public class StackIterator implements Iterator<T> {
        private int currentIndex;

        public StackIterator() {
            currentIndex = head;
        }

        @Override
        public boolean hasNext() {
            return currentIndex >= 0;
        }

        @Override
        public T next() {
            T value = stack[currentIndex];
            currentIndex--;
            return value;
        }
    }
}