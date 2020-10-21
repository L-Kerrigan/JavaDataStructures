import java.util.ArrayList;

/*
 * Map implementation using hash table with separate chaining.
 */

public class ChainHashMap<K, V> extends AbstractHashMap<K, V> {
    // a fixed capacity array of UnsortedTableMap that serve as buckets
    private UnsortedTableMap<K, V>[] table; // initialized within createTable

    /** Creates a hash table with capacity 11 and prime factor 109345121. */
    public ChainHashMap() {
        super();
    }

    /** Creates a hash table with given capacity and prime factor 109345121. */
    public ChainHashMap(int cap) {
        super(cap);
    }

    /** Creates a hash table with the given capacity and prime factor. */
    public ChainHashMap(int cap, int p) {
        super(cap, p);
    }

    /** Creates an empty table having length equal to current capacity. */
    @Override
    @SuppressWarnings({ "unchecked" })
    protected void createTable() {
        table = (UnsortedTableMap<K,V>[]) new UnsortedTableMap[capacity];
    }

    /**
     * Returns value associated with key k in bucket with hash value h. If no such
     * entry exists, returns null.
     *
     * @param h the hash value of the relevant bucket
     * @param k the key of interest
     * @return associate value (or null, if no such entry)
     */
    @Override
    protected V bucketGet(int h, K k) {
        UnsortedTableMap<K,V> bucket = table[h];
        if(bucket == null){
            return null;
        }
        return bucket.get(k);
    }

    /**
     * Associates key k with value v in bucket with hash value h, returning the
     * previously associated value, if any.
     *
     * @param h the hash value of the relevant bucket
     * @param k the key of interest
     * @param v the value to be associated
     * @return previous value associated with k (or null, if no such entry)
     */
    @Override
    protected V bucketPut(int h, K k, V v) {
        UnsortedTableMap<K,V> bucket = table[h];
        if(bucket == null) {
            bucket = table[h] = new UnsortedTableMap<>();
        }
        //Rechecking size as it might have increased
        int oldSize = bucket.size();
        V ans = bucket.put(k,v);
        n += (bucket.size() - oldSize);
        return ans;
    }

    /**
     * Removes entry having key k from bucket with hash value h, returning the
     * previously associated value, if found.
     *
     * @param h the hash value of the relevant bucket
     * @param k the key of interest
     * @return previous value associated with k (or null, if no such entry)
     */
    @Override
    protected V bucketRemove(int h, K k) {
        UnsortedTableMap<K,V> bucket = table[h];
        if(bucket == null) {
            return null;
        }
        //Rechecking size as it might have decreased
        int oldSize = bucket.size();
        V ans = bucket.remove(k);
        n -= (oldSize - bucket.size());
        return ans;
    }

    /**
     * Returns an iterable collection of all key-value entries of the map.
     *
     * @return iterable collection of the map's entries
     */
    @Override
    public Iterable<Entry<K, V>> entrySet() {
        ArrayList<Entry<K,V>> buffer = new ArrayList<>();
        for(int i = 0; i < capacity; i++){
            if(table[i] != null){ //Add all entries to the buffer
                for(Entry<K,V> entry : table[i].entrySet()){
                    buffer.add(entry);
                }
            }
        }
        return buffer;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for(Entry<K, V> me : entrySet()) {
            if(me.getValue()!= null) {
                sb.append(me.getValue()).append(", ");
            }
        }
        sb.delete(sb.length()-2, sb.length());
        sb.append("]");
        return sb.toString();
        //StringBuilder sb = new StringBuilder("[");
        //Iterable<Entry<K,V>> buffer = entrySet();
        //for(int i = 0; i < capacity; i++) {
            /*for (Entry<K, V> e : buffer) {
                sb.append(e.getKey()).append(" = ").append(e.getValue()).append(", ");
            }*/
       // }
        /*for(int i = 0; i < table.length; i++) {
            if(table[i] != null) {
                sb.append(table[i].keySet().toString()).append(", ");
            }
        }*/
        //sb.delete(sb.length()-2, sb.length()).append("]");
    }

    public static void main(String[] args) {
        //HashMap<Integer, String> m = new HashMap<Integer, String>();
        ChainHashMap<Integer, String> m = new ChainHashMap<>();
        m.put(1, "One");
        m.put(10, "Ten");
        m.put(11, "Eleven");
        m.put(20, "Twenty");

        System.out.println("m: " + m);

        m.remove(11);
        System.out.println("m: " + m);
    }
}