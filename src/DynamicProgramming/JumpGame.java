package DynamicProgramming;

import java.util.Scanner;
import java.util.StringTokenizer;

public class JumpGame {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int cases = Integer.parseInt(sc.nextLine());

        while (--cases >= 0) {
            int size = Integer.parseInt(sc.nextLine());
            int[][] map = new int[size][size];
            Boolean[][] possible = new Boolean[size][size];

            for (int y = 0; y < size; y++) {
                StringTokenizer st = new StringTokenizer(sc.nextLine());
                for (int x = 0; x < size; x++) {
                    map[y][x] = Integer.parseInt(st.nextToken());
                }
            }

            if (isAbleToBeFinished(map, possible, 0, 0)) {
                System.out.println("YES");
            } else {
                System.out.println("NO");
            }
        }
    }

    private static boolean isAbleToBeFinished(int[][] map, Boolean[][] possible, int x, int y) {
        if (x == map.length - 1 && y == map.length - 1) return true;
        if (x < 0 || x >= map.length || y < 0 || y >= map.length) return false;

        if (possible[y][x] != null) return possible[y][x];

        int d = map[y][x];
        possible[y][x] = isAbleToBeFinished(map, possible, x + d, y) || isAbleToBeFinished(map, possible, x, y + d);

        return possible[y][x];
    }
}
