public class BoundedStack<E> implements Stack<E> {
    public final int CAPACITY;
    private E[] elements;
    private int t = -1;

    public BoundedStack(int maxSize) {
        CAPACITY = maxSize;
        elements = (E[]) new Object[maxSize];
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
        if (isFull()) {
            throw new IllegalStateException("Stack is full.");
        }
        elements[++t] = e;
    }

    @Override
    public E top() {
        if (isEmpty()) {
            return null;
        }
        return elements[t];
    }

    public boolean isFull(){
        return CAPACITY == size();
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
        BoundedStack<String> stk = new BoundedStack<>(5);
        stk.push("weirdo");
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

