import org.junit.Assert;
import org.junit.Test;

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
        int expected = 4;
        ArrayDeque<Integer> ad = new ArrayDeque<Integer>();
        for (int i = 1; i <= 8; i++) {
            ad.addFirst(i);
        }
        int actual = ad.get(4);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void addLastTest() {
        int expected = 4;
        ArrayDeque<Integer> ad = new ArrayDeque<Integer>();
        for (int i = 1; i <= 8; i++) {
            ad.addLast(i);
        }
        int actual = ad.get(3);
        Assert.assertEquals(expected, actual);

    }

    @Test
    public void isEmptyTest() {
        ArrayDeque<Integer> ad = new ArrayDeque<Integer>();
        boolean actual1 = ad.isEmpty();
        Assert.assertTrue(actual1);

        ad.addFirst(5);
        boolean actual2 = ad.isEmpty();
        Assert.assertFalse(actual2);
    }

    @Test
    public void sizeTest() {
        ArrayDeque<Integer> ad = new ArrayDeque<Integer>();
        int expected = 5;
        for (int i = 0; i < 6; i++) {
            ad.addFirst(i);
        }
        ad.removeLast();
        int actual = ad.size;
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void printDequeTest() {
        Assert.fail();
    }

    @Test
    public void removeFirstTest() {
        ArrayDeque<Integer> ad = new ArrayDeque<Integer>();
        int expected = 0;
        for (int i = 0; i < 8; i++) {
            ad.addLast(i);
        }
        int actual = ad.removeFirst();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void removeLastTest() {
        ArrayDeque<Integer> ad = new ArrayDeque<Integer>();
        int expected = 0;
        for (int i = 0; i < 8; i++) {
            ad.addFirst(i);
        }
        int actual = ad.removeLast();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void invariantTest() {
        ArrayDeque<Integer> ad = new ArrayDeque<Integer>();
        for (int i = 0; i < 8; i++) {
            ad.addLast(i);
        }
        for (int i = 0; i < 8; i++) {
            ad.removeFirst();
        }
        Assert.fail();
    }

    @Test
    public void resizeTest() {
        ArrayDeque<Integer> ad = new ArrayDeque<Integer>();
        int expected = 200;
        ad.addLast(20);
        for (int i = 1; i <= 8; i++) {
            ad.addFirst(i);
        }
        ad.addFirst(9);
        ad.addLast(200);
        int actual = ad.get(ad.size - 1);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void resizeDownTest() {
        ArrayDeque<Integer> ad = new ArrayDeque<Integer>();
        int expected = 2;
        for (int i = 1; i <= 16; i++) {
            ad.addFirst(i);
        }
        for (int i = 0; i < 14; i++) {
            ad.removeLast();
        }
        int actual = ad.size;
        Assert.assertEquals(expected, actual);
    }
}
