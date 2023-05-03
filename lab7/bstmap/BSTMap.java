package bstmap;

import java.util.Iterator;
import java.util.Set;

/* This data structure is Map implemented using Binary Search Tree
 * Each key-value pair is stored as a node in the Binary Search Tree
 *
 * */
public class BSTMap<K extends Comparable, V> implements Map61B<K, V> {
    private class BSTNode {
        public K key;
        public V value;

        public BSTNode() {
            this.key = null;
            this.value = null;
        }

        public BSTNode(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }


    private int size;
    private BSTNode node;

    private BSTMap left;
    private BSTMap right;

    public BSTMap() {
        size = 0;
        node = new BSTNode();
    }

    public BSTMap(K key, V value) {
        size = 1;
        node = new BSTNode(key, value);
    }

    /**
     * Removes all the nodes from the tree
     */
    @Override
    public void clear() {
        size = 0;
        node = new BSTNode();

        left = null;
        right = null;
    }

    /**
     * Searches recursively through the tree to see whether it contains the specified key
     */
    private boolean containsKey(K key, BSTMap T) {
        // Base case
        if (T == null) {
            return false;
        }
        int comparison = key.compareTo(node.key);
        /* If key == node.key */
        if (comparison == 0) {
            return true;
        }
        /* If key > node.key */
        else if (comparison == 1) {
            return containsKey(key, T.right);
        }
        /* If key < node.key */
        else {
            return containsKey(key, T.left);
        }
    }

    @Override
    public boolean containsKey(K key) {
        return containsKey(key, this);
    }

    /**
     * Recursively find the value that is associated with the given key
     */
    private V get(K key, BSTMap T) {
        // Base case
        if (T == null) {
            return null;
        }

        int comparison = key.compareTo(T.node.key);

        // If key == node.key
        if (comparison == 0) {
            return T.node.value;
        }
        // if key < node.key
        else if (comparison == -1) {
            return get(key, left);
        }
        // If key > node.key
        else {
            return get(key, right);
        }
    }

    /**
     * Given a key returns a value associated with that key
     */
    @Override
    public V get(K key) {
        return get(key, this);
    }

    @Override
    public int size() {
        return size;
    }

    // TODO
    private BSTMap put(K key, V value, BSTMap T) {
        if (T == null) {
            return new BSTMap(key, value);
        }
        int comparison = key.compareTo(T.node.key);
    }

    @Override
    public void put(K key, V value) {
        put(key, value, this);
    }

    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException("keySet not supported!");
    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException("remove not supported!");
    }

    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException("remove not supported!");
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException("iterator not supported!");
    }
}
