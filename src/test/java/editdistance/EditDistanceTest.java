package editdistance;
import org.junit.jupiter.api.Test;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;

public class EditDistanceTest {

    private final RecursiveEditDistance recursive =
            new RecursiveEditDistance();

    private final TableEditDistance table =
            new TableEditDistance();

    private final TwoRowsEditDistance twoRows =
            new TwoRowsEditDistance();

    private void checkAll(String a, String b, int expected) {
        assertEquals(expected, recursive.distance(a, b));
        assertEquals(expected, table.distance(a, b));
        assertEquals(expected, twoRows.distance(a, b));
    }

    @Test
    void referenceValues() {
        checkAll("kitten", "sitting", 3);
        checkAll("flaw", "lawn", 2);
        checkAll("intention", "execution", 5);
        checkAll("", "abc", 3);
        checkAll("abc", "abc", 0);
    }

    @Test
    void emptyStrings() {
        checkAll("", "", 0);
        checkAll("abc", "", 3);
        checkAll("", "hello", 5);
    }

    @Test
    void singleCharacters() {
        checkAll("a", "a", 0);
        checkAll("a", "b", 1);
        checkAll("a", "ab", 1);
    }

    @Test
    void randomConsistency() {
        Random random = new Random(42);

        for (int t = 0; t < 100; t++) {
            String a = randomString(random, 4);
            String b = randomString(random, 4);

            int expected = table.distance(a, b);

            assertEquals(expected, recursive.distance(a, b));
            assertEquals(expected, twoRows.distance(a, b));
        }
    }

    private String randomString(Random random, int maxLength) {
        int length = random.nextInt(maxLength + 1);
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < length; i++) {
            result.append((char) ('a' + random.nextInt(3)));
        }

        return result.toString();
    }
}
