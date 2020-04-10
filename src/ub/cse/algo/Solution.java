package ub.cse.algo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Stack;

public class Solution {
    
    private int _startNode;
    private int _endNode;
    private HashMap<Integer, ArrayList<Integer>> graph;
    public Solution(int startNode, int endNode, HashMap<Integer, ArrayList<Integer>> g){
        _startNode = startNode;
        _endNode = endNode;
        graph = g;
    }
    
    public ArrayList<Integer> outputPath(){
        /*
         * Find the smallest weighted path between _startNode and _endNode
         * The first number of graph's adjacency list is the weight of it's node
         */

        ArrayList<Integer> path = new ArrayList<>();

        int size = this.graph.size();

        int[] discovered = new int[size];
        discovered[this._startNode] = 1;
        int currentNode = this._startNode;

        Stack<Integer> stack = new Stack<>();
        stack.push(currentNode);
        boolean found = false;

        while (!stack.empty()) {

            ArrayList<Integer> adjacentNodes = this.graph.get(currentNode);

            int min = 51;
            int index = -1;

            // Find the endNode or pick the smallest weighted node
            for (int i = 1; i < adjacentNodes.size(); i++) {
                int adjNode = adjacentNodes.get(i);

                if (discovered[adjNode] != 1) {
                    if (adjNode == this._endNode) {
                        index = i;
                        found = true;
                        break;
                    }

                    int addWeight = this.graph.get(adjNode).get(0);
                    if (addWeight < min) {
                        min = addWeight;
                        index = i;
                    }
                }
            }

            if (index == -1) {
                currentNode = stack.pop();
                continue;
            }

            int adjNode =  adjacentNodes.get(index);
            currentNode = adjNode;
            stack.push(currentNode);
            discovered[adjNode] = 1;

            if (found) {
                break;
            }
        }

        if (!stack.empty()) {
            while (!stack.empty()) {
                path.add(stack.pop());
            }
        }

        return path;
    }
}

