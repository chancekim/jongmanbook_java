package ExhaustiveSearch;

import java.util.Scanner;

public class ClockSync {
    static int[][] switchs = new int[][]{
            {0, 1, 2},
            {3, 7, 9, 11},
            {4, 10, 14, 15},
            {0, 4, 5, 6, 7},
            {6, 7, 8, 10, 12},
            {0, 2, 14, 15},
            {3, 14, 15},
            {4, 5, 7, 14, 15},
            {1, 2, 3, 4, 5},
            {3, 4, 5, 9, 13},
    };
    private static int MAX = 10000;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int cases = sc.nextInt();
        int[] clocks = new int[16];

        for (int iter = 0; iter < cases; iter++) {
            for (int c = 0; c < 16; c++) {
                clocks[c] = sc.nextInt();
            }

            int result = switchOn(clocks, 0, 0);
            if (result == MAX) result = -1;

            System.out.println(result);
        }
    }

    public static int switchOn(int[] clocks, int switchIndex, int onCounts) {
        boolean isAllTwelve = true;
        for (int i = 0; i < 16; i++) {
            if (clocks[i] != 12) isAllTwelve = false;
        }
        if (isAllTwelve) return onCounts;
        if (switchIndex >= 10) return MAX;

        int min = MAX;
        min = Math.min(min, switchOn(clocks, switchIndex + 1, onCounts));

        for (int i = 1; i < 4; i++) {
            for (int connected : switchs[switchIndex]) {
                clocks[connected] = plusThree(clocks[connected]);
            }
            int result = switchOn(clocks, switchIndex + 1, onCounts + i);
            min = Math.min(min, result);
        }

        for (int connected : switchs[switchIndex]) {
            clocks[connected] = plusThree(clocks[connected]);
        }

        return min;
    }

    private static int plusThree(int hour) {
        int result = hour + 3;

        if (result == 15) result = 3;

        return result;
    }
}
