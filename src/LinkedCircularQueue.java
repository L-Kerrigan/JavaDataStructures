/**
 * Realization of a circular FIFO queue as an adaptation of a
 * CircularlyLinkedList. This provides one additional method not part of the
 * general Queue interface. A call to rotate() is a more efficient simulation of
 * the combination enqueue(dequeue()). All operations are performed in constant
 * time.
 */

public class LinkedCircularQueue<E> implements Queue<E> {
    CircularlyLinkedList<E> list = new CircularlyLinkedList<>();

    public static void main(String[] args) {
        LinkedCircularQueue<String> queue = new LinkedCircularQueue<>();
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

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public void enqueue(E e) {
        list.addLast(e);
    }

    @Override
    public E first() {
        return list.get(0);
    }

    @Override
    public E dequeue() {
        return list.removeFirst();
    }

}