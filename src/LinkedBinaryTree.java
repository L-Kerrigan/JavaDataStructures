/**
 * Concrete implementation of a binary tree using a node-based, linked structure.
 */
public class LinkedBinaryTree<E> extends AbstractBinaryTree<E> {

    /** Nested static class for a binary tree node. */
    protected static class Node<E> implements Position<E> {
        private E element;
        private Node<E> left, right, parent;

        public Node(E e, Node<E> above, Node<E> leftChild, Node<E> rightChild){
            element = e;
            left = leftChild;
            right = rightChild;
            parent = above;
        }

        @Override
        public E getElement() /*throws IllegalStateException*/ {
            return element;
        }

        public void setElement(E element) {
            this.element = element;
        }

        public Node<E> getLeft() {
            return left;
        }

        public void setLeft(Node<E> left) {
            this.left = left;
        }

        public Node<E> getRight() {
            return right;
        }

        public void setRight(Node<E> right) {
            this.right = right;
        }

        public Node<E> getParent() {
            return parent;
        }

        public void setParent(Node<E> parent) {
            this.parent = parent;
        }

        @Override
        public String toString(){
            return getElement().toString();
        }
    }

    /** Factory function to create a new node storing element e. */
    protected Node<E> createNode(E e, Node<E> parent,
                                 Node<E> left, Node<E> right) {
        return new Node<E>(e, parent, left, right);
    }

    // LinkedBinaryTree instance variables
    /** The root of the binary tree */
    protected Node<E> root = null;     // root of the tree

    /** The number of nodes in the binary tree */
    private int size = 0;              // number of nodes in the tree

    // constructor
    /** Constructs an empty binary tree. */
    public LinkedBinaryTree() { }      // constructs an empty binary tree

    public LinkedBinaryTree(Node<E> node){
        root = node;
    }
    // nonpublic utility
    /**
     * Verifies that a Position belongs to the appropriate class, and is
     * not one that has been previously removed. Note that our current
     * implementation does not actually verify that the position belongs
     * to this particular list instance.
     *
     * @param p   a Position (that should belong to this tree)
     * @return    the underlying Node instance for the position
     * @throws IllegalArgumentException if an invalid position is detected
     */
    protected Node<E> validate(Position<E> p) throws IllegalArgumentException {
        if (!(p instanceof Node)) {
            throw new IllegalArgumentException("Not valid position type.");
        }
        Node<E> node = (Node<E>) p;       // safe cast
        if (node.getParent() == node) {     // our convention for defunct node
            throw new IllegalArgumentException("p is no longer in the tree.");
        }
        return node;
    }

    // accessor methods (not already implemented in AbstractBinaryTree)
    /**
     * Returns the number of nodes in the tree.
     * @return number of nodes in the tree
     */
    @Override
    public int size() {
        return size;
    }

    private final DefaultComparator<E> comp = new DefaultComparator<>();

    public int compareTo(E first, E second){
        return comp.compare(first, second);
    }

    @Override
    public boolean isEmpty(){
        return size() == 0;
    }

    /**
     * Returns the root Position of the tree (or null if tree is empty).
     * @return root Position of the tree (or null if tree is empty)
     */
    @Override
    public Position<E> root() {
        return root;
    }

    /**
     * Returns the Position of p's parent (or null if p is root).
     *
     * @param p    A valid Position within the tree
     * @return Position of p's parent (or null if p is root)
     * @throws IllegalArgumentException if p is not a valid Position for this tree.
     */
    @Override
    public Position<E> parent(Position<E> p) throws IllegalArgumentException {
        Node<E> node = validate(p);
        return node.getParent();
    }

    /**
     * Returns the Position of p's left child (or null if no child exists).
     *
     * @param p A valid Position within the tree
     * @return the Position of the left child (or null if no child exists)
     * @throws IllegalArgumentException if p is not a valid Position for this tree
     */
    @Override
    public Position<E> left(Position<E> p) throws IllegalArgumentException {
        Node<E> node = validate(p);
        return node.getLeft();
    }

    /**
     * Returns the Position of p's right child (or null if no child exists).
     *
     * @param p A valid Position within the tree
     * @return the Position of the right child (or null if no child exists)
     * @throws IllegalArgumentException if p is not a valid Position for this tree
     */
    @Override
    public Position<E> right(Position<E> p) throws IllegalArgumentException {
        Node<E> node = validate(p);
        return node.getRight();
    }

    // update methods supported by this class
    /**
     * Places element e at the root of an empty tree and returns its new Position.
     *
     * @param e   the new element
     * @return the Position of the new element
     * @throws IllegalStateException if the tree is not empty
     */
    public Position<E> addRoot(E e) throws IllegalStateException {
        if(!isEmpty()){
            throw new IllegalStateException("Tree isn't empty. A root already exists.");
        }
        root = createNode(e,null,null,null);
        size = 1;
        return root;
    }

    public void insert(E e){
        //recursively add from root
        if(e != null) {
            root = addRecursive(root, e);
            ++size;
        }
    }

    //recursively add Nodes to binary tree in proper position
    private Node<E> addRecursive(Node<E> p, E e){
        if(p != null) {
            if(e.equals(p.getElement())){
                return p;
            }
            else if (compareTo(e, p.getElement()) < 0) {
                if (p.getLeft() != null) {
                    p.setLeft(addRecursive(p.getLeft(), e));
                } else {
                    p.setLeft(createNode(e, p, null, null));
                }
                //return (Node<E>) addLeft(p, e);
            } else if(compareTo(e, p.getElement()) > 0) {
                if (p.getRight() != null) {
                    p.setRight(addRecursive(p.getRight(), e));
                } else {
                    p.setRight(createNode(e, p, null, null));
                }
            } else {
                return p;
            }
        } else {
            return createNode(e, p, null, null);
        }
        return p;
    }


    /**
     * Creates a new left child of Position p storing element e and returns its Position.
     *
     * @param p   the Position to the left of which the new element is inserted
     * @param e   the new element
     * @return the Position of the new element
     * @throws IllegalArgumentException if p is not a valid Position for this tree
     * @throws IllegalArgumentException if p already has a left child
     */
    public Position<E> addLeft(Position<E> p, E e)  throws IllegalArgumentException {
        Node<E> parent = validate(p);
        if (parent.getLeft() != null) {
            throw new IllegalArgumentException("p already has a left child.");
        }
        Node<E> child = createNode(e, parent, null, null);
        parent.setLeft(child);
        size++;
        return child;
    }

    /**
     * Creates a new right child of Position p storing element e and returns its Position.
     *
     * @param p   the Position to the right of which the new element is inserted
     * @param e   the new element
     * @return the Position of the new element
     * @throws IllegalArgumentException if p is not a valid Position for this tree.
     * @throws IllegalArgumentException if p already has a right child
     */
    public Position<E> addRight(Position<E> p, E e) throws IllegalArgumentException {
        Node<E> parent = validate(p);
        if (parent.getRight() != null) {
            throw new IllegalArgumentException("p already has a right child.");
        }
        Node<E> child = createNode(e, parent, null, null);
        parent.setRight(child);
        size++;
        return child;
    }

    /**
     * Replaces the element at Position p with element e and returns the replaced element.
     *
     * @param p   the relevant Position
     * @param e   the new element
     * @return the replaced element
     * @throws IllegalArgumentException if p is not a valid Position for this tree.
     */
    public E set(Position<E> p, E e) throws IllegalArgumentException {
        Node<E> node = validate(p);
        E temp = node.getElement();
        node.setElement(e);
        return temp;
    }

    @Override
    public int height(Position<E> pos){
        int h = 0;
        for(Position<E> p : children(pos)){
            h = Math.max(h, 1 + height(p));
        }
        return h;
    }

    /**
     * Attaches trees t1 and t2, respectively, as the left and right subtree of the
     * leaf Position p. As a side effect, t1 and t2 are set to empty trees.
     *
     * @param p   a leaf of the tree
     * @param t1  an independent tree whose structure becomes the left child of p
     * @param t2  an independent tree whose structure becomes the right child of p
     * @throws IllegalArgumentException if p is not a valid Position for this tree
     * @throws IllegalArgumentException if p is not a leaf
     */
    public void attach(Position<E> p, LinkedBinaryTree<E> t1, LinkedBinaryTree<E> t2) throws IllegalArgumentException {
        Node<E> node = validate(p);
        if(isInternal(p)){
            throw new IllegalArgumentException("p must be a leaf.");
        }
        size += t1.size() + t2.size();

        if(!t1.isEmpty()){
            t1.root.setParent(node);
            node.setLeft(t1.root);
            t1.root = null;
            t1.size = 0;
        }

        if(!t2.isEmpty()){
            t2.root.setParent(node);
            node.setLeft(t2.root);
            t2.root = null;
            t2.size = 0;
        }
    }

    public void createLevelOrder(E[] array){
        root = createLevelOrderHelper(array, root, 0);
    }

    private Node<E> createLevelOrderHelper(E[] array, Node<E> parent, int i) {
        if(i < array.length){
            Node<E> newNode = createNode(array[i], parent, null, null);
            newNode.setLeft(createLevelOrderHelper(array, newNode.getLeft(), 2*i+1));
            newNode.setRight(createLevelOrderHelper(array, newNode.getRight(), 2*i+2));
            size++;
            return newNode;
        }
        return parent;
    }

    /**
     * Removes the node at Position p and replaces it with its child, if any.
     *
     * @param p   the relevant Position
     * @return element that was removed
     * @throws IllegalArgumentException if p is not a valid Position for this tree.
     * @throws IllegalArgumentException if p has two children.
     */
    public E remove(Position<E> p) throws IllegalArgumentException {
        Node<E> node = validate(p);
        if(numChildren(p) == 2){
            throw new IllegalArgumentException("p has 2 children.");
        }
        Node<E> child = (node.getLeft() != null ? node.getLeft() : node.getRight());
        if(child != null){
            child.setParent(node.getParent());
        }
        if(node == root){
            root = child;
        } else {
            Node<E> parent = node.getParent();
            if (node == parent.getLeft()) {
                parent.setLeft(child);
            } else {
                parent.setRight(child);
            }
        }
        size--;

        E temp = node.getElement();
        node.setElement(null);
        node.setLeft(null);
        node.setRight(null);
        node.setParent(node);
        return temp;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for(Position<E> pos : inorder()) {
            sb.append(pos.getElement());
            sb.append(", ");
        }
        sb.delete(sb.length()-2, sb.length());
        sb.append("]");
        return sb.toString();
    }

    public void mirror(){
        root = mirror(root);
    }

    public Node<E> mirror(Node<E> node){
        if(node == null){
            return null;
        }

        Node<E> l = mirror(node.getLeft());
        Node<E> r = mirror(node.getRight());

        node.setLeft(r);
        node.setRight(l);

        return node;
    }


    public boolean isMirror(Node<E> node1, Node<E> node2){
        if(node1 == null && node2 == null){
            return true;
        }

        if(node1 != null && node2 != null){
            return (isMirror(node1.getLeft(), node2.getRight()) && isMirror(node1.getRight(), node2.getLeft()));
        }
        return false;
    }

    public boolean isSymmetric(Node<E> node){
        return isMirror(root, root);
    }

    public int distanceBetween(Node<E> node, E a, E b){
        if(node == null){
            return 0;
        }
        if((compareTo(node.getElement(), a) > 0) && (compareTo(node.getElement(), b) > 0)){
            return distanceBetween(node.getLeft(), a , b);
        }
        if((compareTo(node.getElement(), a) < 0) && (compareTo(node.getElement(), b) < 0)){
            return distanceBetween(node.getRight(), a, b);
        }
        if((compareTo(node.getElement(), a) >= 0) && (compareTo(node.getElement(), b) <= 0)){
            return distanceFromRoot(node, a) + distanceFromRoot(node, b);
        }
        return 0;
    }

    public int distanceFromRoot(Node<E> node, E e){
        if(node.getElement() == e){
            return 0;
        } else if(compareTo(node.getElement(), e) > 0){
            return 1 + distanceFromRoot(node.getLeft(), e);
        }
        return 1 + distanceFromRoot(node.getRight(), e);
    }

    public int findDistanceWrapper(Node<E> node, E a, E b){
        E temp;
        if(compareTo(a, b) > 0){
            temp = a;
            a = b;
            b = temp;
        }
        return distanceBetween(node, a, b);
    }

    public static void main(String [] args) {
        LinkedBinaryTree<Integer> bt = new LinkedBinaryTree<>();

        /*int [] arr = {4,2,5,1,6,3,7};
        for(int i : arr) {
            bt.insert(i);
        }*/

        System.out.println("bt: " + bt + "\nIs symmetric: " + bt.isSymmetric(bt.root));

        bt.addRoot(1);
        bt.root.setLeft(bt.createNode(2, bt.root, null, null));
        bt.root.setRight(bt.createNode(3, bt.root, null, null));
        bt.root.getLeft().setLeft(bt.createNode(4, bt.root.getLeft(), null, null));
        bt.root.getLeft().setRight(bt.createNode(5, bt.root.getLeft(), null, null));
        bt.root.getRight().setLeft(bt.createNode(6, bt.root.getRight(), null, null));
        bt.root.getRight().setRight(bt.createNode(7, bt.root.getRight(), null, null));
        System.out.println("bt: " + bt + "\nIs symmetric: " + bt.isSymmetric(bt.root));

        bt.mirror();
        System.out.println("bt: " + bt + "\nIs symmetric: " + bt.isSymmetric(bt.root));

        LinkedBinaryTree<Integer> lbt = new LinkedBinaryTree<>();

        /*int [] arr = {12, 25, 31, 58, 36, 42, 90, 62, 75};
        for(int i : arr) {
            lbt.insert(i);
        }*/
        int [] arr = {12, 25, 31, 58, 36, 75};
        for(int i : arr) {
            lbt.insert(i);
        }
        System.out.println("lbt size: " + lbt.size() + ", lbt: " + lbt);

        System.out.println();
        System.out.println("Distance from 25 to 75: " + bt.findDistanceWrapper(bt.root, 25, 75));
        System.out.println();
    }
}