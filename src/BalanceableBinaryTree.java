public class BalanceableBinaryTree<K, V> extends LinkedBinaryTree<Entry<K, V>> {
    // -------------- nested BSTNode class --------------
    // this extends the inherited LinkedBinaryTree.Node class
    protected static class BSTNode<E> extends Node<E> {
        int aux = 0;

        BSTNode(E e, Node<E> parent, Node<E> leftChild, Node<E> rightChild) {
            super(e, parent, leftChild, rightChild);
        }

        public int getAux() {
            return aux;
        }

        public void setAux(int value) {
            aux = value;
        }
    } // --------- end of nested BSTNode class ---------

    // positional-based methods related to aux field
    public int getAux(Position<Entry<K, V>> p) {
        return ((BSTNode<Entry<K, V>>) p).getAux();
    }

    public void setAux(Position<Entry<K, V>> p, int value) {
        ((BSTNode<Entry<K, V>>) p).setAux(value);
    }

    // Override node factory function to produce a BSTNode (rather than a Node)
    @Override
    protected Node<Entry<K, V>> createNode(Entry<K, V> e, Node<Entry<K, V>> parent, Node<Entry<K, V>> left,
                                           Node<Entry<K, V>> right) {
        return new BSTNode<>(e, parent, left, right);
    }

    /** Relinks a parent node with its oriented child node. */
    private void relink(Node<Entry<K, V>> parent, Node<Entry<K, V>> child, boolean makeLeftChild) {
        child.setParent(parent);
        if(makeLeftChild){
            parent.setLeft(child);
        } else {
            parent.setRight(child);
        }
    }

    /**
     * Rotates Position p above its parent. Switches between these configurations,
     * depending on whether p is a or p is b.
     *
     * <pre>
     *          b                  a
     *         / \                / \
     *        a  t2             t0   b
     *       / \                    / \
     *      t0  t1                 t1  t2
     * </pre>
     *
     * Caller should ensure that p is not the root.
     */
    public void rotate(Position<Entry<K, V>> p) {
        Node<Entry<K, V>> a = validate(p);
        Node<Entry<K, V>> b = a.getParent(); //Retrieves the parent
        Node<Entry<K, V>> c = b.getParent(); //Retrieves the grandparent

        if(c == null){
            root = a; //Making a the root of the tree
            a.setParent(null);
        } else {
            relink(c, a, b == c.getLeft()); //a becomes a child directly beneath c

            if(a == b.getLeft()){
                //a's right child becomes b's left child
                relink(b, a.getRight(), true);
                //b becomes a's right child
                relink(a, b, false);
            } else {
                //a's left child becomes b's right child
                relink(b, a.getLeft(), false);
                //b becomes a's left child
                relink(a, b, true);
            }
        }
    }

    /**
     *
     * Returns the Position that becomes the root of the restructured subtree.
     *
     * Assumes the nodes are in one of the following configurations:
     *
     * <pre>
     *     z=a                 z=c           z=a               z=c
     *    /  \                /  \          /  \              /  \
     *   t0  y=b             y=b  t3       t0   y=c          y=a  t3
     *      /  \            /  \               /  \         /  \
     *     t1  x=c         x=a  t2            x=b  t3      t0   x=b
     *        /  \        /  \               /  \              /  \
     *       t2  t3      t0  t1             t1  t2            t1  t2
     * </pre>
     *
     * The subtree will be restructured so that the node with key b becomes its
     * root.
     *
     * <pre>
     *           b
     *         /   \
     *       a       c
     *      / \     / \
     *     t0  t1  t2  t3
     * </pre>
     *
     * Caller should ensure that x has a grandparent.
     */
    public Position<Entry<K, V>> restructure(Position<Entry<K, V>> x) {
        Position<Entry<K, V>> y = parent(x);
        Position<Entry<K, V>> z = parent(y);

        if((x == right(y)) == (y == right(z))){ //If they have matching alignments
            rotate(y); //Rotate y once
            return y;
        }
        //Otherwise, the alignments don't match, so rotate x twice
        rotate(x);
        rotate(x);
        return x; //The new root of the subtree
    }
}