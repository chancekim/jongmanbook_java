package Greedy;

import java.util.Arrays;
import java.util.Scanner;
import java.util.StringTokenizer;

public class MatchOrder {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int cases = Integer.parseInt(sc.nextLine());

        while (--cases >= 0) {
            int playerCnt = Integer.parseInt(sc.nextLine());

            StringTokenizer russianSt = new StringTokenizer(sc.nextLine());
            StringTokenizer koreanSt = new StringTokenizer(sc.nextLine());

            int[] russians = new int[playerCnt];
            int[] koreans = new int[playerCnt];

            for (int i = 0; i < playerCnt; i++) {
                russians[i] = Integer.parseInt(russianSt.nextToken());
                koreans[i] = Integer.parseInt(koreanSt.nextToken());
            }

            Arrays.sort(russians);
            Arrays.sort(koreans);

            int koreanIdx = 0;
            int result = 0;

            for (int i = 0; i < playerCnt; i++) {
                while (koreanIdx < playerCnt && russians[i] > koreans[koreanIdx]) {
                    koreanIdx++;
                }
                if (koreanIdx == playerCnt) break;
                koreanIdx++;
                result++;
            }

            System.out.println(result);
        }
    }
}
