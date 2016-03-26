package sk.fiit.stuba.xnovakd1;

import java.util.*;

/**
 * Created by doriot on 3/19/16.
 * A * algorithm
 */
public class Main {

    private static int count;
    private static int stepCount;
    private static boolean found = false;
    private Node initialNode;
    public static int heuristic;

    // attributes specifying the size of matrix
    public static int i;
    public static int j;

    public static int finalState[][];


    public void method(Node node, PriorityQueue<Node> nodePriorityQueue) {
        for (int r = 0; r < i; r++) {
            for (int c = 0; c < j; c++) {
                if (node.startState[r][c] == 0) {
                    if (c - 1 >= 0) { // to the left
                        node.startState[r][c] = node.startState[r][c - 1];
                        node.startState[r][c - 1] = 0;
                        nodePriorityQueue.add(new Node(node.startState, node, Move.LEFT));
                        count++;
                        node.startState[r][c - 1] = node.startState[r][c];
                        node.startState[r][c] = 0;
                    }
                    if (c + 1 <= j - 1) { // to the right
                        node.startState[r][c] = node.startState[r][c + 1];
                        node.startState[r][c + 1] = 0;
                        nodePriorityQueue.add(new Node(node.startState, node, Move.RIGHT));
                        count++;
                        node.startState[r][c + 1] = node.startState[r][c];
                        node.startState[r][c] = 0;
                    }
                    if (r - 1 >= 0) { // up
                        node.startState[r][c] = node.startState[r - 1][c];
                        node.startState[r - 1][c] = 0;
                        nodePriorityQueue.add(new Node(node.startState, node, Move.UP));
                        count++;
                        node.startState[r - 1][c] = node.startState[r][c];
                        node.startState[r][c] = 0;
                    }
                    if (r + 1 <= i - 1) { // down
                        node.startState[r][c] = node.startState[r + 1][c];
                        node.startState[r + 1][c] = 0;
                        nodePriorityQueue.add(new Node(node.startState, node, Move.DOWN));
                        count++;
                        node.startState[r + 1][c] = node.startState[r][c];
                        node.startState[r][c] = 0;
                    }
                }
            }
        }
    }

    private boolean isFinalState(Node node) {
        return (Arrays.deepEquals(node.startState, finalState));
    }

    private void printResults(Node current, Node initialNode, Stack<Node> stack) {
        System.out.println("Algoritmus prebehol uspesne.\nPocet vygenerovanych stavov: " + count);
        System.out.println(Arrays.deepToString(current.startState));
        found = true;
        System.out.println(current.getDepth());

        while (current != initialNode) {
            stack.push(current);
            current = current.getParent();
            stepCount++;
        }
        System.out.println("Pocet krokov: " + stepCount + ".");

        while (!stack.isEmpty()) {
            System.out.println(stack.pop().getMove());
        }
    }

    private void init() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Prosim zadajte rozmery matice:");

        i = scanner.nextInt();
        j = scanner.nextInt();
        finalState = new int[i][j];
        for (int r = 0; r < i; r++) {
            for (int c = 0; c < j; c++) {
                finalState[r][c] = r * j + c + 1;
            }
        }

        finalState[i - 1][j - 1] = 0;

        initialNode = new Node();
        initialNode.setDepth(0);


        System.out.println("Prosim zadajte cislo heuristiky, 1 alebo 2:");

        heuristic = scanner.nextInt();

        System.out.println("Prosim zadajte startovny stav:");

        //loop used to initialize the values of the starting matrix
        for (int r = 0; r < i; r++) {
            for (int c = 0; c < j; c++) {
                initialNode.startState[r][c] = scanner.nextInt();
            }
        }
    }

    public static void main(String args[]) {
        Main main = new Main();

        // here we initialize all the neccessary data for the algorithm
        main.init();

        // comparator is then passed into priorityQueue and tells it how to order nodes
        Comparator<Node> comparator = (o1, o2) -> {
            if (o1.getHeuristic() > o2.getHeuristic()) return 1;
            if (o1.getHeuristic() < o2.getHeuristic()) return -1;
            return 0;
        };

        // HashSet is used in order to prevent us from visiting duplicate nodes
        HashSet<Node> usedNodes = new HashSet<>();

        // Stack is used in order to print operands in correct order once we reach the desired state
        Stack<Node> stack = new Stack<>();

        // according to comparator, priorityQueue keeps our nodes sorted
        PriorityQueue<Node> nodePriorityQueue = new PriorityQueue<>(10, comparator);
        nodePriorityQueue.add(main.initialNode);

        // the algorithm itself
        // While priorityQueue is not empty, we keep cycling through the nodes, or until we find the final node
        while (!nodePriorityQueue.isEmpty()) {

            // the node with the most feasible heuristic is removed from the queue and inserted into "current"
            Node current = nodePriorityQueue.poll();

            // if we already searched through particular node, we discard it and poll another node
            if (!usedNodes.add(current)) continue;

            // in this method we create all nodes possible by moving blank space: left, right, up, down - where possible
            main.method(current, nodePriorityQueue);

            // and finally here we check whether we have found the desired node, if so, we print the results
            if (main.isFinalState(current)) {
                main.printResults(current, main.initialNode, stack);
                break;
            }
        }

        if (!found) {
            System.out.println("Bol prehladany cely stavovy priestor. Riesenie neexistuje.");
        }
    }
}
