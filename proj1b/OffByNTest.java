import org.junit.Test;
import static org.junit.Assert.*;
public class OffByNTest {
    @Test
    public void equalCharsTest() {
        OffByN obn = new OffByN(5);
        assertFalse(obn.equalChars('a', 'b'));
        assertTrue(obn.equalChars('a', 'f'));
        assertFalse(obn.equalChars('a', 'F'));
    }
}
