public class LinkedStack<E> implements Stack<E> {
    private SinglyLinkedList<E> list = new SinglyLinkedList<>();

    public LinkedStack(){}

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public void push(E e) {
        list.addFirst(e);
    }

    @Override
    public E top() {
        return list.get(0);
    }

    @Override
    public E pop() {
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
        LinkedStack<String> stk = new LinkedStack<>();
        stk.push("blah");
        stk.push("you");
        stk.push("there");
        stk.push("hi");
        stk.push("Hey");

        System.out.println("Is the stack empty: " + stk.isEmpty());
        System.out.println();
        System.out.println(stk.top());

        System.out.println(stk.pop());
        System.out.println(stk.pop());
        System.out.println(stk.pop());
        System.out.println(stk.pop());
        System.out.println(stk.pop());
        System.out.println();
        System.out.println("Is the stack empty: " + stk.isEmpty());
    }

}