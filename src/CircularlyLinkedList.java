import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class CircularlyLinkedList<E> implements List<E> {
	private Node<E> tail = null;
	private int size = 0;

	public CircularlyLinkedList(){ }

	private static class Node<E> {
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
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public E get(int i) {
		Node<E> temp = tail.getNext();
		for(int j = 0; j < i; j++){
			temp = temp.getNext();
		}
		return temp.getElement();
	}

	public E first(){
		if(!isEmpty()) {
			return get(0);
		}
		return null;
	}

	public E last(){
		if(!isEmpty()) {
			return tail.getElement();
		}
		return null;
	}

	@Override
	public void add(int i, E e) {
		if(i == 0){
			addFirst(e);
		} else if(i == size){
			addLast(e);
		} else {
			Node<E> temp = tail.getNext();
			for(int j = 0; j < i-1; j++){
				temp = temp.getNext();
			}
			temp.setNext(new Node<>(e));
		}
		/*Node<E> addNode = new Node<>(e, tail.getNext());
		Node<E> curr = tail;

		for (int j = 0; j < i - 1; j++){
			curr = curr.getNext();
		}
		addNode.setNext(curr.getNext());
		curr.setNext(addNode);*/
		size ++;
	}

	@Override
	public E remove(int i) {
		/*Node<E> temp = tail.getNext();
		if(i == 0){
			removeFirst();
		} else if(i == size){
			removeLast();
		} else {
			for(int j = 0; j < i; j++){
				temp = temp.getNext();
			}
			//Remove node...
		}
		return temp.getElement();*/

		Node<E> temp = tail.getNext();
		Node<E> prev = new Node<>(null);
		prev.setNext(null);
		if (size == 0){
			return null;
		}else if(size == 1){
			tail = null;
		}else
			for (int j = 0; j < i; j++){
				prev = temp;
				temp = temp.getNext();
			}
		prev.setNext(temp.getNext());
		size--;
		return temp.getElement();
	}

	@Override
	public E removeFirst() {
		if(isEmpty()){
			return null;
		}

		Node<E> head = tail.getNext();
		if(head == tail){
			tail = null;
		} else {
			tail.setNext(head.getNext());
		}
		size--;
		return head.getElement();
	}

	@Override
	public E removeLast() {
		if(isEmpty()){
			return null;
		}
		Node<E> head = tail.getNext();
		Node<E> temp = head;
		if(size == 1){
			tail = null;
		} else if (temp.getNext() == tail) {
			temp.setNext(head);
			tail = temp;
		}
		size--;
		return temp.getElement();

		/*if(temp1 == tail){
			tail = null;
		} else {
			tail = temp1;
			tail.setNext(temp1.getNext());
		}
		size--;
		return temp1.getElement();*/
		/*Node<E> temp = head.getNext();
		while(temp.getNext() != null){
			temp = head;
			head = head.getNext();
		}*/
		/*temp.setNext(head);
		tail = temp;
		size--;
		return tail.getElement();*/
	}

	@Override
	public Iterator<E> iterator() {
		return new ListIterator();

	}

	private class ListIterator implements Iterator<E> {
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
				return CircularlyLinkedList.this.get(index++);
			}
		}

		//ListIterator(){
			/*current = tail.getNext();}

		@Override
		public boolean hasNext(){
			return current != tail;
		}

		@Override
		public E next(){
			E ans = current.getElement();
			current = current.getNext();
			return ans;
		}*/
	}

	@Override
	public void addFirst(E e) {
		if(isEmpty()){
			tail = new Node<>(e);
			tail.setNext(tail);
		} else {
			Node<E> newest = new Node<>(e);
			newest.setNext(tail.getNext());
			tail.setNext(newest);
		}
		size++;
	}

	@Override
	public void addLast(E e) {
		addFirst(e);
		tail = tail.getNext();
	}

	public void rotate() {
		if(tail != null){
			tail = tail.getNext();
		}
	}

	//Errors here
	@Override
	public String toString() {
		/*if(tail == null){
			return null;
		}*/
		/*Node<E> node = tail.getNext();
		ArrayList<E> list = new ArrayList<>();
		list.add(node.getElement());
		for (int i = 1; i < size; i++) {
			node = node.getNext();
			list.add(node.getElement());
		}
		return list.toString();*/

		/*String list = "[";
		Node<E> temp = tail.getNext();
		while (temp != tail && temp != null) {
			list += temp.getElement();
			if (temp.getNext() != null) {
				list += ", ";
			}
			temp = temp.getNext();
		}

		return list + "]";*/
		ArrayList<E> list = new ArrayList<>();
		for(int i = 0; i < size; i++){
			list.add(get(i));
		}

		return list.toString();
	}
	
	
	public static void main(String[] args) {
		CircularlyLinkedList<Integer> ll = new CircularlyLinkedList<>();
		for(int i = 10; i < 20; ++i) {
			ll.addLast(i);
		}

		System.out.println(ll.toString());

		ll.removeFirst();
		System.out.println(ll.toString());

		ll.removeLast();

		ll.rotate();
		System.out.println(ll.toString());

		ll.removeFirst();
		ll.rotate();
		System.out.println(ll.toString());

		ll.removeLast();
		ll.rotate();
		System.out.println(ll.toString());

		for (Integer e : ll) {
			System.out.println("value: " + e);
		}
	}
}
