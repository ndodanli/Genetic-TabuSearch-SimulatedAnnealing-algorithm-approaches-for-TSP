package TabuAlgorithm;

import ObjectModels.City;
import java.util.ArrayList;
import java.util.HashSet;

public class Solver {

    double[][] distances;
    double bestCost;
    int[] bestPath;

    public double getBestCost() {
        return bestCost;
    }

    public Solver(ArrayList<City> cities) {
        distances = new double[cities.size()][cities.size()];
        for (int i = 0; i < cities.size(); i++) {
            for (int j = 0; j < cities.size(); j++) {
                distances[i][j] = cities.get(i).distance(cities.get(j), cities.get(j));
            }
        }
    }

    public Solver(double[][] distances) {
        this.distances = distances;
    }

    public int[] calculateShortestPath() {
        HashSet<Integer> locationSet = new HashSet<Integer>(distances.length);
        System.out.println("Initializing...");
        for (int i = 0; i < distances.length; i++) {
            locationSet.add(i);
        }

        bestCost = Double.MAX_VALUE;

        int[] activeSet = new int[distances.length];
        for (int i = 0; i < activeSet.length; i++) {
            activeSet[i] = i;
        }

        Node root = new Node(null, 0, distances, activeSet, 0);
        traverse(root);

        return bestPath;
    }

    private void traverse(Node parent) {
        Node[] children = parent.generateChildren();

        for (Node child : children) {
            if (child.isLeaf()) {
                double cost = child.getPathCost();
                if (cost < bestCost) {
                    bestCost = cost;
                    bestPath = child.getPath();
                }
            } else if (child.calculateLowerBound() <= bestCost) {
                traverse(child);
            }
        }
    }

}
