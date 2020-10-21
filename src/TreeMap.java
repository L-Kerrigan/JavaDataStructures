import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * An implementation of a sorted map using a binary search tree.
 */

public class TreeMap<K, V> extends AbstractSortedMap<K, V> {

    // We reuse the BalanceableBinaryTree class. A limitation here is that we only use the key.
    protected BalanceableBinaryTree<K, V> tree = new BalanceableBinaryTree<K,V>();

    /** Constructs an empty map using the natural ordering of keys. */
    public TreeMap() {
        super(); // the AbstractSortedMap constructor
        tree.addRoot(null); // create a sentinel leaf as root
    }

    public TreeMap(Comparator<K> comp) {
        super(comp);              // the AbstractSortedMap constructor
        tree.addRoot(null);       // create a sentinel leaf as root
    }

    /**
     * Returns the number of entries in the map.
     *
     * @return number of entries in the map
     */
    @Override
    public int size() {
        return (tree.size() - 1) / 2; // only internal nodes have entries
    }

    /** Utility used when inserting a new entry at a leaf of the tree */
    private void expandExternal(Position<Entry<K, V>> p, Entry<K, V> entry) {
        tree.set(p, entry); //Storing entry at position
        tree.addLeft(p, null);
        tree.addRight(p, null);
    }

    // Some notational shorthands for brevity (yet not efficiency)
    protected Position<Entry<K, V>> root() {
        return tree.root();
    }

    protected Position<Entry<K, V>> parent(Position<Entry<K, V>> p) {
        return tree.parent(p);
    }

    protected Position<Entry<K, V>> left(Position<Entry<K, V>> p) {
        return tree.left(p);
    }

    protected Position<Entry<K, V>> right(Position<Entry<K, V>> p) {
        return tree.right(p);
    }

    protected Position<Entry<K, V>> sibling(Position<Entry<K, V>> p) {
        return tree.sibling(p);
    }

    protected boolean isRoot(Position<Entry<K, V>> p) {
        return tree.isRoot(p);
    }

    protected boolean isExternal(Position<Entry<K, V>> p) {
        return tree.isExternal(p);
    }

    protected boolean isInternal(Position<Entry<K, V>> p) {
        return tree.isInternal(p);
    }

    protected void set(Position<Entry<K, V>> p, Entry<K, V> e) {
        tree.set(p, e);
    }

    protected Entry<K, V> remove(Position<Entry<K, V>> p) {
        return tree.remove(p);
    }

    /**
     * Returns the position in p's subtree having the given key (or else the
     * terminal leaf).
     *
     * @param key a target key
     * @param p   a position of the tree serving as root of a subtree
     * @return Position holding key, or last node reached during search
     */
    Position<Entry<K, V>> treeSearch(Position<Entry<K, V>> p, K key) {
        if(isExternal(p)){
            return p;
        }
        if(compare(key, p.getElement()) == 0){
            return p;
        } else if(compare(key, p.getElement()) < 0){
            return treeSearch(left(p), key);
        } else {
            return treeSearch(right(p), key);
        }
    }

    /**
     * Returns position with the minimal key in the subtree rooted at Position p.
     *
     * @param p a Position of the tree serving as root of a subtree
     * @return Position with minimal key in subtree
     */
    protected Position<Entry<K, V>> treeMin(Position<Entry<K, V>> p) {
        Position<Entry<K, V>> current = p;
        while(isInternal(current)){
            current = left(current);
        }
        return parent(current);
    }

    /**
     * Returns the position with the maximum key in the subtree rooted at p.
     *
     * @param p a Position of the tree serving as root of a subtree
     * @return Position with maximum key in subtree
     */
    protected Position<Entry<K, V>> treeMax(Position<Entry<K, V>> p) {
        Position<Entry<K, V>> current = p;
        while(isInternal(current)){
            current = right(current);
        }
        return parent(current);
    }

    /**
     * Returns the value associated with the specified key, or null if no such entry
     * exists.
     *
     * @param key the key whose associated value is to be returned
     * @return the associated value, or null if no such entry exists
     */
    @Override
    public V get(K key) throws IllegalArgumentException {
        checkKey(key);
        Position<Entry<K, V>> pos = treeSearch(root(), key);
        rebalanceAccess(pos); //Hook for balanced tree subclasses
        if(isInternal(pos)){ //Match found
            return pos.getElement().getValue();
        } //Match not found
        return null;
    }

    /**
     * Associates the given value with the given key. If an entry with the key was
     * already in the map, this replaced the previous value with the new one and
     * returns the old value. Otherwise, a new entry is added and null is returned.
     *
     * @param key   key with which the specified value is to be associated
     * @param value value to be associated with the specified key
     * @return the previous value associated with the key (or null, if no such
     *         entry)
     */
    @Override
    public V put(K key, V value) throws IllegalArgumentException {
        checkKey(key);
        Entry<K, V> newEntry = new MapEntry<>(key, value);
        Position<Entry<K, V>> pos = treeSearch(root(), key);
        if(isExternal(pos)){
            //Add new key
            expandExternal(pos, newEntry);
            rebalanceInsert(pos); //Hook for balanced tree subclasses
            return null;
        } else { //Replace existing key
            V ret = pos.getElement().getValue();
            set(pos, newEntry);
            rebalanceAccess(pos); //Hook for balanced tree subclasses
            return ret; //Return value that was previously at position pos
        }
    }

    /**
     * Removes the entry with the specified key, if present, and returns its
     * associated value. Otherwise does nothing and returns null.
     *
     * @param key the key whose entry is to be removed from the map
     * @return the previous value associated with the removed key, or null if no
     *         such entry exists
     */
    @Override
    public V remove(K key) throws IllegalArgumentException {
        checkKey(key);
        Position<Entry<K, V>> pos = treeSearch(root(), key);
        if(isExternal(pos)){
            rebalanceAccess(pos);
            return null;
        } else {
            V ret = pos.getElement().getValue();
            if(isInternal(left(pos)) && isInternal(right(pos))){
                Position<Entry<K, V>> replace = treeMax(left(pos));
                set(pos, replace.getElement());
                pos = replace;
            }
            Position<Entry<K, V>> leaf = (isExternal(left(pos)) ? left(pos) : right(pos));
            Position<Entry<K, V>> sibling = sibling(leaf);
            remove(leaf);
            remove(pos);
            rebalanceDelete(sibling);
            return ret;
        }
    }

    // additional behaviors of the SortedMap interface
    /**
     * Returns the entry having the least key (or null if map is empty).
     *
     * @return entry with least key (or null if map is empty)
     */
    @Override
    public Entry<K, V> firstEntry() {
        if (isEmpty())
            return null;
        return treeMin(root()).getElement();
    }

    /**
     * Returns the entry having the greatest key (or null if map is empty).
     *
     * @return entry with greatest key (or null if map is empty)
     */
    @Override
    public Entry<K, V> lastEntry() {
        if (isEmpty()) {
            return null;
        }
        return treeMax(root()).getElement();
    }

    /**
     * Returns the entry with least key greater than or equal to given key (or null
     * if no such key exists).
     *
     * @return entry with least key greater than or equal to given (or null if no
     *         such entry)
     * @throws IllegalArgumentException if the key is not compatible with the map
     */
    @Override
    public Entry<K, V> ceilingEntry(K key) throws IllegalArgumentException {
        checkKey(key);
        Position<Entry<K, V>> pos = treeSearch(root(), key);
        if (isInternal(pos)) {
            return pos.getElement(); //Found perfect match
        }
        while (!isRoot(pos)){
            if(pos == left(parent(pos))){
                return parent(pos).getElement(); //Parent is the next biggest key that exists
            } else {
                pos = parent(pos);
            }
        }
        return null; //No ceiling
    }

    /**
     * Returns the entry with greatest key less than or equal to given key (or null
     * if no such key exists).
     *
     * @return entry with greatest key less than or equal to given (or null if no
     *         such entry)
     * @throws IllegalArgumentException if the key is not compatible with the map
     */
    @Override
    public Entry<K, V> floorEntry(K key) throws IllegalArgumentException {
        checkKey(key);
        Position<Entry<K, V>> pos = treeSearch(root(), key);
        if (isInternal(pos)) {
            return pos.getElement(); //Found perfect match
        }
        while (!isRoot(pos)){
            if(pos == right(parent(pos))){
                return parent(pos).getElement(); //Parent is the next smallest entry that exists
            } else {
                pos = parent(pos);
            }
        }
        return null; //No floor entry
    }

    /**
     * Returns the entry with greatest key strictly less than given key (or null if
     * no such key exists).
     *
     * @return entry with greatest key strictly less than given (or null if no such
     *         entry)
     * @throws IllegalArgumentException if the key is not compatible with the map
     */
    @Override
    public Entry<K, V> lowerEntry(K key) throws IllegalArgumentException {
        checkKey(key);
        Position<Entry<K, V>> pos = treeSearch(root(), key);
        if (isInternal(pos) && isInternal(left(pos))) {
            return treeMax(left(pos)).getElement(); //The predecessor of p is returned
        }
        while (!isRoot(pos)){
            if(pos == right(parent(pos))){
                return parent(pos).getElement(); //Parent is the next smaller entry that exists
            } else {
                pos = parent(pos);
            }
        }
        return null; //No smaller entry
    }

    /**
     * Returns the entry with least key strictly greater than given key (or null if
     * no such key exists).
     *
     * @return entry with least key strictly greater than given (or null if no such
     *         entry)
     * @throws IllegalArgumentException if the key is not compatible with the map
     */
    @Override
    public Entry<K, V> higherEntry(K key) throws IllegalArgumentException {
        checkKey(key);
        Position<Entry<K, V>> pos = treeSearch(root(), key);
        if (isInternal(pos) && isInternal(right(pos))) {
            return treeMin(left(pos)).getElement(); //The successor of p is returned
        }
        while (!isRoot(pos)){
            if(pos == left(parent(pos))){
                return parent(pos).getElement(); //Parent is the next bigger entry that exists
            } else {
                pos = parent(pos);
            }
        }
        return null; //No bigger entry
    }

    // Support for iteration
    /**
     * Returns an iterable collection of all key-value entries of the map.
     *
     * @return iterable collection of the map's entries
     */
    @Override
    public Iterable<Entry<K, V>> entrySet() {
        ArrayList<Entry<K, V>> buffer = new ArrayList<>(size());
        for(Position<Entry<K, V>> pos : tree.inorder()){
            if(isInternal(pos)){
                buffer.add(pos.getElement());
            }
        }
        return buffer;
    }

    /**
     * Returns an iterable containing all entries with keys in the range from
     * <code>fromKey</code> inclusive to <code>toKey</code> exclusive.
     *
     * @return iterable with keys in desired range
     * @throws IllegalArgumentException if <code>fromKey</code> or
     *                                  <code>toKey</code> is not compatible with
     *                                  the map
     */
    @Override
    public Iterable<Entry<K, V>> subMap(K fromKey, K toKey) throws IllegalArgumentException {
        checkKey(fromKey);
        checkKey(toKey);
        ArrayList<Entry<K,V>> buffer = new ArrayList<>(size());
        if(compare(fromKey, toKey) < 0){
            subMapRecurse(fromKey, toKey, root(), buffer);
        }
        return buffer;
    }

    private void subMapRecurse(K fromKey, K toKey, Position<Entry<K, V>> pos, ArrayList<Entry<K, V>> buffer){
        if(isInternal(pos)){
            if(compare(pos.getElement(), fromKey) < 0){ //If this is true, all relevant entries are on the right
                subMapRecurse(fromKey, toKey, right(pos), buffer);
            } else {
                subMapRecurse(fromKey, toKey, left(pos), buffer);
                if(compare(pos.getElement(), toKey) < 0){ //If p is in range, add it to the buffer
                    buffer.add(pos.getElement());

                    subMapRecurse(fromKey, toKey, right(pos), buffer);
                }
            }
        }
    }

    @Override
    public Iterable<K> keySet(){
        ArrayList<K> buffer = new ArrayList<>(size());
        for(Position<Entry<K, V>> pos : tree.inorder()){
            if(isInternal(pos)){
                buffer.add(pos.getElement().getKey());
            }
        }
        return buffer;
    }

    // remainder of class is for debug purposes only
    /** Prints textual representation of tree structure (for debug purpose only). */
    protected void dump() {
        dumpRecurse(root(), 0);
    }

    /** This exists for debugging only */
    private void dumpRecurse(Position<Entry<K, V>> p, int depth) {
        String indent = (depth == 0 ? "" : String.format("%" + (2 * depth) + "s", ""));
        if (isExternal(p))
            System.out.println(indent + "leaf");
        else {
            System.out.println(indent + p.getElement());
            dumpRecurse(left(p), depth + 1);
            dumpRecurse(right(p), depth + 1);
        }
    }

    @Override
    public String toString() {
        /*StringBuilder sb = new StringBuilder();
        sb.append("[");
        for(Position<Entry<K, V>> pos : tree.positions()) {
            if(pos.getElement()!= null) {
                sb.append(pos.getElement()).append(", ");
            }
        }
        sb.delete(sb.length()-2, sb.length());
        sb.append("]");
        return sb.toString();*/
        return tree.toString();
    }

    public static void main(String[] args) {
        TreeMap<Integer, Integer> treeMap = new TreeMap<Integer, Integer>();

        Random rnd = new Random();
        int n = 16;
        java.util.List<Integer> rands = rnd.ints(1, 1000).limit(n).distinct().boxed().collect(Collectors.toList());

        for(Integer i : rands) {
            treeMap.put(i, i);
        }

        System.out.println("tree entries: " + treeMap.entrySet());

        treeMap.remove(rands.get(1));

        System.out.println("tree entries after removal: " + treeMap.entrySet());
    }

    /** Overrides the TreeMap rebalancing hook that is called after an insertion. */
    protected void rebalanceInsert(Position<Entry<K, V>> p) {
        // TODO Auto-generated method stub

    }

    /** Overrides the TreeMap rebalancing hook that is called after a deletion. */
    protected void rebalanceDelete(Position<Entry<K, V>> p) {
        // TODO Auto-generated method stub

    }

    /** Overrides the TreeMap rebalancing hook that is called after a node access. */
    protected void rebalanceAccess(Position<Entry<K, V>> p) {
        // TODO Auto-generated method stub

    }

    private void reLink(LinkedBinaryTree.Node<Entry<K, V>> parent, LinkedBinaryTree.Node<Entry<K, V>> child, boolean makeLeftChild){
        child.setParent(parent);
        if(makeLeftChild){
            parent.setLeft(child);
        } else {
            parent.setRight(child);
        }
    }

    protected void rotate(Position<Entry<K, V>> p) {
        LinkedBinaryTree.Node<Entry<K, V>> a = tree.validate(p);
        LinkedBinaryTree.Node<Entry<K, V>> b = a.getParent(); //Retrieves the parent
        LinkedBinaryTree.Node<Entry<K, V>> c = b.getParent(); //Retrieves the grandparent

        if(c == null){
            tree.root = a; //Making a the root of the tree
            a.setParent(null);
        } else {
            reLink(c, a, b == c.getLeft()); //a becomes a child directly beneath c

            if(a == b.getLeft()){
                //a's right child becomes b's left child
                reLink(b, a.getRight(), true);
                //b becomes a's right child
                reLink(a, b, false);
            } else {
                //a's left child becomes b's right child
                reLink(b, a.getLeft(), false);
                //b becomes a's left child
                reLink(a, b, true);
            }
        }
    }

}