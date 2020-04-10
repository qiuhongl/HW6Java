package ub.cse.algo;

import java.util.*;

public class Solution {
    
    private int _startNode;
    private int _endNode;
    private HashMap<Integer, ArrayList<Integer>> graph;
    public Solution(int startNode, int endNode, HashMap<Integer, ArrayList<Integer>> g){
        _startNode = startNode;
        _endNode = endNode;
        graph = g;
    }

    /**
     * Find the smallest weighted path between _startNode and _endNode
     * The first number of graph's adjacency list is the weight of it's node
     */
    public ArrayList<Integer> outputPath(){

        Stack<Integer> pathStack = new Stack<>();
        ArrayList<Integer> path = new ArrayList<>();
        int size = this.graph.size();

        // Keep track of the status of each node
        int[] prev = new int[size];           // the previous node of the given node
        int[] weight = new int[size];         // the weight of the given node
        int[] discovered = new int[size];

        /* The container of nodes ready to be explored later
         * PriorityQueue gives the ability to sort the element when it is inserted
         * Add method only takes O(log(n)) time
         */
        PriorityQueue<Integer> unexplored = new PriorityQueue<>(Comparator.comparingInt(node -> weight[node]));

        // Initialize the status of each node
        for (int node : this.graph.keySet()) {
            prev[node] = -1;
            weight[node] = this.graph.get(node).get(0);
        }

        // Begin with the starting node
        int currentNode = this._startNode;
        weight[currentNode] = 0;
        discovered[currentNode] = 1;

        // All adjacent nodes of the starting node is push into the queue
        ArrayList<Integer> adjacentNodes = this.graph.get(currentNode);
        for (int i = 1; i < adjacentNodes.size(); i++) {
            int adjNode = adjacentNodes.get(i);
            prev[adjNode] = currentNode;
            weight[adjNode] += weight[currentNode];
            unexplored.add(adjNode);
            discovered[adjNode] = 1;
        }

        // Dijkstra's Algorithm modified to this problem
        while (!unexplored.isEmpty()) {

            // Pick and remove the node with the smallest weight(or distance away) from the starting node
            /* (PriorityQueue.poll method gives us the head of the queue, which is the node with the smallest weight,
             * and runs in O(log(n)) time) */
            currentNode = unexplored.poll();
            adjacentNodes = this.graph.get(currentNode);

            for (int i = 1; i < adjacentNodes.size(); i++) {
                int adjNode = adjacentNodes.get(i);

                /* If undiscovered, update the status of nodes
                 */
                if (discovered[adjNode] == 0) {
                    prev[adjNode] = currentNode;
                    weight[adjNode] += weight[currentNode];
                    unexplored.add(adjNode);
                    discovered[adjNode] = 1;
                }

                /* If discovered before, compare the accumulative weights given by two different parent nodes
                 * That is, the current parent and the previous parent
                 * Swap when the weight with the current parent node is less than the weight with the previous one
                 */
                else {
                    int before = weight[adjNode];
                    int after = weight[adjNode] + weight[currentNode];
                    if (after < before) {
                        prev[adjNode] = currentNode;
                        weight[adjNode] = after;
                    }
                }
            }
        }

        /* The path we get is reverse from the expected result
         * Use the stack to reverse the path and compute the output
         */
        if (prev[this._endNode] != -1) {
            int node = this._endNode;
            pathStack.push(node);
            while (prev[node] != -1) {
                pathStack.push(prev[node]);
                node = prev[node];
            }
        }
        while (!pathStack.isEmpty()) {
            path.add(pathStack.pop());
        }

        return path;
    }
}

