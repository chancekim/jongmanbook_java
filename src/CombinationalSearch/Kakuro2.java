package CombinationalSearch;

import java.io.*;
import java.util.*;

public class Kakuro2 {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

        int cases = Integer.parseInt(reader.readLine().trim());

        while (--cases >= 0) {
            int size = Integer.parseInt(reader.readLine().trim());

            int[][] map = new int[size][size];

            for (int i =0; i < size; i++) {
                StringTokenizer st = new StringTokenizer(reader.readLine().trim());

                for (int j = 0; j < size; j++) {
                    map[i][j] = Integer.parseInt(st.nextToken());
                }
            }

            int hintCnt = Integer.parseInt(reader.readLine().trim());
            Map<Hint,Hint> hints = new HashMap<Hint,Hint>();
            Hint[][] verticalHintMap = new Hint[size][size];
            Hint[][] horizontalHintMap = new Hint[size][size];

            for (int i = 0; i < hintCnt; i++) {
                StringTokenizer st = new StringTokenizer(reader.readLine());

                int y = Integer.parseInt(st.nextToken()) - 1;
                int x = Integer.parseInt(st.nextToken()) - 1;
                int direction = Integer.parseInt(st.nextToken());
                int sum = Integer.parseInt(st.nextToken());
                int cnt;

                if (direction == 0) {
                    int movingX = x + 1;
                    while (movingX < size && map[y][movingX] == 1) {
                        horizontalHintMap[y][movingX] = new Hint(x, y, false);
                        movingX++;
                    }
                    cnt = movingX - (x + 1);
                } else {
                    int movingY = y + 1;
                    while (movingY < size && map[movingY][x] == 1) {
                        verticalHintMap[movingY][x] = new Hint(x, y, true);
                        movingY++;
                    }
                    cnt = movingY - (y + 1);
                }

                Hint hint = new Hint(x, y, sum, cnt, direction == 1);
                hints.put(hint, hint);
            }

            int[][] solution = new int[size][size];
            solve(size, map, hints, verticalHintMap, horizontalHintMap, solution);

            for (int y = 0; y < size; y++) {
                StringBuilder sb = new StringBuilder();
                for (int x = 0; x < size; x++) {
                    sb.append(solution[y][x]);
                    sb.append(' ');
                }
                writer.write(sb.toString());
                writer.newLine();
            }

            writer.flush();
        }
    }

    private static boolean solve(int size, int[][] map, Map<Hint,Hint> hints, Hint[][] verticalHintMap, Hint[][] horizontalHintMap, int[][] solution) {
        int x = -1, y = -1;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (map[i][j] == 1) {
                    x = j;
                    y = i;
                    break;
                }
            }
            if (x != - 1) break;
        }

        if (x == -1) {
            return true;
        }

        Hint verticalHint = hints.get(verticalHintMap[y][x]);
        Hint horizontalHint = hints.get(horizontalHintMap[y][x]);

        map[y][x] = 0;
        for (int i = 1; i < 10; i++) {
            if (!verticalHint.used[i] && !horizontalHint.used[i]) {
                use(verticalHint, horizontalHint, i);
                if ((verticalHint.curCnt != verticalHint.cnt || verticalHint.curSum == verticalHint.sum)
                        && (horizontalHint.curCnt != horizontalHint.cnt || horizontalHint.curSum == horizontalHint.sum)) {
                    if (!(verticalHint.curSum > verticalHint.sum || horizontalHint.curSum > horizontalHint.sum
                            || verticalHint.curSum + 9 * (verticalHint.cnt - verticalHint.curCnt) < verticalHint.sum
                            || horizontalHint.curSum + 9 * (horizontalHint.cnt - horizontalHint.curCnt) < horizontalHint.sum
                            || verticalHint.cnt - verticalHint.curCnt > verticalHint.sum - verticalHint.curSum
                            || horizontalHint.cnt - horizontalHint.curCnt > horizontalHint.sum - horizontalHint.curSum )) {
                        solution[y][x] = i;
                        if (solve(size, map, hints, verticalHintMap, horizontalHintMap, solution)) {
                            return true;
                        }
                        solution[y][x] = 0;
                    }
                }
                unuse(verticalHint, horizontalHint, i);
            }
        }
        map[y][x] = 1;

        return false;
    }

    private static void use(Hint verticalHint, Hint horizontalHint, int i) {
        verticalHint.used[i] = true;
        horizontalHint.used[i] = true;
        verticalHint.curCnt++;
        horizontalHint.curCnt++;
        verticalHint.curSum += i;
        horizontalHint.curSum += i;
    }

    private static void unuse(Hint verticalHint, Hint horizontalHint, int i) {
        verticalHint.used[i] = false;
        horizontalHint.used[i] = false;
        verticalHint.curCnt--;
        horizontalHint.curCnt--;
        verticalHint.curSum -= i;
        horizontalHint.curSum -= i;
    }

    private static class Hint {
        int x;
        int y;
        int sum;
        int cnt;
        int curSum;
        int curCnt;
        boolean vertical;
        boolean[] used;

        public Hint(int x, int y, int sum, int cnt, boolean isVertical) {
            this.x = x;
            this.y = y;
            this.sum = sum;
            this.cnt = cnt;
            this.vertical = isVertical;
            this.curSum = 0;
            this.curCnt = 0;
            this.used = new boolean[10];
        }

        public Hint(int x, int y, boolean isVertical) {
            this.x = x;
            this.y = y;
            this.vertical = isVertical;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Hint)) {
                return false;
            }

            Hint p = (Hint) o;

            return x == p.x && y == p.y && vertical == p.vertical;
        }

        @Override
        public int hashCode() {
            int sign = vertical ? 1 : -1;
            return (100 * x + y) * sign;
        }
    }
}
