public class LinkedDeque<E> implements Deque<E> {
    DoublyLinkedList<E> list = new DoublyLinkedList<>();

    public static void main(String[] args) {

    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public E first() {
        return list.get(0);
    }

    @Override
    public E last() {
        return list.get(size());
    }

    @Override
    public void addFirst(E e) {
        list.addFirst(e);
    }

    @Override
    public void addLast(E e) {
       list.addLast(e);
    }

    @Override
    public E removeFirst() {
        return list.removeFirst();
    }

    @Override
    public E removeLast() {
        return list.removeLast();
    }
}
