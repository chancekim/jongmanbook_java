package Greedy;

import java.util.*;

public class LunchBox {
    private static List<Pair> participants;

    private static class Pair implements Comparable<Pair> {
        public int microWaveTime;
        public int eatTime;

        public Pair(int microWaveTime, int eatTime) {
            this.microWaveTime = microWaveTime;
            this.eatTime = eatTime;
        }

        @Override
        public int compareTo(Pair pair) {
            return this.eatTime - pair.eatTime;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int cases = Integer.parseInt(sc.nextLine());

        while (--cases >= 0) {
            int participantCnt = Integer.parseInt(sc.nextLine());

            participants = new ArrayList<Pair>(participantCnt);

            StringTokenizer microWaveSt = new StringTokenizer(sc.nextLine());
            StringTokenizer eatSt = new StringTokenizer(sc.nextLine());

            for (int i = 0; i < participantCnt; i++) {
                participants.add(new Pair(Integer.parseInt(microWaveSt.nextToken()), Integer.parseInt(eatSt.nextToken())));
            }

            System.out.println(getShortestTime());
        }
    }

    private static int getShortestTime() {
        Collections.sort(participants);

        int totalMicroWave = 0;
        int max = 0;

        for (int i = participants.size() - 1; i >= 0; i--) {
            totalMicroWave += participants.get(i).microWaveTime;
            max = Math.max(totalMicroWave + participants.get(i).eatTime, max);
        }

        return max;
    }
}
