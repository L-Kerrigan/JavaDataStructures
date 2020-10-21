public class ArrayStack<E> implements Stack<E> {
    public static final int CAPACITY = 1000;
    private E[] elements;
    private int t = -1;
    public ArrayStack() { this(CAPACITY); }

    public ArrayStack(int capacity){
        elements = (E[]) new Object[capacity];
    }

    @Override
    public int size() {
        return t + 1;
    }

    @Override
    public boolean isEmpty() {
        return t == -1;
    }

    @Override
    public void push(E e) throws IllegalStateException {
        if(size() == elements.length){
            throw new IllegalStateException("Stack is full.");
        }
        elements[++t] = e;
    }

    @Override
    public E top() {
        if(isEmpty()){
            return null;
        }
        return elements[t];
    }

    @Override
    public E pop() {
        if (isEmpty()) {
            return null;
        }
        E ans = elements[t];
        elements[t] = null;
        t--;
        return ans;
    }

    public static void main(String[] args) {
        ArrayStack<String> stk = new ArrayStack<>();
        stk.push("working");
        stk.push("be");
        stk.push("should");
        stk.push("you");
        stk.push("there");
        stk.push("Hey");

        System.out.println("Is the stack empty: " + stk.isEmpty());
        System.out.println();
        System.out.println(stk.top());

        System.out.println(stk.pop());
        System.out.println(stk.pop());
        System.out.println(stk.pop());
        System.out.println(stk.pop());
        System.out.println(stk.pop());
        System.out.println(stk.pop());
        System.out.println();
        System.out.println("Is the stack empty: " + stk.isEmpty());
    }

}

