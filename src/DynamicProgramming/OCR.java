package DynamicProgramming;

import java.util.*;

public class OCR {
    private static int wordCnt;
    private static String[] words;
    private static Map<String, Integer> wordIndexMap;
    private static double[] atFirst;
    private static double[][] atNext;
    private static double[][] classification;

    private static double[][] cache;
    private static int[][] choice;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        StringTokenizer st = new StringTokenizer(sc.nextLine());

        wordCnt = Integer.parseInt(st.nextToken());
        int sentenceCnt = Integer.parseInt(st.nextToken());

        words = sc.nextLine().trim().split(" ");
        wordIndexMap = new HashMap<String, Integer>();

        for (int i = 0; i < wordCnt; i++) {
            wordIndexMap.put(words[i], i);
        }

        st = new StringTokenizer(sc.nextLine());

        atFirst = new double[wordCnt];

        for (int i = 0; i < wordCnt; i++) {
            atFirst[i] = Math.log(Double.parseDouble(st.nextToken()));
        }

        atNext = new double[wordCnt][wordCnt];

        for (int i = 0; i < wordCnt; i++) {
            st = new StringTokenizer(sc.nextLine());
            for (int j = 0; j < wordCnt; j++) {
                atNext[i][j] = Math.log(Double.parseDouble(st.nextToken()));
            }
        }

        classification = new double[wordCnt][wordCnt];

        for (int i = 0; i < wordCnt; i++) {
            st = new StringTokenizer(sc.nextLine());
            for (int j = 0; j < wordCnt; j++) {
                classification[i][j] = Math.log(Double.parseDouble(st.nextToken()));
            }
        }

        StringBuffer sb = new StringBuffer();

        while (--sentenceCnt >= 0) {
            st = new StringTokenizer(sc.nextLine());
            int cnt = Integer.parseInt(st.nextToken());
            String[] problem = new String[cnt];
            cache = new double[cnt][wordCnt];
            choice = new int[cnt][wordCnt];
            for (int i = 0; i < cnt; i++) {
                problem[i] = st.nextToken();
                Arrays.fill(cache[i], 1.0);
            }

            getBestSentence(problem, 0, -1);
            sb.append(reconstruct(problem.length, 0, 0));
            sb.append("\n");
        }

        System.out.println(sb.toString());
    }

    private static double getBestSentence(String[] problem, int wordAt, int prevWordIndex) {
        if (wordAt == problem.length) {
            return 0.0;
        }

        if (wordAt == 0) {
            String str = problem[0];
            int wordIndex = wordIndexMap.get(str).intValue();

            double maxProb = -1e200;
            int maxIndex = 0;

            for (int i = 0; i < wordCnt; i++) {
                double result = atFirst[i] + classification[i][wordIndex] + getBestSentence(problem, wordAt + 1, i);

                if (result > maxProb) {
                    maxProb = result;
                    maxIndex = i;
                }
            }

            choice[0][0] = maxIndex;

            return maxProb;
        } else {
            String str = problem[wordAt];
            int wordIndex = wordIndexMap.get(str).intValue();

            if (cache[wordAt][prevWordIndex] <= 0.0) return cache[wordAt][prevWordIndex];

            double maxProb = -1e200;
            int maxIndex = 0;

            for (int i = 0; i < wordCnt; i++) {
                double result = atNext[prevWordIndex][i] + classification[i][wordIndex] + getBestSentence(problem, wordAt + 1, i);
                if (result > maxProb) {
                    maxProb = result;
                    maxIndex = i;
                }
            }

            cache[wordAt][prevWordIndex] = maxProb;
            choice[wordAt][prevWordIndex] = maxIndex;

            return maxProb;
        }
    }

    private static String reconstruct(int totalLength, int wordAt, int previousIndex) {
        if (wordAt == totalLength) return "";

        int currentIndex = choice[wordAt][previousIndex];

        return words[currentIndex] + " " + reconstruct(totalLength,wordAt + 1, currentIndex);
    }
}
