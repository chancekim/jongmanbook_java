package DynamicProgramming;

import java.util.Arrays;
import java.util.Scanner;

public class PI {
    private static char[] numbers;
    private static int[] cache;
    private final static int MAX = 10000000;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int cases = Integer.parseInt(sc.nextLine().trim());

        while (--cases >= 0) {
            String line = sc.nextLine().trim();
            numbers = line.toCharArray();
            cache = new int[numbers.length];
            Arrays.fill(cache, MAX);
            System.out.println(minDifficulty(0));
        }
    }

    private static int minDifficulty(int start) {
        if (cache[start] != MAX) return cache[start];
        if (numbers.length - start < 3) {
            cache[start] = MAX;
            return cache[start];
        }
        else if (numbers.length - start == 3 || numbers.length - start == 4 || numbers.length - start == 5) {
            cache[start] = getDifficulty(start, numbers.length - 1);
            return cache[start];
        }

        int min = MAX;
        min = Math.min(min, getDifficulty(start, start + 2) + minDifficulty(start + 3));
        if (numbers.length - start > 5) {
            min = Math.min(min, getDifficulty(start, start + 3) + minDifficulty(start + 4));
        }
        if (numbers.length - start > 6) {
            min = Math.min(min, getDifficulty(start, start + 4) + minDifficulty(start + 5));
        }

        cache[start] = min;

        return min;
    }

    private static int getDifficulty(int start, int end) {
        if (isOne(start, end)) return 1;
        else if (isTwo(start, end)) return 2;
        else if (isFour(start, end)) return 4;
        else if (isFive(start, end)) return 5;
        else return 10;
    }

    private static boolean isOne(int start, int end) {
        for (int i = start + 1; i <= end; i++) {
            if (numbers[start] != numbers[i]) return false;
        }
        return true;
    }

    private static boolean isTwo(int start, int end) {
        int diff;
        if (numbers[start] < numbers[end]) diff = 1;
        else diff = -1;

        for (int i = 1; i <= end - start; i++) {
            if (numbers[start + i] - numbers[start] != i * diff) return false;
        }
        return true;
    }

    private static boolean isFour(int start, int end) {
        for (int i = start; i <= end; i += 2) {
            if (numbers[start] != numbers[i]) return false;
        }
        for (int i = start + 1; i <= end; i += 2) {
            if (numbers[start + 1] != numbers[i]) return false;
        }
        return true;
    }

    private static boolean isFive(int start, int end) {
        int diff = numbers[start + 1] - numbers[start];
        for (int i = 1; i <= end - start; i++) {
            if (numbers[start + i] - numbers[start] != diff * i) return false;
        }
        return true;
    }
}
