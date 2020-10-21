public class LinkedQueue<E> implements Queue<E> {
    SinglyLinkedList<E> list = new SinglyLinkedList<>();
    public LinkedQueue(){}

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

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for(int i = 0; i < list.size(); i++){
            sb.append(list.get(i));
            if(i < list.size()-1){
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    public static void main(String[] args) {
        LinkedQueue<String> queue = new LinkedQueue<>();
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