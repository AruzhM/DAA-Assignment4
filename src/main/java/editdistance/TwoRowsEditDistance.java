package editdistance;

public class TwoRowsEditDistance implements EditDistance {

    private long operations;

    public int distance(String a, String b) {

        /*
         * State: previous[j] and current[j] store DP row values.
         * Transition: match or min(top, left, diagonal) + 1.
         * Base case: previous[j] = j; current[0] = i.
         */

        operations = 0;

        int n = a.length();
        int m = b.length();

        int[] previous = new int[m + 1];
        int[] current = new int[m + 1];

        for (int j = 0; j <= m; j++) {
            previous[j] = j;
        }

        for (int i = 1; i <= n; i++) {
            current[0] = i;

            for (int j = 1; j <= m; j++) {
                operations++;

                if (a.charAt(i - 1) == b.charAt(j - 1)) {
                    current[j] = previous[j - 1];
                } else {
                    current[j] = 1 + Math.min(
                            previous[j],
                            Math.min(
                                    current[j - 1],
                                    previous[j - 1]
                            )
                    );
                }
            }

            int[] temp = previous;
            previous = current;
            current = temp;
        }

        return previous[m];
    }

    public long getOperations() {
        return operations;
    }
}
