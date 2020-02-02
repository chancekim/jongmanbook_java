package DynamicProgramming;

import java.util.Arrays;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Quantize {
    private static int[] numbers;
    private static int[] squareCache;
    private static int[] sumCache;
    private static int[][] cache;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int cases = Integer.parseInt(sc.nextLine());

        while (--cases >= 0) {
            StringTokenizer st = new StringTokenizer(sc.nextLine());
            int n = Integer.parseInt(st.nextToken());
            int s = Integer.parseInt(st.nextToken());
            numbers = new int[n];
            sumCache = new int[n];
            squareCache = new int[n];
            cache = new int[s + 1][n];

            st = new StringTokenizer(sc.nextLine());
            for (int i = 0; i < n; i++) {
                numbers[i] = Integer.parseInt(st.nextToken());
            }
            Arrays.sort(numbers);
            for (int i = 0; i < n; i++) {
                if (i == 0) {
                    sumCache[i] = numbers[i];
                    squareCache[i] = numbers[i] * numbers[i];
                } else {
                    sumCache[i] = sumCache[i - 1] + numbers[i];
                    squareCache[i] = squareCache[i - 1] + numbers[i] * numbers[i];
                }
            }
            for (int i = 0; i <= s; i++) {
                Arrays.fill(cache[i], -1);
            }

            System.out.println(quantize(s, 0));
        }
    }

    private static int quantize(int remaining, int from) {
        if (cache[remaining][from] != -1) return cache[remaining][from];
        if (remaining == 1) {
            cache[remaining][from] = getMinSum(from, numbers.length - 1);
            return cache[remaining][from];
        }
        if (from == numbers.length - 1) return 0;

        int min = 1000000000;
        for (int i = from; i < numbers.length - 1; i++) {
            min = Math.min(min, quantize(remaining - 1, i + 1) + getMinSum(from, i));
        }
        cache[remaining][from] = min;
        return cache[remaining][from];
    }

    private static int getMinSum(int start, int end) {
        if (start == end) return 0;

        int sum;
        int sumOfSquare;

        if (start == 0) {
            sum = sumCache[end];
        } else {
            sum = sumCache[end] - sumCache[start - 1];
        }
        int aver = Math.round(sum /(float)(end - start + 1));

        if (start == 0) {
            sumOfSquare = squareCache[end];
        } else {
            sumOfSquare = squareCache[end] - squareCache[start - 1];
        }
        return sumOfSquare - 2 * aver * sum + aver * aver * (end - start + 1);
    }
}
