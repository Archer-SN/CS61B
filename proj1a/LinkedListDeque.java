public class LinkedListDeque<T> {
    private class ItemNode {
        private T item;
        private ItemNode prev;
        private ItemNode next;

        public ItemNode(T itemValue, ItemNode pNode, ItemNode nNode) {
            item = itemValue;
            prev = pNode;
            next = nNode;
        }

        /** Used to create a sentinel node*/
        public ItemNode() {
            this.prev = this;
            this.next = this;
        }
    }

    private ItemNode sentinel;
    private int size;

    /** Creates an empty doubly linked list if no parameter is given*/
    public LinkedListDeque() {
        size = 0;
        sentinel = new ItemNode();
    }

    /** Creates a new doubly linked list with one node attached to the sentinel*/
    /* Gradescope somehow wants me to remove this
    public LinkedListDeque(T item) {
        size = 1;
        sentinel = new ItemNode();
        sentinel.next = new ItemNode(item, sentinel, sentinel);
        sentinel.prev = sentinel.next;
    }

    public LinkedListDeque(LinkedListDeque other) {
        size = other.size;
        ItemNode p = other.sentinel;
        while (p.next != other.sentinel) {
            p = p.next;
            addLast(p.item);
        }
    }
    */

    public void addFirst(T item) {
        size += 1;
        ItemNode newNode = new ItemNode(item, sentinel, sentinel.next);
        // Points the second node's previous node to the first node
        sentinel.next.prev = newNode;
        // Points to the new first node
        sentinel.next = newNode;
    }

    public void addLast(T item) {
        size += 1;
        ItemNode newNode = new ItemNode(item, sentinel.prev, sentinel);
        // Points the second last to the last node
        sentinel.prev.next = newNode;
        // Points sentinel's prev to the new last node
        sentinel.prev = newNode;
    }

    /** Checks if the linked list is empty
     * @return boolean
     * */
    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    /** Prints all them items in deque from first to last, separated by a space*/
    public void printDeque() {
        ItemNode p = sentinel;
        while (p.next != sentinel) {
            p = p.next;
            System.out.print(p.item + " ");
        }
        System.out.println();
    }

    /** Removes and returns the first item on the Linked List*/
    public T removeFirst() {
        if (size > 0) {
            size -= 1;
            T firstItem = sentinel.next.item;
            sentinel.next = sentinel.next.next;
            sentinel.next.prev = sentinel;
            return firstItem;
        }
        return null;
    }

    /** Removes and returns the last item on the Linked List*/
    public T removeLast() {
        if (size > 0) {
            size -= 1;
            T lastItem = sentinel.prev.item;
            // Pointing the arrow to the second last node
            sentinel.prev = sentinel.prev.prev;
            // Remove the pointer of the second node on the last node
            sentinel.prev.next = sentinel;
            return lastItem;
        }
        return null;
    }

    public T get(int index) {
        if (index + 1 > size) {
            return null;
        }

        ItemNode p = sentinel;
        while (index != 0) {
            index -= 1;
            p = p.next;
        }
        return p.item;
    }

    private T getRecursiveHelper(int index, ItemNode node) {
        if (index == 0) {
            return node.item;
        }
        return getRecursiveHelper(index - 1, node.next);

    }

    /* Given index, gets an item recursively
    */
    public T getRecursive(int index) {
        if (index >= size) {
            return null;
        }
        return getRecursiveHelper(index, sentinel.next);
    }

}
