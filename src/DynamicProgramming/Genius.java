package DynamicProgramming;

import java.util.Arrays;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Genius {
    private static int songCnt;
    private static int targetMinute;
    private static int favoriteCnt;

    private static int[] songLengths;
    private static double[][] matrix;
    private static int[] favorites;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int cases = Integer.parseInt(sc.nextLine());

        while (--cases >= 0) {
            StringTokenizer st = new StringTokenizer(sc.nextLine().trim());

            songCnt = Integer.parseInt(st.nextToken());
            targetMinute = Integer.parseInt(st.nextToken());
            favoriteCnt = Integer.parseInt(st.nextToken());

            songLengths = new int[songCnt];

            st = new StringTokenizer(sc.nextLine().trim());
            int maxLength = 0;
            for (int i = 0; i < songCnt; i++) {
                songLengths[i] = Integer.parseInt(st.nextToken());
                maxLength = Math.max(maxLength, songLengths[i]);
            }

            matrix = new double[songCnt][songCnt];
            for (int i = 0; i < songCnt; i++) {
                st = new StringTokenizer(sc.nextLine().trim());
                for (int j = 0; j < songCnt; j++) {
                    matrix[i][j] = Double.parseDouble(st.nextToken());
                }
            }

            favorites = new int[favoriteCnt];
            st = new StringTokenizer(sc.nextLine().trim());
            for (int i = 0; i < favoriteCnt; i++) {
                favorites[i] = Integer.parseInt(st.nextToken());
            }

            System.out.println(solve());
        }
    }

    private static String solve() {
        double[][] magicMatrix = new double[4 * songCnt][4 * songCnt];

        for (int i = songCnt; i < songCnt * 4; i++) {
            magicMatrix[i][i - songCnt] = 1.0;
        }

        for (int i = 0; i < songCnt; i++) {
            for (int j = 0; j < songCnt; j++) {
                magicMatrix[i][(songLengths[j] - 1) * songCnt + j] = matrix[j][i];
            }
        }

        double[][] baseMatrix = new double[4 * songCnt][1];
        baseMatrix[0][0] = 1.0;

        double[][] resultMatrix = multiply(pow(magicMatrix, targetMinute), baseMatrix);

        double[] results = new double[favorites.length];
        for (int i = 0; i < favorites.length; i++) {
            double result = 0.0;
            for (int j = 0; j < songLengths[favorites[i]]; j++) {
                result += resultMatrix[j * songCnt + favorites[i]][0];
            }
            results[i] = result;
        }

        return Arrays.stream(results).mapToObj(String::valueOf).reduce("", (x, y) -> x + " " + y);
    }

    private static double[][] pow(double[][] matrix, int n) {
        if (n == 0) {
            double[][] identity = new double[matrix.length][matrix.length];

            for (int i = 0; i < matrix.length; i++) {
                identity[i][i] = 1.0;
            }

            return identity;
        }

        int half = n / 2;

        double[][] halfPow = pow(matrix, half);
        double[][] halfSquare = multiply(halfPow, halfPow);

        return n % 2 == 1 ? multiply(halfSquare, matrix) : halfSquare;
    }

    private static double[][] multiply(double[][] matrixA, double[][] matrixB) {
        double[][] result = new double[matrixA.length][matrixB[0].length];

        for (int i = 0; i < matrixA.length; i++) {
            for (int j = 0; j < matrixB[0].length; j++) {
                for (int k = 0; k < matrixA[0].length; k++) {
                    result[i][j] += matrixA[i][k] * matrixB[k][j];
                }
            }
        }

        return result;
    }
}
