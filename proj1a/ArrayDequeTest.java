public class ArrayDequeTest {

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

    public static void randomTest3() {
        ArrayDeque<Integer> ad = new ArrayDeque<>();
        ad.addFirst(0);
        ad.removeFirst();
        ad.addFirst(2);
        ad.removeFirst();
        ad.addLast(4);
        ad.addFirst(5);
        ad.removeFirst();
        ad.addLast(7);
        ad.addFirst(8);
        ad.addLast(9);
        ad.get(3);
        ad.removeFirst();
        ad.removeFirst();
        ad.removeLast();
        ad.removeFirst();
    }

    private static void randomTest4() {
        ArrayDeque<Integer> ad = new ArrayDeque<>();
        ad.addFirst(0);
        ad.addFirst(1);
        ad.addFirst(2);
        ad.size();
        ad.addLast(4);
        ad.addLast(5);
        ad.addLast(6);
        ad.addLast(7);
        ad.addLast(8);
        ad.size();
        ad.size();
        ad.addFirst(11);
    }

    private static void randomTest5() {
        ArrayDeque<Integer> ad = new ArrayDeque<>();
        ad.addLast(0);
        ad.removeFirst();
        ad.size();
        ad.addLast(3);
        ad.removeLast();
        ad.addFirst(5);
        ad.addFirst(6);
    }

    private static void randomTest6() {
        ArrayDeque<Integer> ad = new ArrayDeque<>();
        ad.addLast(0);
        ad.removeFirst();
        ad.isEmpty();
        ad.addLast(3);
        ad.addFirst(4);
        ad.removeLast();
    }

    private static void randomTest7() {
        ArrayDeque<Integer> ad = new ArrayDeque<>();
        ad.addFirst(0);
        ad.addFirst(1);
        ad.addFirst(2);
        ad.addFirst(3);
        ad.addFirst(4);
        ad.addFirst(5);
        ad.addFirst(6);
        ad.removeLast();
        ad.isEmpty();
        ad.addFirst(9);
        ad.removeLast();
    }

    private static void randomTest8() {
        ArrayDeque<Integer> ad = new ArrayDeque<>();
        ad.addFirst(0);
        ad.removeFirst();
        ad.addFirst(2);
        ad.get(0);
        ad.get(0);
        ad.get(0);
        ad.get(0);
        ad.addLast(7);
        ad.removeFirst();
        ad.addLast(9);
        ad.removeFirst();
        ad.removeLast();
        ad.addFirst(12);
        ad.removeLast();
    }

    private static void randomTest9() {
        ArrayDeque<Integer> ad = new ArrayDeque<>();
        ad.addFirst(0);
        ad.addFirst(1);
        ad.addFirst(2);
        ad.addFirst(3);
        ad.addFirst(4);
        ad.addFirst(5);
        ad.addFirst(6);
        ad.removeLast();
        ad.isEmpty();
        ad.addFirst(9);
        ad.removeLast();
    }

    private static void addGetTest() {
        ArrayDeque<Integer> ad = new ArrayDeque<>();
        for (int i = 1; i <= 8; i++) {
            ad.addLast(i);
        }
        System.out.println(ad.get(7));

    }

    private static void addGetTest2() {
        ArrayDeque<Integer> ad = new ArrayDeque<>();
        for (int i = 1; i <= 16; i++) {
            ad.addFirst(i);
        }
        //System.out.println(ad.get(5));

    }


    public static void main(String[] args) {
        addGetTest();
        addGetTest2();
        randomTest2();
        randomTest3();
        randomTest4();
        randomTest5();
        randomTest6();
        randomTest7();
        randomTest8();
        randomTest9();
    }
}
