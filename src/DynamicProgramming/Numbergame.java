package DynamicProgramming;

import java.util.Scanner;
import java.util.StringTokenizer;

public class Numbergame {
    private static int[] numbers;
    private static int[][][] cache;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int cases = Integer.parseInt(sc.nextLine().trim());

        while (--cases >= 0) {
            int numberCnt = Integer.parseInt(sc.nextLine().trim());
            numbers = new int[numberCnt];
            cache = new int[numberCnt][numberCnt][2];

            StringTokenizer st = new StringTokenizer(sc.nextLine().trim());

            for (int i = 0; i < numberCnt; i++) {
                numbers[i] = Integer.parseInt(st.nextToken());
            }

            for (int i = 0; i < numberCnt; i++) {
                for (int j = 0; j < numberCnt; j++) {
                    cache[i][j][0] = 1000000;
                    cache[i][j][1] = 1000000;
                }
            }

            System.out.println(playGame(0, numberCnt - 1, true));
        }
    }

    private static int playGame(int start, int end, boolean isHyunwoo) {
        if (start > end) {
            return 0;
        }

        if (cache[start][end][isHyunwoo ? 1 : 0] != 1000000) {
            return cache[start][end][isHyunwoo ? 1 : 0];
        }

        int result;

        if (isHyunwoo) {
            result = numbers[start] + playGame(start + 1, end, !isHyunwoo);
            result = getOptimal(result, numbers[end] + playGame(start, end - 1, !isHyunwoo), isHyunwoo);
        } else {
            result = playGame(start + 1, end, !isHyunwoo) - numbers[start];
            result = getOptimal(result, playGame(start, end - 1, !isHyunwoo) - numbers[end], isHyunwoo);
        }

        if (end - start >= 1) {
            result = getOptimal(result, playGame(start + 2, end, !isHyunwoo), isHyunwoo);
            result = getOptimal(result, playGame(start, end - 2, !isHyunwoo), isHyunwoo);
        }

        cache[start][end][isHyunwoo ? 1 : 0] = result;
        return result;
    }

    private static int getOptimal(int result1, int result2, boolean isHyunwoo) {
        if (isHyunwoo) {
            return Math.max(result1, result2);
        } else {
            return Math.min(result1, result2);
        }
    }
}
