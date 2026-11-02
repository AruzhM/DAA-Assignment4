package experiment;
import java.util.Random;

public class InputGenerator {

    private final Random random;

    public InputGenerator(long seed) {
        random = new Random(seed);
    }

    public String randomString(int length, int alphabetSize) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < length; i++) {
            result.append((char) ('a' + random.nextInt(alphabetSize)));
        }

        return result.toString();
    }

    public String repeatedString(int length, char character) {
        return String.valueOf(character).repeat(length);
    }

    public String alternatingString(int length) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < length; i++) {
            result.append(i % 2 == 0 ? 'a' : 'b');
        }

        return result.toString();
    }

    public String worstCasePattern(int length) {
        if (length <= 1) {
            return "b";
        }

        return "a".repeat(length - 1) + "b";
    }
}
