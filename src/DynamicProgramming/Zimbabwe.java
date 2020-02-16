package DynamicProgramming;

import java.util.Arrays;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Zimbabwe {
    private static int[] price;
    private static int candyCnt;
    private static int finishFlag;
    private static int[][][] cache;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int cases = Integer.parseInt(sc.nextLine());

        while (--cases >= 0) {
            StringTokenizer st = new StringTokenizer(sc.nextLine());
            char[] priceCharArray = st.nextToken().toCharArray();
            price = new int[priceCharArray.length];
            finishFlag = 0;
            for (int i = 0; i < priceCharArray.length; i++) {
                price[i] = priceCharArray[i] - '0';
                finishFlag = finishFlag | (0x1 << i);
            }
            candyCnt = Integer.parseInt(st.nextToken());

            cache = new int[candyCnt][finishFlag + 1][2];

            for (int i = 0; i < candyCnt; i++) {
                for (int j = 0; j <= finishFlag; j++) {
                    Arrays.fill(cache[i][j], -1);
                }
            }

            System.out.println(getCandidateCnt(0, 0, 0,true));
        }
    }

    private static int getCandidateCnt(int usedDigits, int remainder, int currentIndex, boolean hasLimit) {
        if (currentIndex == price.length) {
            return (!hasLimit && remainder == 0) ? 1 : 0;
        }
        if (cache[remainder][usedDigits][hasLimit ? 1 : 0] != -1) {
            return cache[remainder][usedDigits][hasLimit ? 1 : 0];
        }

        int cnt = 0;

        boolean[] duplicate = new boolean[10];

        for (int i = 0; i < price.length; i++) {
            if (((0x1 << i) & usedDigits) == 0 && !duplicate[price[i]]) {
                boolean nextHasLimit = false;

                if (hasLimit && price[i] > price[currentIndex]) {
                    continue;
                }
                if (hasLimit && price[i] == price[currentIndex]) {
                    nextHasLimit = true;
                }
                int nextRemainder = ((price[i] % candyCnt) + remainder * 10) % candyCnt;

                cnt = (cnt + getCandidateCnt(
                        (0x1 << i) | usedDigits,
                        nextRemainder,
                        currentIndex + 1,
                        nextHasLimit
                ) % 1000000007) % 1000000007;

                duplicate[price[i]] = true;
            }
        }

        cache[remainder][usedDigits][hasLimit ? 1 : 0] = cnt;

        return cache[remainder][usedDigits][hasLimit ? 1 : 0];
    }
}
