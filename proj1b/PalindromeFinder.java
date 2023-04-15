/**
 * This class outputs all palindromes in the words file in the current directory.
 */
public class PalindromeFinder {
    public static void main(String[] args) {
        int minLength = 2;
        Palindrome palindrome = new Palindrome();
        int maxWordCount = 0;
        int N = 0;

        for (int i = 0; i < 26; i++) {
            int longest = 0;
            OffByN obn = new OffByN(i);
            In in = new In("../library-sp19/data/words.txt");

            while (!in.isEmpty()) {
                String word = in.readString();
                if (word.length() >= minLength && palindrome.isPalindrome(word, obn)) {
                    if (word.length() > longest) {
                        longest = word.length();
                    }
                }
            }
            System.out.println("N: " + i + " Longest: " + longest);
        }
    }
}
