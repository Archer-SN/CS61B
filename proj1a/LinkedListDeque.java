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
    public LinkedListDeque(T item) {
        size = 1;
        sentinel = new ItemNode();
        sentinel.next = new ItemNode(item, sentinel, sentinel);
    }

    public LinkedListDeque(LinkedListDeque other) {
        size = other.size;
        ItemNode p = other.sentinel;
        while (p.next != other.sentinel) {
            p = p.next;
            addLast(p.item);
        }
    }

    public void addFirst(T item) {
        size += 1;
        sentinel.next = new ItemNode(item, sentinel, sentinel.next);
    }

    public void addLast(T item) {
        size += 1;
        sentinel.prev = new ItemNode(item, sentinel.prev, sentinel);
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
            return firstItem;
        }
        return null;
    }

    /** Removes and returns the last item on the Linked List*/
    public T removeLast() {
        if (size > 0) {
            size -= 1;
            T lastItem = sentinel.prev.item;
            sentinel.prev = sentinel.prev.prev;
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

    public T getRecursiveHelper(int index, ItemNode node) {
        if (index == 0) {
            return node.item;
        }
        return getRecursiveHelper(index - 1, node.next);
    }
    public T getRecursive(int index) {
        if (index >= size) {
            return null;
        }
        return getRecursiveHelper(index, sentinel.next);
    }

}
