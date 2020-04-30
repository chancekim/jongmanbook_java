package CombinationalSearch;

import java.util.*;

/*
 * 타임아웃 발생
 * 책의 답안과 같은 방식으로 구현해도 타임아웃이 발생하는 것으로 보아 자바로는 구현이 어려워 보임
 */
public class BoardCover2 {
    private static class Pair {
        int x;
        int y;

        Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object object) {
            if (!(object instanceof Pair)) {
                return false;
            }

            Pair p2 = (Pair) object;

            return this.x == p2.x && this.y == p2.y;
        }

        @Override
        public int hashCode() {
            return this.x + 1000 + this.y;
        }
    }

    private static int MAX;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder output = new StringBuilder();

        int cases = Integer.parseInt(scanner.nextLine().trim());

        while (--cases >= 0) {
            String line = scanner.nextLine();

            StringTokenizer st = new StringTokenizer(line);

            MAX = 0;

            int height = Integer.parseInt(st.nextToken());
            int width = Integer.parseInt(st.nextToken());
            int r = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());

            boolean[][] map = new boolean[height][];

            for (int i = 0; i < height; i++) {
                int j = 0;
                map[i] = new boolean[width];
                for (char cell : scanner.nextLine().trim().toCharArray()) {
                    map[i][j] = cell == '#';
                    j++;
                }
            }

            char[][] block = new char[r][];

            for (int i = 0; i < r; i++) {
                block[i] = scanner.nextLine().trim().toCharArray();
            }

            Set<List<Pair>> rotates = new HashSet<List<Pair>>();

            int blockSize = 0;

            for (int i = 0; i < 4; i++) {
                List<Pair> rotated = new ArrayList<Pair>();
                int originX = -1, originY = -1;
                for (int y = 0; y < block.length; y++) {
                    for (int x = 0; x < block[0].length; x++) {
                        if (block[y][x] == '#') {
                            if (i == 0) blockSize++;
                            if (originY == -1) {
                                originX = x;
                                originY = y;
                            }
                            rotated.add(new Pair(x - originX, y - originY));
                        }
                    }
                }
                rotates.add(rotated);
                block = rotate90(block);
            }

            maxBlocks(map, rotates, blockSize, 0, 0, 0);

            output.append(MAX);
            output.append("\n");
        }

        System.out.println(output.toString());
    }

    private static char[][] rotate90(char[][] origin) {
        char[][] rotated = new char[origin[0].length][origin.length];

        for (int i = 0; i < origin[0].length; i++) {
            for (int j = origin.length - 1; j >= 0; j--) {
                rotated[i][origin.length - 1 - j] = origin[j][i];
            }
        }

        return rotated;
    }

    private static void maxBlocks(boolean[][] map, Set<List<Pair>> rotates, int blockSize, int curY, int curX, int curBlocks) {
        if (curY >= map.length) {
            MAX = Math.max(MAX, curBlocks);
            return;
        } else if (curX >= map[0].length) {
            maxBlocks(map, rotates, blockSize, curY + 1, 0, curBlocks);
            return;
        }

        int spaces = 0;

        for (int x = curX; x < map[0].length; x++) {
            if (!map[curY][x]) spaces++;
        }

        for (int y = curY + 1; y < map.length; y++) {
            for (int x = 0; x < map[0].length; x++) {
                if (!map[y][x]) spaces++;
            }
        }

        if (spaces / blockSize + curBlocks <= MAX) {
            return;
        }

        if (map[curY][curX]) {
            maxBlocks(map, rotates, blockSize, curY, curX + 1, curBlocks);
            return;
        }

        for (List<Pair> block : rotates) {
            if (cover(map, block, curY, curX)) {
                maxBlocks(map, rotates, blockSize, curY, curX + 1, curBlocks + 1);
                uncover(map, block, curY, curX);
            }
        }

        maxBlocks(map, rotates, blockSize, curY, curX + 1, curBlocks);
    }

    private static boolean cover(boolean[][] map, List<Pair> block, int curY, int curX) {
        for (Pair cordinate : block) {
            if (curY + cordinate.y >= map.length || curX + cordinate.x >= map[0].length ||
                    curY + cordinate.y < 0 || curX + cordinate.x < 0 || map[curY + cordinate.y][curX + cordinate.x]) {
                return false;
            }
        }

        for (Pair cordinate : block) {
            map[curY + cordinate.y][curX + cordinate.x] = true;
        }

        return true;
    }

    private static void uncover(boolean[][] map, List<Pair> block, int curY, int curX) {
        for (Pair cordinate : block) {
            map[curY + cordinate.y][curX + cordinate.x] = false;
        }
    }
}
