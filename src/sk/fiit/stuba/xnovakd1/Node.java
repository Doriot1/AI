package sk.fiit.stuba.xnovakd1;

import java.util.Arrays;

/**
 * Created by doriot on 3/19/16.
 * halp
 */
public class Node {

    private int heuristic;
    private int depth;
    private Node parent;
    private Move move;

    public int startState[][] = new int[Main.i][Main.j];

    public Node() {
    }

    // in this constructor we set the parent for the node, the operator we used to get here and the depth
    public Node(int[][] startState, Node node, Move move) {
        this.move = move;
        parent = node;
        this.depth = node.depth + 1;

    // according to the selected heuristic, we calculate the heuristic value for given node
        if (Main.heuristic == 1) {
            for (int i = 0; i < Main.i; i++) {
                for (int j = 0; j < Main.j; j++) {
                    this.startState[i][j] = startState[i][j];
                    if (startState[i][j] == j + 1 + i * Main.j) {
                        heuristic--;
                    }
                }
            }
        } else {
            int x, y;
            for (int i = 0; i < Main.i; i++) {
                for (int j = 0; j < Main.j; j++) {
                    this.startState[i][j] = startState[i][j];
                    if(startState[i][j] != 0){
                        x = (startState[i][j] - 1) / Main.i;
                        y = (startState[i][j] - 1) % Main.j;
                        heuristic += Math.abs(i -x) + Math.abs(j - y);
                    }
                }

            }
        }
        for (int i = 1; i <= depth; i += 2) heuristic++;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Node && Arrays.deepEquals(((Node) obj).startState, this.startState);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(startState);
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public int getDepth() {
        return depth;
    }

    public int getHeuristic() {
        return heuristic;
    }

    public Node getParent() {
        return parent;
    }

    public Move getMove() {
        return move;
    }
}
