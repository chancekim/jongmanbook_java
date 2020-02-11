package DynamicProgramming;

import java.util.Scanner;
import java.util.StringTokenizer;

public class Morse {
    private static long[][] cache;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int cases = Integer.parseInt(sc.nextLine());

        while (--cases >= 0) {
            StringTokenizer st = new StringTokenizer(sc.nextLine());
            int longDots = Integer.parseInt(st.nextToken());
            int shortDots = Integer.parseInt(st.nextToken());
            long order = Long.parseLong(st.nextToken());

            cache = new long[longDots + 1][shortDots + 1];

            for (int i = 0; i <= shortDots; i++) {
                cache[0][i] = 1;
            }

            for (int i = 0; i <= longDots; i++) {
                cache[i][0] = 1;
            }

            countMorse(longDots, shortDots);

            System.out.println(reconstruct(longDots, shortDots, order));
        }
    }

    private static long countMorse(int longDots, int shortDots) {
        if (longDots == 0 || shortDots == 0) return 1;
        if (cache[longDots][shortDots] != 0) return cache[longDots][shortDots];

        cache[longDots][shortDots] = Math.min(1000000000, countMorse(longDots - 1, shortDots) + countMorse(longDots, shortDots - 1));
        return cache[longDots][shortDots];
    }

    private static String reconstruct(int longDots, int shortDots, long order) {
        if (longDots == 0) {
            StringBuffer sb = new StringBuffer();

            for (int i = 0; i < shortDots; i++) {
                sb.append("o");
            }

            return sb.toString();
        } else if (shortDots == 0) {
            StringBuffer sb = new StringBuffer();

            for (int i = 0; i < longDots; i++) {
                sb.append("-");
            }

            return sb.toString();
        }

        if (cache[longDots - 1][shortDots] >= order) {
            return "-" + reconstruct(longDots - 1, shortDots, order);
        } else {
            return "o" + reconstruct(longDots, shortDots - 1, order - cache[longDots - 1][shortDots]);
        }
    }
}
