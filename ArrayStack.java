import java.util.Arrays;
import java.util.Iterator;

public class ArrayStack<T extends  Cloneable> implements Stack<T> {
    public T[] stack;
    public int head;
    public int size;
    public int counter;

    public ArrayStack(int size) throws StackException{
        if (size < 0) {
            throw new NegativeCapacityException();
        }
        this.stack = (T[]) new Cloneable[size];
        this.head = -1;
        this.size = size;
        this.counter = head;
    }

    @Override
    public void push(T element) throws StackException{
        head++;
        if (!(head < size)){
            head--;
            throw new StackOverflowException();
        }else {
            this.stack[head] = element;
            counter = head;
        }
    }


    @Override
    public T pop() throws StackException{
        if (head == -1){
            throw new EmptyStackException();
        }
        T elementToReturn = this.stack[head];
        this.stack[head]= null;
        head--;
        counter = head;
        return elementToReturn;
    }

    @Override
    public T peek(){
        if (head == -1){
            throw new EmptyStackException();
        }
        return this.stack[head];
    }

    @Override
    public int size(){
        return (head + 1);
    }

    public boolean isEmpty(){
        return head==-1;
    }

    @Override
    public Iterator<T> iterator(){
        return new StackIterator<T>();
    }

    @Override
    public ArrayStack<T> clone(){
        try {
            int i = head;
            ArrayStack copy = (ArrayStack) super.clone();
            copy.stack = stack.clone();
            for (T element : stack){
                copy.stack[i]= element.clone();
                i--;
            }
            return copy;
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

    public class StackIterator<T> implements Iterator<T>{

        @Override
        public boolean hasNext(){
            return (counter >= 0);
        }

        @Override
        public T next(){
            T value = (T) stack[counter];
            counter --;
            return value;
        }
    }
}
