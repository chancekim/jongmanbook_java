package CombinationalSearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Allergy {
    private static int MIN;

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        int cases = Integer.parseInt(bufferedReader.readLine().trim());

        while (--cases >= 0) {
            StringTokenizer st = new StringTokenizer(bufferedReader.readLine());

            int friendCnt = Integer.parseInt(st.nextToken());
            int foodCnt = Integer.parseInt(st.nextToken());

            HashMap<String,Integer> nameToIdx = new HashMap<String,Integer>();

            int idx = 0;

            st = new StringTokenizer(bufferedReader.readLine());

            for (int i = 0; i < friendCnt; i++) {
                nameToIdx.put(st.nextToken(), idx++);
            }

            long[] foodPerFriend = new long[friendCnt];
            List<Integer>[] friendPerFood = new List[foodCnt];

            for (int i = 0; i < foodCnt; i++) {
                friendPerFood[i] = new ArrayList<Integer>();

                st = new StringTokenizer(bufferedReader.readLine());

                int cnt = Integer.parseInt(st.nextToken());

                for (int j = 0; j < cnt; j++) {
                    int friendIdx = nameToIdx.get(st.nextToken());
                    friendPerFood[i].add(friendIdx);
                }
            }

            Arrays.sort(friendPerFood, (integers, t1) -> -Integer.compare(integers.size(), t1.size()));

            long[] foodCanCover = new long[foodCnt];

            for (int i = 0; i < foodCnt; i++) {
                long cover = 0;
                for (int friendIdx : friendPerFood[i]) {
                    foodPerFriend[friendIdx] |= 1L << i;
                    cover |= 1L << friendIdx;
                }
                foodCanCover[i] = cover;
            }

            MIN = Integer.MAX_VALUE;

            long covered = 0;
            long checker = ~(0x8000000000000000L >> (63 - friendCnt));

            minFoods(foodPerFriend, friendPerFood, foodCanCover, covered, 0, checker);

            System.out.println(MIN);
        }
    }

    private static void minFoods(long[] foodPerFriend, List<Integer>[] friendPerFood, long[] foodCanCover, long covered, int curFoodCnt, long checker) {
        if (curFoodCnt >= MIN) {
            return;
        }

        if (covered == checker) {
            MIN = curFoodCnt;
            return;
        }

        long prevCovered = covered;

        int first = 0;

        for (int i = 0; i < foodPerFriend.length; i++) {
            if ((covered & (1L << i)) == 0L) {
                first = i;
                break;
            }
        }

        for (int j = 0; j < friendPerFood.length; j++) {
            if ((foodPerFriend[first] & (1L << j)) != 0L) {
                covered |= foodCanCover[j];
                minFoods(foodPerFriend, friendPerFood, foodCanCover, covered, curFoodCnt + 1, checker);
                covered = prevCovered;
            }
        }
    }
}
