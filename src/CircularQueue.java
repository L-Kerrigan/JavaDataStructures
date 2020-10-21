public class CircularQueue<E> implements Queue<E> {
    CircularlyLinkedList<E> q = new CircularlyLinkedList<E>();

    @Override
    public int size() {
        return q.size();
    }

    @Override
    public boolean isEmpty() {
        return q.isEmpty();
    }

    @Override
    public void enqueue(E e) {
        q.addLast(e);
    }

    @Override
    public E first() {
        return q.first();
    }

    @Override
    public E dequeue() {
        return q.removeFirst();
    }

    public void rotate(){
        q.rotate();
    }

    public static void main(String[] args) {
        CircularQueue<String> queue = new CircularQueue<>();
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
