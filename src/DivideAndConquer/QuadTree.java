package DivideAndConquer;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class QuadTree {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int cases = sc.nextInt();
        sc.nextLine();

        for (int iter = 0; iter < cases; iter++) {
            String line = sc.nextLine().trim();
            int length = line.length();
            List<Tree> stack = new ArrayList<Tree>();
            Tree last = null;

            if (line.length() == 1) {
                System.out.println(line);
                continue;
            }
            for (char c : line.toCharArray()) {
                if (c == 'x') {
                    stack.add(new Tree(false, 'x'));
                    continue;
                }
                last = stack.get(stack.size() - 1);
                last.addChild(new Tree(true, c));
                while (last.isComplete()) {
                    stack.remove(stack.size() - 1);
                    if (stack.isEmpty()) break;
                    stack.get(stack.size() - 1).addChild(last);
                    last = stack.get(stack.size() - 1);
                }
            }

            System.out.println(last.upSideDown());
        }
    }

    private static class Tree {
        private boolean isLeaf;
        private char bw;
        private List<Tree> children;

        public Tree(boolean isLeaf, char bw) {
            this.isLeaf = isLeaf;
            this.bw = bw;
            this.children = new ArrayList<Tree>(4);
        }

        public boolean isLeaf() {
            return isLeaf;
        }

        public char getBw() {
            return bw;
        }

        public void addChild(Tree t) {
            this.children.add(t);
        }

        public boolean isComplete() {
            if (isLeaf) return true;
            else if (this.children.size() == 4) return true;
            else return false;
        }

        public String upSideDown() {
            if (this.isLeaf()) return String.valueOf(bw);
            else {
                return "x" + this.children.get(2).upSideDown() + this.children.get(3).upSideDown() + this.children.get(0).upSideDown() + this.children.get(1).upSideDown();
            }
        }
    }
}
