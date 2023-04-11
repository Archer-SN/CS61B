import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Array;

public class ArrayDequeTest {

    @Test
    public void getTest() {
        int expected = 1; // Expected return value
        ArrayDeque<Integer> ad = new ArrayDeque<Integer>();
        ad.addFirst(1);
        ad.addFirst(2);
        ad.addFirst(3);
        ad.addLast(4);
        int actual = ad.get(2);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void addFirstTest() {

    }

    @Test
    public void addLastTest() {


    }

    @Test
    public void isEmptyTest() {

    }

    @Test
    public void sizeTest() {
    }

    @Test
    public void printDequeTest() {

    }

    @Test
    public void removeFirstTest() {

    }

    @Test
    public void removeLastTest() {

    }

    @Test
    public void resizeTest() {

    }
}
