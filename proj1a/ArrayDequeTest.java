public class ArrayDequeTest {
    public static void addGetTest() {
        ArrayDeque<Integer> ad = new ArrayDeque<>();
        ad.addFirst(1);
        ad.addFirst(2);
        int value = ad.get(1);
        System.out.println(value);
    }

    public static void addFirstAddLastTest() {
        ArrayDeque<Integer> ad = new ArrayDeque<>();
        ad.addFirst(1);
        ad.addFirst(2);
        ad.addLast(5);
        ad.addLast(9);
        int value = ad.get(3);
        System.out.println(value);
    }

    public static void resizeTest() {
        ArrayDeque<Integer> ad = new ArrayDeque<>();
        for (int i = 0; i < 6; i++) {
            ad.addFirst(1);
        }
        ad.addLast(2);
        ad.addLast(2);
        ad.addLast(3);

        ad.printDeque();
    }

    public static void randomTest1() {
        ArrayDeque<Integer> ad = new ArrayDeque<>();
        ad.addFirst(0);
        ad.addFirst(1);
        ad.removeFirst();
    }

    public static void randomTest2() {
        ArrayDeque<Integer> ad = new ArrayDeque<>();
        ad.isEmpty();
        for (int i = 1; i <= 5; i++) {
            ad.addFirst(i);
        }
        ad.removeLast();
    }


    public static void main(String[] args) {
        randomTest2();
    }
}
