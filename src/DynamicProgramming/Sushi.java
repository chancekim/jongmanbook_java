package DynamicProgramming;

import java.util.Scanner;
import java.util.StringTokenizer;

public class Sushi {
    private static int[] prices;
    private static int[] priorities;
    private static int[] cache;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int cases = Integer.parseInt(sc.nextLine());

        while(--cases >= 0) {
            StringTokenizer st = new StringTokenizer(sc.nextLine().trim());
            int sushiCnt = Integer.parseInt(st.nextToken());
            int budget = Integer.parseInt(st.nextToken()) / 100;

            prices = new int[sushiCnt];
            priorities = new int[sushiCnt];
            cache = new int[300];

            for (int i= 0; i < sushiCnt; i++) {
                st = new StringTokenizer(sc.nextLine().trim());
                prices[i] = Integer.parseInt(st.nextToken()) / 100;
                priorities[i] = Integer.parseInt(st.nextToken());
            }

            for (int i = 1; i <= budget; i++) {
                int maxPriority = 0;
                for (int j = 0; j < prices.length; j++) {
                    if (prices[j] <= i) {
                        int tmpPriority = priorities[j] + cache[(i - prices[j]) % 200];
                        maxPriority = Math.max(maxPriority, tmpPriority);
                    }
                }
                cache[i % 200] = maxPriority;
            }

            System.out.println(cache[budget % 200]);
        }
    }
}
