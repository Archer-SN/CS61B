package bstmap;

import org.w3c.dom.Node;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/* This data structure is Map implemented using Binary Search Tree
 * Each key-value pair is stored as a node in the Binary Search Tree
 *
 * */
public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    /**
     * A node in BST
     */
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

    private BSTMap<K, V> left;
    private BSTMap<K, V> right;

    // A constructor for BSTMap
    public BSTMap() {
        size = 0;
        node = null;
        left = null;
        right = null;
    }

    /**
     * Prints all the keys and values in tree in order
     */
    private void printInOrder(BSTMap<K, V> T) {
        if (T == null || T.node == null) {
            return;
        }
        printInOrder(T.left);
        System.out.println(T.node.key + " " + T.node.value);
        printInOrder(T.right);
    }

    public void printInOrder() {
        printInOrder(this);
    }

    /**
     * Removes all the nodes from the tree
     */
    @Override
    public void clear() {
        size = 0;
        node = null;

        left = null;
        right = null;
    }

    /**
     * Searches recursively through the tree to see whether it contains the specified key
     */
    private boolean containsKey(K key, BSTMap<K, V> T) {
        // Base case
        if (T == null || T.node == null) {
            return false;
        }
        int comparison = key.compareTo(T.node.key);
        /* If key == node.key */
        if (comparison == 0) {
            return true;
        }
        /* If key > node.key */
        else if (comparison > 0) {
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
    private BSTMap<K, V> get(K key, BSTMap<K, V> T) {
        // Base case
        if (T == null || T.node == null) {
            return null;
        }

        int comparison = key.compareTo(T.node.key);

        // If key == node.key
        if (comparison == 0) {
            return T;
        }
        // if key < node.key
        else if (comparison < 0) {
            return get(key, T.left);
        }
        // If key > node.key
        else {
            return get(key, T.right);
        }
    }

    /**
     * Given a key returns a value associated with that key
     */
    @Override
    public V get(K key) {
        BSTMap<K, V> T = get(key, this);
        if (T == null) {
            return null;
        }
        return T.node.value;
    }

    @Override
    public int size() {
        return size;
    }


    /**
     * Inserts a key-value pair as a node into the tree in sorted order
     */
    private BSTMap<K, V> put(K key, V value, BSTMap<K, V> T) {
        if (T == null) {
            T = new BSTMap<K, V>();
            T.node = new BSTNode(key, value);
            return T;
        } else if (T.node == null) {
            T.node = new BSTNode(key, value);
        }
        int comparison = key.compareTo(T.node.key);
        // If key == node.key
        if (comparison == 0) {
            T.node = new BSTNode(key, value);
        }
        // if key < node.key
        else if (comparison < 0) {
            T.left = put(key, value, T.left);
        }
        // If key > node.key
        else {
            T.right = put(key, value, T.right);
        }
        return T;
    }

    /**
     * Inserts a key-value pair into Map
     */
    @Override
    public void put(K key, V value) {
        put(key, value, this);
        size += 1;
    }


    /**
     * Gets all the keys in a tree recursively
     * We make use of mutability property of Set
     */
    private Set<K> keySet(Set<K> S, BSTMap<K, V> T) {
        if (T == null) {
            return S;
        }
        keySet(S, T.left);
        S.add(T.node.key);
        keySet(S, T.right);
        return S;
    }

    /**
     * Returns a set view of keys stored in the BSTMap
     */
    @Override
    public Set<K> keySet() {
        return keySet(new TreeSet<K>(), this);
    }


    /**
     * Given a key returns the key's parent tree
     * The existence of the key is assumed to exist
     * If currentT is the root node, currentT is returned
     */
    private BSTMap<K, V> getParentNode(BSTMap<K, V> currentT, BSTMap<K, V> parentT, K key) {
        int comparison = key.compareTo(currentT.node.key);
        // If the parent node is found
        if (comparison == 0) {
            return parentT;
        }
        // Moves to the left of the tree
        else if (comparison < 0) {
            return getParentNode(currentT.left, currentT, key);
        }
        // Moves to the right of the trees
        else {
            return getParentNode(currentT.right, currentT, key);
        }
    }

    private BSTMap<K, V> getRightMostNode(BSTMap<K, V> T) {
        if (T.right == null) {
            return T;
        }
        return getRightMostNode(T.right);
    }

    private void changeNodeValue(BSTNode node, K key, V value) {
        node.key = key;
        node.value = value;
    }

    /**
     * Remove the mapping for the specified key if it is present
     */
    @Override
    public V remove(K key) {
        BSTMap<K, V> T = get(key, this);

        if (T == null) {
            return null;
        }

        BSTMap<K, V> parentT = getParentNode(this, this, key);
        V value = T.node.value;

        // Case 1: The node has no children
        if (T.left == null && T.right == null) {
            // If T is the root node
            if (parentT == T) {
                T.node = null;
            }
            // Removing T from existence
            else if (parentT.left == T) {
                parentT.left = null;
            }
            // Removing T from existence
            else {
                parentT.right = null;
            }
        }
        // Case 2: The node has one child
        else if (T.left == null || T.right == null) {
            BSTMap<K, V> leaf;
            // Stores the leaf value before we remove the current node
            if (T.left == null) {
                leaf = T.right;
            } else {
                leaf = T.left;
            }

            // If T is a root node
            if (parentT == T) {
                changeNodeValue(T.node, leaf.node.key, leaf.node.value);
                T.left = leaf.left;
                T.right = leaf.right;
            }
            // Relink the parent node with leaf
            else if (parentT.right == T) {
                parentT.right = leaf;
            } else {
                parentT.left = leaf;
            }
        }
        // Case 3: The node has 2 children
        else {
            BSTMap<K, V> newT = getRightMostNode(T.left);
            BSTMap<K, V> newTParent = getParentNode(this, this, newT.node.key);

            // Setting the root node T to be newT
            T.node.value = newT.node.value;
            T.node.key = newT.node.key;

            // Linking the newTParent to newT's right node
            // There is no right child because this is the righter most node
            // This will remove newT's existence.
            newTParent.left = newT.left;
        }
        size -= 1;
        return value;
    }

    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException("remove not supported!");
    }


    /**
     * Returns an iterator for BSTMap
     * Implemented using set iterator
     */
    @Override
    public Iterator<K> iterator() {
        return keySet().iterator();
    }
}
