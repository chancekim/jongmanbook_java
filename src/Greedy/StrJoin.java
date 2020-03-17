package Greedy;

import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.StringTokenizer;

public class StrJoin {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int cases = Integer.parseInt(sc.nextLine());

        while ((--cases) >= 0) {
            int wordCnt = Integer.parseInt(sc.nextLine());
            PriorityQueue<Integer> pq = new PriorityQueue<>(wordCnt);
            StringTokenizer st = new StringTokenizer(sc.nextLine());

            for (int i = 0; i < wordCnt; i++) {
                pq.offer(Integer.parseInt(st.nextToken()));
            }

            int sum = 0;

            while (pq.size() > 1) {
                int first = pq.poll();
                int second = pq.poll();

                sum += first + second;

                pq.offer(first + second);
            }

            System.out.println(sum);
        }
    }
}
