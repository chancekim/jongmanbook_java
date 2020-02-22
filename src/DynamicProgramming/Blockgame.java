package DynamicProgramming;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Blockgame {
    private static byte[] cache;
    private static int endFlag = 0b1111111111111111111111111;
    private static List<Integer> blocks = new ArrayList<>();

    static {
        boolean[][] tmp = new boolean[5][5];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                tmp[i][j] = true;
                tmp[i+1][j] = true;
                tmp[i+1][j+1] = true;
                blocks.add(toInt(tmp));
                tmp[i][j] = false;
                tmp[i+1][j] = false;
                tmp[i+1][j+1] = false;

                tmp[i+1][j] = true;
                tmp[i+1][j+1] = true;
                tmp[i][j+1] = true;
                blocks.add(toInt(tmp));
                tmp[i+1][j] = false;
                tmp[i+1][j+1] = false;
                tmp[i][j+1] = false;

                tmp[i][j] = true;
                tmp[i+1][j] = true;
                tmp[i][j+1] = true;
                blocks.add(toInt(tmp));
                tmp[i][j] = false;
                tmp[i+1][j] = false;
                tmp[i][j+1] = false;

                tmp[i][j] = true;
                tmp[i][j+1] = true;
                tmp[i+1][j+1] = true;
                blocks.add(toInt(tmp));
                tmp[i][j] = false;
                tmp[i][j+1] = false;
                tmp[i+1][j+1] = false;
            }
        }

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (j != 4) {
                    tmp[i][j] = true;
                    tmp[i][j + 1] = true;
                    blocks.add(toInt(tmp));
                    tmp[i][j] = false;
                    tmp[i][j + 1] = false;
                }
                if (i != 4) {
                    tmp[i][j] = true;
                    tmp[i+1][j] = true;
                    blocks.add(toInt(tmp));
                    tmp[i][j] = false;
                    tmp[i+1][j] = false;
                }
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int cases = Integer.parseInt(sc.nextLine());
        cache = new byte[endFlag + 1];

        for (int i = 0; i < cache.length; i++) {
            cache[i] = 2;
        }

        while (--cases >= 0) {
            boolean[][] board = new boolean[5][5];

            for (int i = 0; i < 5; i++) {
                String line = sc.nextLine().trim();
                for (int j = 0; j < 5; j++) {
                    char c = line.charAt(j);
                    if (c == '#') {
                        board[i][j] = true;
                    }
                }
            }

            if (playGame(toInt(board))) {
                System.out.println("WINNING");
            } else {
                System.out.println("LOSING");
            }
        }
    }

    private static boolean playGame(int intBoard) {
        if (intBoard == endFlag) return false;

        if (cache[intBoard] != 2) {
            return cache[intBoard] == 1;
        }

        boolean result = true;

        for (int block : blocks) {
            if ((intBoard & block) == 0) {
                result = playGame(intBoard | block);
                if (!result) break;
            }
        }

        if (result) {
            cache[intBoard] = 0;
        } else {
            cache[intBoard] = 1;
        }

        return cache[intBoard] == 1;
    }

    private static int toInt(boolean[][] board) {
        int result = 0;

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (board[i][j]) {
                    result = result * 2 + 1;
                } else {
                    result = result * 2;
                }
            }
        }

        return result;
    }
}
