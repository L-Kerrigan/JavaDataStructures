import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class DoublyLinkedList<E> implements List<E> {
    private Node<E> header = null; //The head node of the list
    private Node<E> trailer = null; //The tail node of the list

    private class Node<E> {
        private E element; //Declaring the element of the node
        private Node<E> prev; //Pointer to the previous node
        private Node<E> next; //Pointer to the next node

        public Node(E e, Node<E> p) { //Constructor
            element = e; //Initialising element
            prev = p; //Initialising prev(ious)
            next = null; //Initialising next
        }

        public E getElement() { //Getter for element
            return element;
        }

       /* public void setElement(E element) {
            this.element = element;
        }*/

        public Node<E> getPrev() { //Getter for previous node
            return prev;
        }

        public void setPrev(Node<E> prev) {
            this.prev = prev;
        }

        public Node<E> getNext() { //Getter for next node
            return next;
        }

        public void setNext(Node<E> next) {
            this.next = next;
        }
    }

    private void addBetween(E e, Node<E> predecessor, Node<E> successor) {
        Node<E> newNode = new Node<>(e, predecessor);

        predecessor.setNext(newNode);
        newNode.setNext(predecessor.getNext());
        predecessor = newNode;
    }

    @Override
    public int size() {
        Node<E> temp = header; //The temporary node, temp, is assigned the same values as the head
        int count = 0;
        while (temp != null) { //While temp isn't null, we continue counting
            count++;
            temp = temp.getNext(); //After counting, move temp onto the next node in the list
        }
        return count;
    }

    @Override
    public boolean isEmpty() {
        //If the size is 0, the list must be empty
        return size() == 0;
    }

    @Override
    public E get(int i) {
        if (i == 0) {
            return header.getElement();
        } else {
            Node<E> temp = header;
            for (int j = 0; j < i; j++) {
                temp.setPrev(temp);
                temp = temp.getNext();
            }
            return temp.getElement();
        }
    }

    @Override
    public void add(int i, E e) {
        if(i < 0 || i > size()){
            throw new IndexOutOfBoundsException();
        } else if (i == 0) { //If the index is 1, we can just call the add first method
            addFirst(e);
        } else if (i == size()) {
            addLast(e);
        } else {
            Node<E> temp = header;
            Node<E> newNode = new Node<>(e, null);
            for (int j = 1; j < i; j++) {
                temp = temp.getNext();
            }

            newNode.setNext(temp.getNext());
            temp.setNext(newNode);
            newNode.setPrev(temp);
            newNode.getNext().setPrev(newNode);

            //addBetween(e, temp.getPrev(), temp.getNext());
			/*
			temp = temp.getPrev();
			newNode.next = temp.getNext();
			temp.next = newNode;

			 */
        }
    }

    /*
    public E remove(int i) {
        SinglyLinkedList.Node<E> predecessor = null;
        SinglyLinkedList.Node<E> sucessor = header;
        for (int j =0; j<i; j++){
            predecessor.setNext(sucessor);
            sucessor.setPrev(predecessor);
            size--;
        }
        return null;
    } */


    @Override //Issues here. Removing between numbers doesn't work
    public E remove(int i) {
        if (isEmpty() || i > size()) {
            throw new RuntimeException("Can't delete anything if nothing is there!");
        } else if (i == 0) {
            removeFirst();
        } else if (i == size()) {
            removeLast();
        }
        Node<E> temp = header;
        for (int j = 0; j < i; j++) {
            temp = temp.getNext();
        }
        if(temp.getPrev() != null){
            temp.prev.setNext(temp.getNext());
        }
        if(null != temp.getNext()){
            temp.next.setPrev(temp.getPrev());
        }
        //temp = temp.getNext();
        return temp.getElement();


		/*if(header == null){
			throw new RuntimeException("Can't delete anything if nothing is there!");
		} else if (i == 0) {
			removeFirst();
		} else if(i == size()){
			removeLast();
		}

		Node<E> temp = header;
		for (int j = 1; j < i-1 && temp.getNext() != null; j++) {
			temp = temp.getNext();
		}

		if(temp == null){
			throw new RuntimeException("Can't delete anything if nothing is there!");
		}
		//if(temp.getNext() != null){
            temp.setPrev(temp.getNext());
       // }

		*//*if(temp.getNext() == null) {
            temp.prev.next = null;
        } else{*//*
		    temp.setNext(temp.getNext().getNext());
       // }*/
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
                return DoublyLinkedList.this.get(index++);
            }
        }
        /*Node<E> curr;

        public ListIterator() {
            curr = header;
        }

        @Override
        public boolean hasNext() {
            return curr != null && curr.getNext() != null;
        }

        @Override
        public E next() {
            E elem = curr.getElement();
            curr = curr.getNext();
            return elem;
        }*/
        //}
            /*
            @Override
            public boolean hasPrevious() {
                return false;
            }

            @Override
            public E previous() {
                return null;
            }

            @Override
            public int nextIndex() {
                return 0;
            }

            @Override
            public int previousIndex() {
                return 0;
            }

            @Override
            public void remove() {

            }

            @Override
            public void set(E e) {

            }

            @Override
            public void add(E e) {

            }

             */
    }


    @Override
    public E removeFirst() {
        if (header == null) {
            return null;
        } else if(header.getNext() == null) {
            trailer = null;
        } else {
            header.getNext().setPrev(null);
        }
        E temp = header.getElement();
        header = header.getNext();
        return temp;
    }

    @Override
    public E removeLast() {
        E temp;
        if(trailer == null){
            return null;
        } else if(header.getNext() == null){
            temp = header.getElement();
            header = null;
        } else{
            temp = trailer.getElement();
            trailer.getPrev().setNext(null);
        }
        /*Node<E> temp = header;
        for (int i = 2; i < size(); i++) {
            temp.prev = temp;
            temp = temp.getNext(); //Iterating
        }
        trailer.prev = temp.getPrev();
        trailer = temp;
        trailer.next = null;*/

        trailer = trailer.getPrev();
        return temp;
    }


    @Override
    public void addFirst(E e) {
        Node<E> newNode = new Node<>(e, null);
        if (isEmpty()) {
            newNode.setNext(null);
            trailer = newNode;
        } else {
            header.setPrev(newNode);
            newNode.setNext(header);
        }
        //newNode.setNext(header);
        header = newNode;
    }

    @Override
    public void addLast(E e) {
        Node<E> last = header;
        if (isEmpty()) {
            header = new Node<>(e, null);
        } else {
           while (last.getNext() != null) {
                last = last.getNext();
            }
            //newNode.prev = last;
            //last.next = newNode;
            Node<E> newNode = new Node<>(e, null);
            last.setNext(newNode);
            newNode.setPrev(last);
            trailer = newNode;
            //trailer.setPrev(last);
        }
    }

    /*
	@Override
	public String toString(){
		Node<E> node = header;
		String list = (String) node.getElement();
		for(int i = 1; i < size(); i++){
			node = node.getNext();
			list += ", " + node.getElement();
		}
		return list;
	}

     */

    public E first(){
        if(isEmpty()){
            return null;
        }
        return header.getElement();
    }

    public E last(){
        if(isEmpty()) {
            return null;
        }
        return trailer.getElement();
    }

    @Override
    public String toString() {
        Node<E> node = header;
        ArrayList<E> list = new ArrayList<>();
        list.add(node.getElement());
        for (int i = 1; i < size(); i++) {
            node = node.getNext();
            list.add(node.getElement());
        }
        return list.toString();
    }

    public static void main(String[] args) {
        DoublyLinkedList<Integer> ll = new DoublyLinkedList<>();
        ll.addFirst(0);
        ll.addFirst(1);
        ll.addFirst(2);
        ll.addLast(-1);
        System.out.println(ll);

        ll.removeFirst();
        System.out.println(ll);

        ll.removeLast();
        System.out.println(ll);

        System.out.println(ll.size());
        ll.add(1, 9);
        System.out.println(ll);
        System.out.println(ll.size());
        System.out.println();

        ll.remove(1);
        System.out.println(ll);
        System.out.println(ll.size());

        for (Integer e : ll) {
            System.out.println("value: " + e);
        }
    }
}
