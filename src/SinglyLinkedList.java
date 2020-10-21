import java.util.Iterator;
import java.util.NoSuchElementException;

public class SinglyLinkedList<E> implements List<E> {
	private Node<E> head = null; //The head node of the list
	private Node<E> tail = null; //The tail node of the list


	//For testing
	public E getHead() {
		if(isEmpty()){
			return null;
		}
		return head.element;
	}
	public E getTail() {
		if(isEmpty()){
			return null;
		}
		return tail.element;
	}

	private class Node<E> { //Class for nodes
	    private E element; //Declaring the element of the node
		private Node<E> next; //Pointer to the next node

		public Node(E e){ //Constructor
			element = e; //Initialising element
			next = null; //Initialising next
		}

        public E getElement() { //Getter for element
            return element;
        }

        public Node<E> getNext() { //Getter for next node
            return next;
        }

		public void setElement(E element) {
			this.element = element;
		}

		public void setNext(Node<E> next) {
			this.next = next;
		}
	}



	@Override
	public boolean isEmpty() {
		//If the size is 0, the list must be empty
		return size() == 0;
	}

	@Override
	public E get(int i) {
		if(i == 0){
			return head.getElement();
		} else {
			Node<E> temp = head;
			for (int j = 0; j < i; j++) {
				temp = temp.getNext();
			}
			return temp.getElement();
		}
	}

	@Override
	public void add(int i, E e) {
		Node<E> newNode = new Node<>(e);
		if(i == 0){ //If the index is 0, we can just call the add first method
			addFirst(e);
		} else if(size() == 1 && i == 1) { //If the size is 1, we know to add it straight after the head and make it the tail
			tail = newNode;
			head.setNext(tail);
		} else if (i == size()) {
			addLast(e);
		} else {
			Node<E> temp = head;
			for (int j = 0; j < i-1; j++) {
				temp = temp.getNext();
			}
			newNode.setNext(temp.getNext());
			temp.setNext(newNode);
		}
		if(get(size()-1) != tail.getElement()) {
			Node<E> temp = head;
			for (int k = 0; k < size(); k++) {
				temp = temp.getNext();
			}
			tail = temp;
		}
	}

	@Override
	public E remove(int i) {
		if(head == null){
		    throw new RuntimeException("Can't delete anything if nothing is there!");
		} else if (i == 0) {
            removeFirst();
        } else if(i == size()){
			removeLast();
		}

        Node<E> cur = head;
		Node<E> prev = null;

		for (int j = 0; j < i; j++) {
			prev = cur;
			cur = cur.next;
		}

		if(cur == null){
			throw new RuntimeException("Can't delete anything if nothing is there!");
		}

		prev.next = cur.next;
		return cur.getElement();


		/*
		while(cur != null && !cur.getElement().equals(i)) {
            prev = cur;
            cur = cur.next;
        }

		prev.next = cur.next;
		return cur.getElement();
		*/
	}

	@Override
	public Iterator<E> iterator() {
		Iterator<E> it = new Iterator<E>() {
			private boolean removeable = false;
			private int index = 0;


			@Override
			public boolean hasNext() {
			    return index < size();
			}

			@Override
            public E next() throws NoSuchElementException {
				if(index == size()){
					throw new NoSuchElementException("No next element.");
				} else {
					removeable = true;
					return SinglyLinkedList.this.get(index++);
				}
            }

			@Override
			public void remove() throws IllegalStateException {
				if(removeable){
					SinglyLinkedList.this.remove(index);
				} else {
					throw new IllegalStateException("Nothing to remove at current index");
				}
			}
		};
		return it;
	}

	@Override
	public int size() {
		Node<E> temp = head; //The temporary node, temp, is assigned the same values as the head
		int count = 0;
		while(temp != null){ //While temp isn't null, we continue counting
			count++;
			temp = temp.getNext(); //After counting, move temp onto the next node in the list
		}
		return count;
	}	
	

	@Override
	public E removeFirst() {
		if(isEmpty()){
			return null;
		} else {
			Node<E> removed = head;
			head = head.getNext();
			return removed.getElement();
		}
	}

	@Override
	public E removeLast() {
		if(isEmpty()){
			return null;
		} else {
			Node<E> temp = head;
			for (int i = 0; i < size() - 1; i++) {
				temp = temp.getNext(); //Iterating
			}

			tail = temp;
			tail.setNext(null);
			return tail.getElement();
		}
	}

	@Override
	public void addFirst(E e) {
		Node<E> newNode = new Node<>(e);
		newNode.setNext(head);
		head = newNode;
		if (size() == 1) {
			tail = head;
		}
	}


	@Override
	public void addLast(E e) {
		Node<E> newest = new Node<>(e);
		if(isEmpty()){
			tail = newest;
			head = tail;
		} else {
			/*tail.setNext(newest);
			tail = newest;*/
			Node<E> last = head;
			//int size = size();
			if (last == null) {
				head = newest;
			} else {
				while (last.getNext() != null) {
					last = last.getNext();
				}
				last.setNext(newest);
				tail = newest;
			}
		}
	}

	public E first(){
		if(isEmpty()){
			return null;
		}
		return head.getElement();
	}

	public E last(){
		if(isEmpty()) {
			return null;
		}
		return tail.getElement();
	}

	@Override
	public String toString(){
		Node<E> node = head;
		String list = "[" + node.getElement();
		for(int i = 1; i < size(); i++){
			node = node.getNext();
			list += ", " + node.getElement();
		}
		return list + "]";
	}

	//Week 2 Practical Work
	public void reverse() {
		ArrayStack<Node<E>> stack = new ArrayStack<>();

		Node<E> curr = head;
		while(curr.getNext() != null){
			stack.push(curr);
			curr = curr.getNext();
		}

		head = curr;
		while(!stack.isEmpty()){
			curr.setNext(stack.top());
			curr = curr.getNext();
			stack.pop();
		}
		curr.setNext(null);
		//return head;
	}

	//Week 4 Practical Work - recursive
	public Node<E> reverse(Node<E> head){
		if(head == null || head.getNext() == null){
			if(head != null){
				System.out.println(head.getElement());
			}
			return head;
		} else {
			Node<E> headNode = reverse(head.getNext());

			head.next.setNext(head);
			head.setNext(null);

			System.out.println(head.getElement());
			return headNode;
		}
	}
	
	public static void main(String[] args) {
		String[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".split("");

		SinglyLinkedList<String> sll = new SinglyLinkedList<>();
		for (String s : alphabet) {
			sll.addFirst(s);
			sll.addLast(s);
		}

		System.out.println(sll.toString());

		sll.removeFirst();
		System.out.println(sll.toString());

		sll.removeLast();
		System.out.println(sll.toString());

		sll.remove(2);
		System.out.println(sll.toString());

		for (String s : sll) {
			System.out.print(s + ", ");
		}
		System.out.println();

		SinglyLinkedList<String> no = new SinglyLinkedList<>();
		System.out.println(no.isEmpty());
		no.add(0, "Hi");
		System.out.println(no.isEmpty());

		no.add(0, "Hello");
		no.add(2, "There");

		//System.out.println(no.getHead());
		System.out.println(no.get(1));

		//System.out.println(no.getTail());
		System.out.println(no.toString());
		no.remove(1);


		no.addFirst("Oh");
		no.addLast("Bye");
		no.add(2, "Good");
		System.out.println(no.toString());

		no.reverse();
		System.out.println(no.toString());

		no.reverse(no.head);
	}
}
