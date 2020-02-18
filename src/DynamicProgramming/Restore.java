package DynamicProgramming;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Restore {
    private static List<String> words;
    private static int endFlag;
    private static String[] cache;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int cases = Integer.parseInt(sc.nextLine());

        while (--cases >= 0) {
            int wordCnt = Integer.parseInt(sc.nextLine());
            words = new ArrayList<>(wordCnt);
            endFlag = 0;

            while (--wordCnt >= 0) {
                words.add(sc.nextLine().trim());
                endFlag = (endFlag << 1) | 0x1;
            }

            cache = new String[endFlag + 1];

            System.out.println(getShortest(0x0));
        }
    }

    private static String getShortest(int usedWordFlag) {
        if (usedWordFlag == endFlag) {
            return "";
        }

        if (cache[usedWordFlag] != null) {
            return cache[usedWordFlag];
        }

        String shortest = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";

        for (int i = 0; i < words.size(); i++) {
            if ((usedWordFlag & (0x1 << i)) == 0) {
                String word = words.get(i);
                String merged = merge(word, getShortest((usedWordFlag | (0x1 << i))));

                if (merged.length() < shortest.length()) {
                    shortest = merged;
                }
            }
        }

        cache[usedWordFlag] = shortest;
        return cache[usedWordFlag];
    }

    private static String merge(String prefix, String postfix) {
        if (prefix.contains(postfix)) return prefix;
        if (postfix.contains(prefix)) return postfix;

        for (int i = 0; i < prefix.length(); i++) {
            if (postfix.startsWith(prefix.substring(i))) {
                return prefix.substring(0, i) + postfix;
            }
        }

        return prefix + postfix;
    }
}
