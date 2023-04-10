/* A circular array class */
public class ArrayDeque<T> {
    private int defaultSize = 8;
    /* How much to resize the array when full */
    private int defaultFactor = 2;

    /* Size of first and second half of the array */
    private int size = 0;

    private T[] items;
    /* The first element starts at the left most
     * The last element starts at the right most
     */
    private int nextFirst;
    private int nextLast;


    public ArrayDeque() {
        items = (T[]) new Object[defaultSize];
        nextFirst = 0;
        nextLast = defaultSize - 1;
        size = 0;
    }


    /* Returns the index of the first item in the array */
    private int currentFirst() {
        int currentFirst = nextFirst - 1;
        if (currentFirst < 0) {
            currentFirst = items.length - 1;
        }
        if (items[currentFirst] == null) {
            currentFirst = nextLast + size;
        }
        return currentFirst;
    }

    /* Returns the index of the last item in the array */
    private int currentLast() {
        int currentLast = nextLast + 1;
        if (currentLast >= items.length) {
            currentLast = 0;
        }
        if (items[currentLast] == null) {
            currentLast = nextFirst - size;
        }
        return currentLast;
    }

    /* Moves the nextFirst index by i
     *  If i is positive, it means that an element is added
     *  If i is negative, it means that an element is removed
     *  If i is zero, it means that we just want to convert unbound index to bound index*/
    private void changeNextFirst(int i) {
        nextFirst += i;
        if (nextFirst < 0) {
            nextFirst = items.length - 1;
        }
        else if (nextFirst >= items.length) {
            nextFirst = 0;
        }
    }

    /* Moves the nextLast index by i
     *  If i is positive, it means that an element is removed
     *  If i is negative, it means that an element is added
     *  If i is zero, it means that we just want to convert unbound index to bound index*/
    private void changeNextLast(int i) {
        nextLast += i;
        if (nextLast < 0) {
            nextLast = items.length - 1;
        } else if (nextLast >= items.length) {
            nextLast = 0;
        }

    }

    /**
     * Creates a new array with larger size with all the previous elements in it
     * The array is expanded at the middle
     */
    private void resize(float factor) {
        int newSize = Math.round(size() * factor);
        T[] newItems = (T[]) new Object[newSize];

        System.arraycopy(items, 0, newItems, 0, size);
        nextLast = newItems.length - 1;
        nextFirst = size;
        items = newItems;
    }

    /* Adds an item to the right of the previous first item */
    public void addFirst(T item) {
        if (size() >= items.length) {
            resize(defaultFactor);
        }

        items[nextFirst] = item;

        changeNextFirst(1);
        size += 1;
    }

    /* Adds an item to the left of the previous last item */
    public void addLast(T item) {
        if (size() >= items.length) {
            resize(defaultFactor);
        }
        items[nextLast] = item;

        changeNextLast(-1);
        size += 1;
    }

    /* Checks if the array is empty */
    public boolean isEmpty() {
        return size == 0;
    }

    /* Returns the size of the array */
    public int size() {
        return size;
    }

    /* Prints all the elements in the array */
    public void printDeque() {
        for (int i = 0; i < size(); i++) {
            System.out.print(get(i) + " ");
        }
        System.out.println();
    }

    /* Removes the first element from the array
     * Changes the nextFirst index
     * Resizes the array if the array usage is less than 25% */
    public T removeFirst() {
        if (size() <= 0) {
            return null;
        }
        T item = items[currentFirst()];
        items[currentFirst()] = null;
        changeNextFirst(-1);
        size -= 1;

        // If the array usage is small, we resize the array to save space
        if (size() > 8 && size() < items.length * 0.25) {
            resize(0.25F);
        }

        return item;
    }

    /* Removes the last element from the array
     * Resizes the array if the usage is less than 25% */
    public T removeLast() {
        if (size() <= 0) {
            return null;
        }
        T item = items[currentLast()];
        items[currentLast()] = null;
        changeNextLast(1);
        size -= 1;

        // If the array usage is small, we resize the array to save space
        if (size() > 8 && size() < items.length * 0.25) {
            resize(0.25F);
        }

        return item;
    }

    /* Given an index in terms of normal array, return the item */
    public T get(int index) {
        if (index >= size) {
            return null;
        } else {
            int adIndex = currentFirst() - index;
            if (adIndex < 0) {
                adIndex += size;
            }
            return items[adIndex];
        }
    }
}
