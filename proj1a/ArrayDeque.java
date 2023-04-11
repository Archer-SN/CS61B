/* A circular array class */
public class ArrayDeque<T> {
    int defaultSize = 8;
    int defaultFactor = 2;

    T[] items;
    int size;
    int nextFirst;
    int nextLast;

    private int currentFirst() {

    }

    private int currentLast() {

    }

    /* Creates an empty array with default size */
    public ArrayDeque() {
        items = (T[]) new Object[defaultSize];
        size = 0;
        nextFirst = 0;
        nextLast = 1;
    }

    private void resize(int factor) {

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
        }
        else if (nextFirst == items.length) {
            nextFirst = 0;
        }
    }

    /* Moves nextLast index by i */
    private void moveNextLast(int i) {
        nextLast += i;
        if (nextLast == -1) {
            nextLast = minusOne(items.length);
        }
        else if (nextLast == items.length) {
            nextLast = 0;
        }
    }


    /* Adds an item to the front of the array deque
    *  (Not the front of items) */
    public void addFirst(T item) {
        items[nextFirst] = item;
        nextFirst -= 1;

    }

    /* Adds an item to the last of the array deque
     *  (Not the last of items) */
    public void addLast(T item) {

    }

    boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void printDeque() {

    }

    public T removeFirst() {

    }

    public T removeLast() {

    }

    public T get(int index) {
        return items[minusOne(nextFirst) + index]
    }
}
