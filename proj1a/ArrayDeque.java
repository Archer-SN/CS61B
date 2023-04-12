/* A circular array class */
public class ArrayDeque<T> {
    int defaultSize = 8;
    int defaultFactor = 2;

    T[] items;
    int size;
    int nextFirst;
    int nextLast;

    /* Creates an empty array with default size */
    public ArrayDeque() {
        items = (T[]) new Object[defaultSize];
        size = 0;
        nextFirst = 0;
        nextLast = 1;
    }

    /* Returns array deque sorted by array index from first to last*/
    private T[] sortArrayDeque(T[] ad) {
        T[] sortedAD = (T[]) new Object[ad.length];
        for (int i = 0; i < ad.length; i++) {
            sortedAD[i] = get(i);
        }
        return sortedAD;
    }

    /* Resizes the array by a given factor with the order of elements in Java's array changed */
    private void resize(double factor) {
        int newSize = (int) Math.ceil(items.length * factor);
        T[] newItems = (T[]) new Object[newSize];
        T[] sortedItems = sortArrayDeque(items);
        System.arraycopy(sortedItems, 0, newItems, 0, size);
        items = newItems;
        nextLast = size;
        nextFirst = minusOne(newSize);
    }

    /* Returns a number subtracted by 1 */
    private int minusOne(int number) {
        return number - 1;
    }

    /* Returns a number added by one */
    private int addOne(int number) {
        return number + 1;
    }

    /* Moves nextFirst index by i */
    private void moveNextFirst(int i) {
        nextFirst += i;
        if (nextFirst == -1) {
            nextFirst = minusOne(items.length);
        } else if (nextFirst == items.length) {
            nextFirst = 0;
        }
    }

    /* Moves nextLast index by i */
    private void moveNextLast(int i) {
        nextLast += i;
        if (nextLast == -1) {
            nextLast = minusOne(items.length);
        } else if (nextLast == items.length) {
            nextLast = 0;
        }
    }


    /* Adds an item to the front of the array deque
     *  (Not the front of items) */
    public void addFirst(T item) {
        if (size == items.length) {
            resize(defaultFactor);
        }
        items[nextFirst] = item;
        moveNextFirst(-1);
        size += 1;
    }

    /* Adds an item to the last of the array deque
     *  (Not the last of items) */
    public void addLast(T item) {
        if (size == items.length) {
            resize(defaultFactor);
        }
        items[nextLast] = item;
        moveNextLast(1);
        size += 1;
    }

    boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        for (int i = 0; i < size; i++) {
            System.out.print(get(i) + " ");
        }
        System.out.println();
    }

    /* Removes the first item from array deque */
    public T removeFirst() {
        T firstItem = get(0);
        items[getIndex(0)] = null;
        size -= 1;
        moveNextFirst(1);

        /* Resizes the array if the length of items is 16 or more
         * And the usage is less than 25% */
        if ((size >= 16) && (size < (0.25 * items.length))) {
            resize(0.25);
        }

        return firstItem;
    }

    /* Removes the last item from array deque */
    public T removeLast() {
        T lastItem = get(minusOne(size));
        items[getIndex(size - 1)] = null;
        size -= 1;
        moveNextLast(-1);

        /* Resizes the array if the length of items is 16 or more
         * And the usage is less than 25% */
        if ((items.length >= 16) && (size < (0.25 * items.length))) {
            resize(0.25);
        }

        return lastItem;
    }

    /* Given Java array index, returns the true index */
    private int getIndex(int index) {
        int realIndex = addOne(nextFirst) + index;
        if (realIndex >= items.length) {
            realIndex -= items.length;
        }
        return realIndex;
    }

    /* Given Java array index returns the item on that index
     * (Java array index is different from array deque index, so we have to convert it) */
    public T get(int index) {
        int realIndex = getIndex(index);
        return items[realIndex];
    }
}
