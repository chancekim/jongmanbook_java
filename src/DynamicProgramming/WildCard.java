package DynamicProgramming;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class WildCard {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int cases = Integer.parseInt(sc.nextLine());

        while (--cases >= 0) {
            String pattern = sc.nextLine().trim();
            pattern = pattern.replaceAll("\\*", "\\.\\*");
            pattern = pattern.replaceAll("\\?", "\\.{1}");
            pattern = "(^" + pattern + "$)";

            int words = Integer.parseInt(sc.nextLine());
            List<String> matchedWords = new ArrayList<String>();

            while (--words >= 0) {
                String word = sc.nextLine();
                if (word.matches(pattern)) matchedWords.add(word);
            }

            Collections.sort(matchedWords);
            StringBuffer sb = new StringBuffer();

            for (String word : matchedWords) {
                sb.append(word);
                sb.append("\n");
            }

            System.out.print(sb.toString());
        }
    }
}
