public class ArrayDeque<E> implements Deque<E> {
    public static final int CAPACITY = 100;
    private E[] data;
    private int first;
    private int last;
    private int size;

    public ArrayDeque(int size){
        data = (E[]) new Object[CAPACITY];
        first = -1;
        last = 0;
        this.size = size;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return first == -1;
    }

    @Override
    public E first() {
        if (isEmpty()) {
            return null;
        }
        return data[first];
    }

    @Override
    public E last() {
        if(isEmpty() || last < 0){
            return null;
        }
        return data[last];
    }

    @Override
    public void addFirst(E e) {
        if(size == CAPACITY){
            System.out.println("Deque is full.");
            return;
        }
        if(first == -1){
            first = 0;
            last = 0;
        } else if(first == 0) {
            first = size - 1;
        } else {
            first -= 1;
        }
        data[first] = e;
        size++;
    }

    @Override
    public void addLast(E e) {
        if(size == CAPACITY){
            System.out.println("Deque is full.");
            return;
        }

        if(first == -1){
            first = 0;
            last = 0;
        } else if(last == size-1){
            last = 0;
        } else {
            last += 1;
        }

        data[last] = e;
        size++;
    }

    @Override
    public E removeFirst() {
        if(isEmpty()){
            return null;
        }
        if(first == last){
            first = -1;
            last = -1;
            size--;
            return data[0];
        } else {
            if(first == size-1){
                size--;
                first = 0;
                return data[size];
            } else {
                size--;
                first += 1;
                return data[first-1];
            }
        }
    }

    @Override
    public E removeLast() {
        if(isEmpty()){
            return null;
        }
        if(first == last){
            first = -1;
            last = -1;
            size--;
            return data[0];
        } else if (last == 0) {
            size--;
            last = size;
            return data[0];
        } else {
            size--;
            last -= 1;
            return data[last + 1];
        }
    }

    @Override
    public String toString(){
        String ans = data[0].toString();
        for(int i = 1; i < size; i++){
            if(data[i] != null) {
                ans += " " + data[i];
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        ArrayDeque<Integer> dq = new ArrayDeque<>(5);
        dq.addLast(5);
        dq.addLast(10);
        System.out.println(dq.toString());
        System.out.println(dq.last());
        System.out.println(dq.removeLast());
        dq.addFirst(15);
        System.out.println(dq.first());
        System.out.println(dq.removeFirst());
        System.out.println(dq.toString());
    }
}
