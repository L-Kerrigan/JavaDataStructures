import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class CircularlyLinkedListTest {

    @Test
    void testSize() {
        CircularlyLinkedList<Integer> cl = new CircularlyLinkedList<>();
        assertEquals(0, cl.size());
        cl.addFirst(0);
        assertEquals(1, cl.size());
    }

    @Test
    void testIsEmpty() {
        CircularlyLinkedList<Integer> cl = new CircularlyLinkedList<>();
        assertEquals(true, cl.isEmpty());
        cl.addFirst(0);
        assertEquals(false, cl.isEmpty());
        cl.removeFirst();
        assertEquals(true, cl.isEmpty());
    }

    @Test
    void testFirst() {
        CircularlyLinkedList<Integer> cl = new CircularlyLinkedList<>();
        cl.addFirst(-1);
        assertEquals(-1, cl.first());

        cl.removeFirst();
        assertEquals(null, cl.first());


    }

    @Test
    void testLast() {
        CircularlyLinkedList<Integer> cl = new CircularlyLinkedList<>();
        cl.addFirst(-1);

        assertEquals(-1, cl.last());

        cl.addFirst(-2);
        assertEquals(-1, cl.last());

        cl.addLast(-3);
        assertEquals(-3, cl.last());
    }


    @Test
    void testRemoveLast() {
        CircularlyLinkedList<Integer> cl = new CircularlyLinkedList<>();
        cl.addFirst(-1);
        cl.addFirst(-2);
        assertEquals(-1, cl.removeLast());
    }

    @Test
    void testGet() {
        CircularlyLinkedList<Integer> cl = new CircularlyLinkedList<>();
        for(int i = 0; i < 5; ++i) cl.addLast(i);

        assertEquals(1, cl.get(1));
    }

    @Test
    void testRemove() {
        CircularlyLinkedList<Integer> cl = new CircularlyLinkedList<>();
        for(int i = 0; i < 5; ++i) cl.addLast(i);

        cl.remove(1);
        assertEquals("[0, 2, 3, 4]", cl.toString());
    }

    @Test
    void testAdd() {
        CircularlyLinkedList<Integer> cl = new CircularlyLinkedList<>();
        for(int i = 0; i < 5; ++i) cl.addLast(i);

        cl.add(2, -1);
        assertEquals("[0, 1, -1, 2, 3, 4]", cl.toString());
    }

    @Test
    void testToString() {
        CircularlyLinkedList<Integer> cl = new CircularlyLinkedList<>();
        for(int i = 0; i < 5; ++i) cl.addLast(i);

        assertEquals("[0, 1, 2, 3, 4]", cl.toString());
    }

    @Test
    void testIterator() {
        CircularlyLinkedList<Integer> cl = new CircularlyLinkedList<>();
        for(int i = 0; i < 5; ++i) cl.addLast(i);

        ArrayList<Integer> buf = new ArrayList<>();
        for(Integer i : cl) {
            buf.add(i);
        }
        assertEquals("[0, 1, 2, 3, 4]", buf.toString());
    }

}