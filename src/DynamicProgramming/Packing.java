package DynamicProgramming;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Packing {
    private static List<Item> items;
    private static int[][] cache;
    private static boolean[][] choices;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int cases = Integer.parseInt(sc.nextLine());

        while (--cases >= 0) {
            StringTokenizer st = new StringTokenizer(sc.nextLine());
            int itemCnt = Integer.parseInt(st.nextToken());
            int capacity = Integer.parseInt(st.nextToken());

            items = new ArrayList<Item>(itemCnt);
            cache = new int[itemCnt + 1][capacity + 1];
            choices = new boolean[itemCnt + 1][capacity + 1];

            for (int i = 0; i < itemCnt; i++) {
                st = new StringTokenizer(sc.nextLine());
                items.add(new Item(st.nextToken(), Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())));
            }

            int max = calcOptimal(0, capacity);

            List<String> names = new ArrayList<String>(itemCnt);
            reconstruct(names, 0, capacity);

            System.out.println(max + " " + names.size());
            names.forEach(System.out::println);
        }
    }

    private static int calcOptimal(int start, int capacity) {
        if (capacity < 0) return -10000;
        if (start == items.size() || capacity == 0) return 0;

        if (cache[start][capacity] != 0) return cache[start][capacity];

        int excluded = calcOptimal(start + 1, capacity);
        int included = items.get(start).priority + calcOptimal(start + 1, capacity - items.get(start).volume);

        if (excluded > included) {
            cache[start][capacity] = excluded;
            choices[start][capacity] = false;
        } else {
            cache[start][capacity] = included;
            choices[start][capacity] = true;
        }

        return cache[start][capacity];
    }

    private static void reconstruct(List<String> names, int start, int capacity) {
        if (start == items.size() || capacity <= 0) return;

        if (choices[start][capacity]) {
            names.add(items.get(start).name);
            reconstruct(names, start + 1, capacity - items.get(start).volume);
        } else {
            reconstruct(names, start + 1, capacity);
        }
    }

    private static class Item {
        public String name;
        public int volume;
        public int priority;

        public Item(String name, int volume, int priority) {
            this.name = name;
            this.volume = volume;
            this.priority = priority;
        }
    }
}
