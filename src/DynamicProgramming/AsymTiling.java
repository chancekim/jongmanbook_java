package DynamicProgramming;

import java.util.Arrays;
import java.util.Scanner;

public class AsymTiling {
    private static long[] totalCount = new long[101];
    private static long[] symmetricCount = new long[101];

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int cases = Integer.parseInt(sc.nextLine());

        initCache();

        while (--cases >= 0) {
            int size = Integer.parseInt(sc.nextLine());

            long ans = totalCount[size] - symmetricCount[size];
            if (ans < 0) {
                ans += 1000000007L;
            }
            System.out.println(ans);
        }
    }

    private static void initCache() {
        Arrays.fill(totalCount, -1);
        totalCount[0] = 0;
        totalCount[1] = 1;
        totalCount[2] = 2;
        calcTotalCount(100);

        Arrays.fill(symmetricCount, -1);
        symmetricCount[0] = 0;
        symmetricCount[1] = 1;
        symmetricCount[2] = 2;
        symmetricCount[3] = 1;
        symmetricCount[4] = 3;
        calcSymmetricCount(100);
        calcSymmetricCount(99);
    }

    private static long calcTotalCount(int size) {
        if (totalCount[size] != -1) return totalCount[size];

        totalCount[size] = (calcTotalCount(size - 1) + calcTotalCount(size - 2)) % 1000000007L;
        return totalCount[size];
    }

    private static long calcSymmetricCount(int size) {
        if (symmetricCount[size] != -1) return symmetricCount[size];

        symmetricCount[size] = (calcSymmetricCount(size - 2) + calcSymmetricCount(size - 4)) % 1000000007L;
        return symmetricCount[size];
    }
}
