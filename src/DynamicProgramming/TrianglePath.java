package DynamicProgramming;

import java.util.Scanner;
import java.util.StringTokenizer;

public class TrianglePath {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int cases = Integer.parseInt(sc.nextLine());

        while (--cases >= 0) {
            int width = Integer.parseInt(sc.nextLine());
            int[][] triangle = new int[width][width];

            for (int i = 0; i < width; i++) {
                StringTokenizer st = new StringTokenizer(sc.nextLine(), " ");

                for (int j = 0; j <= i; j++) {
                    triangle[i][j] = Integer.parseInt(st.nextToken());
                }
            }

            for (int i = width - 2; i >= 0; i--) {
                for (int j = i; j >= 0; j--) {
                    int max = triangle[i][j];

                    max = Math.max(max, triangle[i][j] + triangle[i+1][j]);
                    max = Math.max(max, triangle[i][j] + triangle[i+1][j+1]);

                    triangle[i][j] = max;
                }
            }

            System.out.println(triangle[0][0]);
        }
    }
}
