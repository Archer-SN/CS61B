public class OffByN implements CharacterComparator {
    // Determines how much 2 characters should be off
    private int n;

    public OffByN(int number) {
        // Should only be positive to prevent confusion
        n = Math.abs(number);
    }


    @Override
    public boolean equalChars(char x, char y) {
        return Math.abs(x - y) == n;
    }
}
