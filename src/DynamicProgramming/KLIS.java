package DynamicProgramming;

import java.util.*;

public class KLIS {
    private static int[] sequence;
    private static long[] countCache;
    private static long[] lengthCache;
    private static long target;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int cases = Integer.parseInt(sc.nextLine());

        while (--cases >= 0) {
            StringTokenizer st = new StringTokenizer(sc.nextLine());

            int elementCnt = Integer.parseInt(st.nextToken());
            target = Long.parseLong(st.nextToken());

            countCache = new long[elementCnt];
            lengthCache = new long[elementCnt];

            sequence = new int[elementCnt];

            st = new StringTokenizer(sc.nextLine());
            for (int i = 0; i < elementCnt; i++) {
                sequence[i] = Integer.parseInt(st.nextToken());
            }

            for (int i = 0; i < elementCnt; i++) {
                calcLis(i);
            }

            String result = getKthLis(0, -1, target);
            String[] elements = result.split(" ");
            System.out.println(elements.length);
            System.out.println(result);
        }
    }

    private static String getKthLis(int start, int largerThan, long k) {
        if (start == sequence.length) return "";

        long max = 0;

        for (int i = start; i < sequence.length; i++) {
            if (sequence[i] > largerThan && lengthCache[i] > max) max = lengthCache[i];
        }

        List<Element> candidates = new ArrayList<>(sequence.length);
        for (int i = start; i < sequence.length; i++) {
            if (sequence[i] > largerThan && lengthCache[i] == max) {
                candidates.add(new Element(sequence[i], i));
            }
        }

        candidates.sort(Comparator.comparingInt(element -> element.value));

        for (Element e : candidates) {
            if (countCache[e.index] >= k) {
               return e.value + " " + getKthLis(e.index + 1, e.value, k);
            } else {
                k -= countCache[e.index];
            }
        }

        return getKthLis(start + 1, largerThan, k);
    }

    private static void calcLis(int start) {
        if (countCache[start] != 0) return;

        long max = 0;
        for (int i = start + 1; i < sequence.length; i++) {
            if (sequence[start] < sequence[i]) {
                calcLis(i);
                if (lengthCache[i] > max) {
                    max = lengthCache[i];
                }
            }
        }

        long cnt = 0;
        for (int i = start + 1; i < sequence.length; i++) {
            if (sequence[start] < sequence[i] && lengthCache[i] == max) {
                cnt = Math.min(target, cnt + countCache[i]);
            }
        }

        lengthCache[start] = max + 1;
        countCache[start] = cnt == 0 ? 1 : cnt;
    }

    private static class Element {
        public int value;
        public int index;

        public Element(int value, int index) {
            this.value = value;
            this.index = index;
        }
    }
}
