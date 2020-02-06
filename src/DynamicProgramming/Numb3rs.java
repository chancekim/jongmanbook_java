package DynamicProgramming;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

public class Numb3rs {
    private static int townCnt;
    private static int[][] adjacent;
    private static int[] adjCnts;
    private static double[][] cache;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int cases = Integer.parseInt(sc.nextLine());

        while (--cases >= 0) {
            StringTokenizer st = new StringTokenizer(sc.nextLine());
            townCnt = Integer.parseInt(st.nextToken());
            int days = Integer.parseInt(st.nextToken());
            int prison = Integer.parseInt(st.nextToken());

            adjacent = new int[townCnt][townCnt];
            adjCnts = new int[townCnt];
            cache = new double[days+1][townCnt];

            for (int i = 0; i < townCnt; i++) {
                st = new StringTokenizer(sc.nextLine());
                int adjCnt = 0;
                for (int j = 0; j < townCnt; j++) {
                    int trueOfFalse = Integer.parseInt(st.nextToken());
                    if (trueOfFalse == 1) adjCnt++;
                    adjacent[i][j] = trueOfFalse;
                }
                adjCnts[i] = adjCnt;
            }
            for (int i = 0; i < townCnt; i++) {
                if (i == prison) {
                    cache[0][i] = 1.0;
                } else {
                    cache[0][i] = 0.0;
                }
            }
            for (int i = 1; i <= days; i++) {
                for (int j = 0; j < townCnt; j++) {
                    cache[i][j] = -1.0;
                }
            }

            int solutionCnt = Integer.parseInt(sc.nextLine());
            List<Integer> targets = new ArrayList<Integer>();
            st = new StringTokenizer(sc.nextLine());
            while (--solutionCnt >= 0) {
                targets.add(Integer.valueOf(st.nextToken()));
            }

            System.out.println(targets.stream()
                    .map(town -> String.valueOf(calcProbability(days, town)))
                    .collect(Collectors.joining(" "))
            );
        }
    }

    private static double calcProbability(int days, int town) {
        if (cache[days][town] >= 0.0) return cache[days][town];

        double result = 0.0;

        for (int i = 0; i < townCnt; i++) {
            if (adjacent[town][i] == 1) {
                result += calcProbability(days - 1, i) / adjCnts[i];
            }
        }
        cache[days][town] = result;
        return result;
    }
}
