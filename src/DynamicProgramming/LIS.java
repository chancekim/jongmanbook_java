package DynamicProgramming;

import java.util.Scanner;
import java.util.StringTokenizer;

public class LIS {
    private static int[] elements = new int[500];
    private static int[] cache = new int[500];
    private static int count = 0;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int cases = Integer.parseInt(sc.nextLine());

        while (--cases >= 0) {
            count = Integer.parseInt(sc.nextLine());
            StringTokenizer st = new StringTokenizer(sc.nextLine());

            for (int i = 0; i < count; i++) {
                elements[i] = Integer.parseInt(st.nextToken());
                cache[i] = 0;
            }

            int max = 0;
            for (int i = count - 1; i >= 0; i--) {
                max = Math.max(max, findLongestSubSet(i));
            }
            System.out.println(max);
        }
    }

    private static int findLongestSubSet(int index) {
        int max = 0;
        for (int i = index + 1; i < count; i++) {
            if (elements[i] > elements[index]) {
                max = Math.max(max, cache[i]);
            }
        }
        cache[index] = max + 1;
        return cache[index];
    }
}
