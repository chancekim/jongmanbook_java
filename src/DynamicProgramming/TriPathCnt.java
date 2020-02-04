package DynamicProgramming;

import java.util.Scanner;
import java.util.StringTokenizer;

public class TriPathCnt {
    private static long[][] triangle;
    private static int size;
    private static long[][] pathCntMemory;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int cases = Integer.parseInt(sc.nextLine().trim());

        while (--cases >= 0) {
            size = Integer.parseInt(sc.nextLine().trim());

            triangle = new long[size][size];
            pathCntMemory = new long[size][size];

            for (int i = 0; i < size; i++) {
                StringTokenizer st = new StringTokenizer(sc.nextLine());
                for (int j = 0; j <= i; j++) {
                    triangle[i][j] = Long.parseLong(st.nextToken());
                    if (i == size - 1) {
                        pathCntMemory[i][j] = 1L;
                    }
                }
            }

            fillMemory();

            System.out.println(pathCntMemory[0][0]);
        }
    }

    private static void fillMemory() {
        for (int i = size - 2; i >= 0; i--) {
            for (int j = 0; j <= i; j++) {
                long first = triangle[i+1][j];
                long second = triangle[i+1][j+1];
                if (first == second) {
                    pathCntMemory[i][j] = pathCntMemory[i+1][j] + pathCntMemory[i+1][j+1];
                    triangle[i][j] += triangle[i+1][j];
                } else if (first > second) {
                    pathCntMemory[i][j] = pathCntMemory[i+1][j];
                    triangle[i][j] += triangle[i+1][j];
                } else {
                    pathCntMemory[i][j] = pathCntMemory[i+1][j+1];
                    triangle[i][j] += triangle[i+1][j+1];
                }
            }
        }
    }
}
