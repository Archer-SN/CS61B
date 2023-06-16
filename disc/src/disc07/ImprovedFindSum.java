package disc07;

public class ImprovedFindSum {
    /** See if the number exists in the sorted array */
    private static boolean findNum(int[] A, int x, int start, int end) {
        if (start > end) {
            return false;
        }
        int middle = (start + end) / 2;
        if (A[middle] == x) {
            return true;
        } else if (A[middle] < x) {
            return findNum(A, x, start, middle - 1);
        } else {
            return findNum(A, x, middle + 1, end);
        }
    }

    public static boolean improvedFindSum(int[] A, int x) {
        for (int i = 0; i < A.length; i++) {
            int a = A[i];
            if (x > a) {
                return false;
            }
            int b = x - a;
            if (findNum(A, b, 0, A.length - 1)) {
                return true;
            }
        }
        return false;
    }

}
