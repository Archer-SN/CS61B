/* A circular array class */
public class ArrayDeque<T> {
    private int defaultSize = 8;
    /* How much to resize the array when full */
    private int defaultFactor = 2;

    /* Size of first and second half of the array */
    private int firstSize = 0;
    private int lastSize = 0;

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

        firstSize = 0;
        lastSize = 0;
    }


    /* Returns the index of the first item in the array */
    private int currentFirst() {
        if (firstSize == 0) {
            return nextLast + lastSize;
        }
        return nextFirst - 1;
    }

    /* Returns the index of the last item in the array */
    private int currentLast() {
        if (lastSize == 0) {
            return nextFirst - firstSize;
        }
        return nextLast + 1;
    }

    /* Decreases firstSize when called
     * Handles the case where the next index is unbound */
    private void decreaseFirstSize() {
        if (firstSize > 0) {
            firstSize -= 1;
            nextFirst -= 1;
        } else {
            lastSize -= 1;
            nextFirst = currentFirst() + 1;
        }
    }

    /* Decreases lastSize when called
     *  Handles the case where the next index is unbound */
    private void decreaseLastSize() {
        if (lastSize > 0) {
            lastSize -= 1;
            nextLast += 1;
        } else {
            firstSize -= 1;
            nextLast = currentLast() - 1;
        }
    }

    /* Increase firstSize when called and also changes the nextFirst index */
    private void increaseFirstSize() {
        if (nextFirst >= items.length) {
            nextFirst = 0;
        } else {
            nextFirst += 1;
        }
        firstSize += 1;
    }

    /* Increase lastSize when called and also changes the nextLast index */
    private void increaseLastSize() {
        if (nextLast < 0) {
            nextLast = items.length - 1;
        } else {
            nextLast -= 1;
        }
        lastSize += 1;
    }

    /* Gets all the items in the first half of the array */
    private T[] getFirstItems() {
        int fSize = firstSize;
        int index = currentFirst();
        T[] firstItems = (T[]) new Object[firstSize];
        for (int i = firstSize - 1; i >= 0; i--) {
            if (index < 0) {
                index = items.length - 1;
            }
            firstItems[i] = items[index];
            index -= 1;
        }
        return firstItems;
    }

    /* Gets all the items in the last half of the array */
    private T[] getLastItems() {
        int lSize = lastSize;
        int index = currentLast();
        T[] lastItems = (T[]) new Object[lastSize];
        for (int i = lastSize - 1; i >= 0; i--) {
            if (index >= items.length) {
                index = 0;
            }
            lastItems[i] = items[index];
            index += 1;
        }
        return lastItems;
    }

    /**
     * Creates a new array with larger size with all the previous elements in it
     * The array is expanded at the middle
     */
    private void resize(float factor) {
        int newSize = Math.round(size() * factor);
        T[] newItems = (T[]) new Object[newSize];
        T[] firstItems = getFirstItems();
        T[] lastItems = getLastItems();

        System.arraycopy(firstItems, 0, newItems, 0, firstSize);
        System.arraycopy(lastItems, 0, newItems, newSize - lastSize, lastSize);
        nextLast = newSize - lastSize - 1;
        items = newItems;
    }

    /* Adds an item to the right of the previous first item */
    public void addFirst(T item) {
        if (size() >= items.length) {
            resize(defaultFactor);
        }

        items[nextFirst] = item;

        increaseFirstSize();
    }

    /* Adds an item to the left of the previous last item */
    public void addLast(T item) {
        if (size() >= items.length) {
            resize(defaultFactor);
        }
        items[nextLast] = item;

        increaseLastSize();
    }

    /* Checks if the array is empty */
    public boolean isEmpty() {
        return size() == 0;
    }

    /* Returns the size of the array */
    public int size() {
        return firstSize + lastSize;
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
        decreaseFirstSize();

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
        decreaseLastSize();

        // If the array usage is small, we resize the array to save space
        if (size() > 8 && size() < items.length * 0.25) {
            resize(0.25F);
        }

        return item;
    }

    /* Converts normal array index to ArrayDeque index
     *  ArrayDeque is a circular array so its index is ordered differently
     */
    private int toArrayDequeIndex(int index) {
        if (index >= firstSize) {
            // The AD index is equal to
            return items.length - 1 - (index - firstSize);
        } else {
            return currentFirst() - index;
        }
    }

    /* Given an index in terms of normal array, return the item */
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
