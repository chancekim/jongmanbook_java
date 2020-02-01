package DivideAndConquer;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Fence {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int cases = sc.nextInt();

        for (int c = 0; c < cases; c++) {
            int fenceCount = sc.nextInt();
            List<Integer> fences = new ArrayList<Integer>(fenceCount);

            for (int i = 0; i < fenceCount; i++) fences.add(sc.nextInt());

            System.out.println(maxRect(fences, 0, fences.size() - 1));
        }
    }

    private static int maxRect(List<Integer> fences, int start, int end) {
        if (start == end) return fences.get(start);

        int mid = (start + end) / 2;

        int left = maxRect(fences, start, mid);
        int right = maxRect(fences, mid + 1, end);

        int maximum = Math.max(left, right);

        int s = mid;
        int e = mid;
        int shortest = fences.get(mid);

        while (true) {
            if (s - 1 >= start && e + 1 <= end) {
                if (fences.get(s - 1) > fences.get(e + 1)) {
                    s--;
                    shortest = Math.min(shortest, fences.get(s));
                }
                else {
                    e++;
                    shortest = Math.min(shortest, fences.get(e));
                }
            } else if (s - 1 >= start) {
                s--;
                shortest = Math.min(shortest, fences.get(s));
            } else if (e + 1 <= end) {
                e++;
                shortest = Math.min(shortest, fences.get(e));
            } else {
                break;
            }
            maximum = Math.max(maximum, (e - s + 1) * shortest);
        }

        return maximum;
    }
}
