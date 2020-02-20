package DynamicProgramming;

import java.util.Arrays;
import java.util.Scanner;

public class TicTacToe {
    private static int[] cache = new int[59049];

    public static void main(String[] args) {
        Arrays.fill(cache, -1);

        Scanner sc = new Scanner(System.in);
        int cases = Integer.parseInt(sc.nextLine());

        while (--cases >= 0) {
            char[][] problem = new char[3][3];

            for (int i = 0; i < 3; i++) {
                String line = sc.nextLine().trim();
                problem[i] = line.toCharArray();
            }

            int result = canWin(problem);

            int cnt = 0;

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (problem[i][j] != '.') cnt++;
                }
            }

            if (result == 1) {
                System.out.println("TIE");
            } else if (result == 0) {
                if (cnt % 2 == 1) {
                    System.out.println('x');
                } else {
                    System.out.println('o');
                }
            } else {
                if (cnt % 2 == 1) {
                    System.out.println('o');
                } else {
                    System.out.println('x');
                }
            }
        }
    }

    private static int canWin(char[][] problem) {
        int intProblem = toInt(problem);

        if (cache[intProblem] != -1) {
            return cache[intProblem];
        }

        if (isEnd(problem)) {
            cache[intProblem] = 0;
            return cache[intProblem];
        }

        int cnt = 0;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (problem[i][j] != '.') cnt++;
            }
        }

        if (cnt == 9) {
            cache[intProblem] = 1;
            return cache[intProblem];
        }

        boolean lose = false;
        boolean draw = false;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (problem[i][j] == '.') {
                    if (cnt % 2 == 1) {
                        problem[i][j] = 'o';
                    } else {
                        problem[i][j] = 'x';
                    }
                    int nextResult = canWin(problem);

                    if (nextResult == 0) lose = true;
                    else if (nextResult == 1) draw = true;

                    problem[i][j] = '.';
                }
            }
        }

        if (lose) {
            cache[intProblem] = 2;
        } else if (draw) {
            cache[intProblem] = 1;
        } else {
            cache[intProblem] = 0;
        }
        return cache[intProblem];
    }

    private static int toInt(char[][] map) {
        int problem = 0;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (map[i][j] == '.') {
                    problem = problem * 3;
                } else if (map[i][j] == 'x') {
                    problem = problem * 3 + 1;
                } else {
                    problem = problem * 3 + 2;
                }
            }
        }

        return problem;
    }

    private static boolean isEnd(char[][] problem) {
        if (problem[0][0] != '.' && problem[0][0] == problem[0][1] && problem[0][1] == problem[0][2]) {
            return true;
        } else if (problem[0][0] != '.' && problem[0][0] == problem[1][0] && problem[1][0] == problem[2][0]) {
            return true;
        } else if (problem[0][0] != '.' && problem[0][0] == problem[1][1] && problem[1][1] == problem[2][2]) {
            return true;
        } else if (problem[0][2] != '.' && problem[0][2] == problem[1][1] && problem[1][1] == problem[2][0]) {
            return true;
        } else if (problem[0][2] != '.' && problem[0][2] == problem[1][2] && problem[1][2] == problem[2][2]) {
            return true;
        } else if (problem[2][0] != '.' && problem[2][0] == problem[2][1] && problem[2][1] == problem[2][2]) {
            return true;
        } else if (problem[1][0] != '.' && problem[1][0] == problem[1][1] && problem[1][1] == problem[1][2]) {
            return true;
        } else if (problem[0][1] != '.' && problem[0][1] == problem[1][1] && problem[1][1] == problem[2][1]) {
            return true;
        }
        return false;
    }
}
