package DynamicProgramming;

import java.util.Arrays;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Snail {
    private static double[][] cache;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int cases = Integer.parseInt(sc.nextLine());

        while (--cases >= 0) {
            StringTokenizer st = new StringTokenizer(sc.nextLine());
            int depth = Integer.parseInt(st.nextToken());
            int days = Integer.parseInt(st.nextToken());

            cache = new double[days + 1][depth + 1];

            for (int i = 0; i <= days; i++) {
                Arrays.fill(cache[i], -1.0);
            }

            System.out.println(calcPossibility(days, depth));
        }
    }

    private static double calcPossibility(int remainingDays, int remainingDepth) {
        if (remainingDays >= 0 && remainingDepth <= 0) return 1.0;
        else if (remainingDays == 0) return 0.0;

        if (cache[remainingDays][remainingDepth] >= 0.0) return cache[remainingDays][remainingDepth];

        cache[remainingDays][remainingDepth] = 0.75 * calcPossibility(remainingDays - 1, remainingDepth - 2)
                + 0.25 * calcPossibility(remainingDays - 1, remainingDepth - 1);

        return cache[remainingDays][remainingDepth];
    }
}
