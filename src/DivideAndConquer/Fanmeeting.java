package DivideAndConquer;

import java.util.Scanner;

public class Fanmeeting {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int cases = sc.nextInt();
        sc.nextLine();

        while (--cases >= 0) {
            char[] members = sc.nextLine().trim().toCharArray();
            char[] fans = sc.nextLine().trim().toCharArray();

            System.out.println(hug(members, fans, 0, fans.length - 1));
        }
    }

    private static int hug(char[] members, char[] fans, int start, int end) {
        if (end - start + 1 < members.length) return 0;

        int mid = (start + end) / 2;

        int left = hug(members, fans, start, mid);
        int right = hug(members, fans, mid + 1, end);
        int result = left + right;

        for (int startIdx = Math.min(mid, end - members.length + 1); startIdx >= start & startIdx + members.length - 1 > mid; startIdx--) {
            if (isEveryoneHug(members, fans, startIdx, startIdx + members.length - 1)) result++;
        }

        return result;
    }

    private static boolean isEveryoneHug(char[] members, char[] fans, int start, int end) {
        boolean result = true;

        for (int i = 0; i < members.length; i++) {
            if (members[i] == 'M' && fans[start + i] == 'M') {
                result = false;
                break;
            }
        }

        return result;
    }
}
