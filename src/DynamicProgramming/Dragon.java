package DynamicProgramming;

import java.util.Scanner;
import java.util.StringTokenizer;

public class Dragon {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int cases = Integer.parseInt(sc.nextLine());

        while (--cases >= 0) {
            StringTokenizer st = new StringTokenizer(sc.nextLine());
            int n = Integer.parseInt(st.nextToken());
            int start = Integer.parseInt(st.nextToken());
            int length = Integer.parseInt(st.nextToken());

            System.out.println(getDrangonSubString(n, start, length));
        }
    }

    private static String getDrangonSubString(int generation, int start, int length) {
        int quotient = start / 12;
        int remainder = start % 12;

        int end = start + length - 1;

        int endQuotient = end / 12;
        int endRemainder = end % 12;

        String result = "";

        for (int i = quotient; i <= endQuotient; i++) {
            result = result + getNthString(i);
        }

        return result.substring(remainder, (endQuotient - quotient) * 12 + endRemainder + 1);
    }

    private static String getNthString(int n) {
        StringBuffer sb = new StringBuffer();

        sb.append(getSign(4 * n));
        sb.append("FX+YF");
        sb.append(getSign(4 * n + 2));
        sb.append("FX-YF");

        return sb.toString();
    }

    private static String getSign(int n) {
        if (n == 0 || n == 1) return "+";
        if (n % 4 == 1) return "+";
        else if (n % 4 == 3) return "-";

        return getSign(n / 2);
    }
}
