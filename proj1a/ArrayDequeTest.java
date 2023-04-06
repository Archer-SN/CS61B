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
        for (int i = 0; i < 9; i++) {
            ad.addFirst(1);
        }
    }


    public static void main(String[] args) {
        addGetTest();
        addFirstAddLastTest();
        resizeTest();
    }
}
