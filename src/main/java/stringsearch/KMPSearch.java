package stringsearch;
import java.util.ArrayList;
import java.util.List;

public class KMPSearch {

    private long comparisons;

    public int[] lps(String pattern) {
        int[] lps = new int[pattern.length()];
        int length = 0;
        int i = 1;

        while (i < pattern.length()) {
            comparisons++;

            if (pattern.charAt(i) == pattern.charAt(length)) {
                length++;
                lps[i] = length;
                i++;
            } else if (length != 0) {
                length = lps[length - 1];
            } else {
                lps[i] = 0;
                i++;
            }
        }

        return lps;
    }

    public List<Integer> search(String text, String pattern) {
        if (pattern.isEmpty()) {
            throw new IllegalArgumentException("Pattern cannot be empty");
        }

        comparisons = 0;
        List<Integer> matches = new ArrayList<>();

        int n = text.length();
        int m = pattern.length();

        if (m > n) {
            return matches;
        }

        int[] prefix = lps(pattern);

        int i = 0;
        int j = 0;

        while (i < n) {
            comparisons++;

            if (text.charAt(i) == pattern.charAt(j)) {
                i++;
                j++;
            } else if (j != 0) {
                j = prefix[j - 1];
            } else {
                i++;
            }

            if (j == m) {
                matches.add(i - j);
                j = prefix[j - 1];
            }
        }

        return matches;
    }

    public long getComparisons() {
        return comparisons;
    }
}
