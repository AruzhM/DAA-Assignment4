package stringsearch;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;

public class StringSearchTest {

    private final NaiveSearch naive = new NaiveSearch();
    private final KMPSearch kmp = new KMPSearch();

    private void checkBoth(String text, String pattern,
                           List<Integer> expected) {
        assertEquals(expected, naive.search(text, pattern));
        assertEquals(expected, kmp.search(text, pattern));
    }

    @Test
    void referenceValues() {
        checkBoth("ABABDABACDABABCABAB",
                "ABABCABAB", List.of(10));

        checkBoth("AAAAA", "AA",
                List.of(0, 1, 2, 3));

        checkBoth("ABABABA", "ABA",
                List.of(0, 2, 4));
    }

    @Test
    void lpsReferenceValues() {
        assertArrayEquals(
                new int[]{0, 1, 2, 0, 1, 2, 3, 3},
                kmp.lps("AAACAAAA")
        );

        assertArrayEquals(
                new int[]{0, 0, 1, 2, 0, 1, 2, 3, 4},
                kmp.lps("ABABCABAB")
        );
    }

    @Test
    void emptyPatternThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> naive.search("hello", ""));

        assertThrows(IllegalArgumentException.class,
                () -> kmp.search("hello", ""));
    }

    @Test
    void patternLongerThanText() {
        checkBoth("abc", "abcdef", List.of());
        checkBoth("", "abc", List.of());
    }

    @Test
    void noMatches() {
        checkBoth("abcdef", "xyz", List.of());
    }


    @Test
    void randomConsistency() {
        Random random = new Random(42);

        for (int t = 0; t < 100; t++) {
            String text = randomString(random, 20);
            String pattern = randomString(random, 5) + "a";

            assertEquals(
                    naive.search(text, pattern),
                    kmp.search(text, pattern)
            );
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
