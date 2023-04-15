public class Palindrome {

    /** Given String, convert it to Deque */
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> deque = new LinkedListDeque<>();
        for (int i = 0; i < word.length(); i++) {
            deque.addLast(word.charAt(i));
        }
        return deque;
    }

    /** Checks if a given word is a palindrome */
    public boolean isPalindrome(String word) {
        Deque<Character> deque = wordToDeque(word);
        while (deque.size() > 0) {
            /* Handles cases with odd length */
            if (deque.size() == 1) {
                return true;
            }
            /* If the first is not equal to last then it is not a palindrome */
            else if (deque.removeFirst() != deque.removeLast()) {
                return false;
            }
        }
        return true;
    }

    /** Checks if a given word is a palindrome
     * According the rules in CharacterComparator cc */
    public boolean isPalindrome(String word, CharacterComparator cc) {
        Deque<Character> deque = wordToDeque(word);
        while (deque.size() > 0) {
            /* Handles cases with odd length */
            if (deque.size() == 1) {
                return true;
            }
            /* If the first is not equal to last according to cc's rules then it is not a palindrome */
            else if (!cc.equalChars(deque.removeFirst(), deque.removeLast())) {
                return false;
            }
        }
        return true;
    }
}
