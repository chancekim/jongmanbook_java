package DynamicProgramming;

import java.util.Scanner;


public class Tiling2 {
    private static long[] cache = new long[101];

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int cases = Integer.parseInt(sc.nextLine());

        cache[0] = 1;
        cache[1] = 1;
        calcSolution(100);

        while (--cases >= 0) {
            int n = Integer.parseInt(sc.nextLine());
            System.out.println(cache[n]);
        }
    }

    private static long calcSolution(int n) {
        if (cache[n] != 0L) return cache[n];
        cache[n] = (calcSolution(n - 1) + calcSolution(n - 2)) % 1000000007L;
        return cache[n];
    }
}
