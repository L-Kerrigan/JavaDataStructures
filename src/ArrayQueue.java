public class ArrayQueue<E> implements Queue<E> {
    public static final int CAPACITY = 1000;
    private E[] data;
    private int first = 0;
    private int sz = 0;

    public ArrayQueue() {
        this(CAPACITY);
    }

    public ArrayQueue(int capacity){
        data = (E[]) new Object[capacity];
    }

    @Override
    public int size() {
        return sz;
    }

    @Override
    public boolean isEmpty() {
        return sz == 0;
    }

    @Override
    public void enqueue(E e) throws IllegalStateException {
        if(sz == data.length){
            throw new IllegalStateException("Queue is full.");
        }
        int avail = (first + sz) % data.length;
        data[avail] = e;
        sz++;
    }

    @Override
    public E first() {
        if(isEmpty()){
            return null;
        }
        return data[first];
    }

    @Override
    public E dequeue() {
        if(isEmpty()){
            return null;
        }
        E ans = data[first];
        first = (first + 1) % data.length;
        sz--;
        return ans;
    }

    public static void main(String[] args) {
        ArrayQueue<String> queue = new ArrayQueue<>();
        queue.enqueue("Hey");
        queue.enqueue("there");
        queue.enqueue("you");
        queue.enqueue("should");
        queue.enqueue("be");
        queue.enqueue("working");

        System.out.println("Is the queue empty: " + queue.isEmpty());
        System.out.println();
        System.out.println(queue.first());

        System.out.println(queue.dequeue());
        System.out.println(queue.dequeue());
        System.out.println(queue.dequeue());
        System.out.println(queue.dequeue());
        System.out.println(queue.dequeue());
        System.out.println(queue.dequeue());
        System.out.println();
        System.out.println("Is the queue empty: " + queue.isEmpty());
    }

}