
/* A circular array class */
public class ArrayDeque<T> {
    private int defaultSize = 8;
    private int defaultFactor = 2;

    /* Size of first and second half of the array */
    private int firstSize = 0;
    private int lastSize = 0;

    private T[] items;
    private int nextFirst;
    private int nextLast;

    /** Creates an array with specified size */
    /*public ArrayDeque(int arraySize) {
        items = (T[]) new Object[arraySize];
        nextFirst = 0;
        nextLast = arraySize - 1;
    }*/

    /** Creates an array with default size */
    public ArrayDeque() {
        items = (T[]) new Object[defaultSize];
        nextFirst = 0;
        nextLast = defaultSize - 1;

        firstSize = 0;
        lastSize = 0;
    }

    /** Create a new array with all the elements in the other array */
    /*
    public ArrayDeque(ArrayDeque other) {
        items = (T[]) new Object[other.size()];
        System.arraycopy(other, 0, items, 0, other.size());
        nextFirst = other.nextFirst;
        nextLast = other.nextLast;
    }
    */

    private int currentFirst() {
        return nextFirst - 1;
    }

    private int currentLast() {
        return nextLast + 1;
    }

    /** Creates a new array with larger size with all the previous elements in it
     *  The array is expanded at the middle
     */
    private void resize(float factor) {
        int newSize = Math.round(size() * factor);
        T[] newItems = (T[]) new Object[newSize];

        /* Copies the first half the array
           The first half doesn't move so no index change
         */
        System.arraycopy(items, 0, newItems, 0, firstSize);
        /* Copies the last half of the array
           The last half is moved thus the index changes
         */
        System.arraycopy(items, currentLast(), newItems, newSize - lastSize - 2, lastSize);
        nextLast = newSize - lastSize - 2;
        items = newItems;
    }

    public void addFirst(T item) {
        if (size() >= items.length) {
            resize(defaultFactor);
        }

        items[nextFirst] = item;

        firstSize += 1;
        nextFirst += 1;
    }

    public void addLast(T item) {
        if (size() >= items.length) {
            resize(defaultFactor);
        }
        items[nextLast] = item;

        lastSize += 1;
        nextLast -= 1;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return firstSize + lastSize;
    }

    public void printDeque() {
        for (int i = 0; i < size(); i++) {
            System.out.print(i + " ");
        }
        System.out.println();
    }

    public T removeFirst() {
        if (firstSize == 0) {
            return null;
        }
        T item = items[currentFirst()];
        items[currentFirst()] = null;
        firstSize -= 1;
        nextFirst -= 1;

        // If the array usage is small, we resize the array to save space
        if (size() < items.length * 0.25) {
            resize(0.25F);
        }

        return item;
    }

    public T removeLast() {
        if (lastSize == 0) {
            return null;
        }
        T item = items[currentLast()];
        items[currentLast()] = null;
        nextLast += 1;
        lastSize -= 1;

        // If the array usage is small, we resize the array to save space
        if (size() < items.length * 0.25) {
            resize(0.25F);
        }

        return item;
    }

    private int toArrayDequeIndex(int index) {
        if (index >= firstSize) {
            // The AD index is equal to
            return items.length - 1 - (index - firstSize - 1);
        }
        else {
            return currentFirst() - index;
        }
    }

    public T get(int index) {
        int adIndex = toArrayDequeIndex(index);
        if (index >= items.length) {
            return null;
        } else if (index >= firstSize) {
            return items[adIndex];
        } else {
            // Our first element is the righter most element
            return items[adIndex];
        }
    }
}
