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
            List<String> candidates = new ArrayList<>(wordCnt);
            words = new ArrayList<>(wordCnt);
            endFlag = 0;

            while (--wordCnt >= 0) {
                candidates.add(sc.nextLine().trim());
            }

            for (int i = 0; i < candidates.size(); i++) {
                boolean isContained = false;
                for (int j = 0; j < candidates.size(); j++) {
                    if (i == j) continue;

                    if (candidates.get(j).contains(candidates.get(i))) {
                        isContained = true;
                        break;
                    }
                }

                if (!isContained) {
                    words.add(candidates.get(i));
                }
            }

            for (int i = 0; i < words.size(); i++) {
                endFlag = endFlag | (0x1 << i);
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
        for (int i = 0; i < prefix.length(); i++) {
            if (postfix.startsWith(prefix.substring(i))) {
                return prefix.substring(0, i) + postfix;
            }
        }

        return prefix + postfix;
    }
}
