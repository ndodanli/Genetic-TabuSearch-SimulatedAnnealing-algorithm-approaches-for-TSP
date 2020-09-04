package TabuAlgorithm;

import ObjectModels.City;

public class Node {

    public Node parentNode;
    private double parentCost;
    public int index;
    private double[][] distances;
    private int[] activeSet;

    public static City[] initCities() {
        return IO.Import.getCities();
    }

    public Node(Node parent, double parentCost, double[][] distances, int[] activeSet, int index) {
        this.parentNode = parent;
        this.parentCost = parentCost;
        this.distances = distances;
        this.activeSet = activeSet;
        this.index = index;
    }

    public boolean isLeaf() {
        return activeSet.length == 1;
    }

    public Node[] generateChildren() {
        Node[] children = new Node[activeSet.length - 1];

        int[] newSet = new int[activeSet.length - 1];
        int i = 0;
        for (int location : activeSet) {
            if (location == index) {
                continue;
            }

            newSet[i] = location;
            i++;
        }

        for (int j = 0; j < children.length; j++) {
            children[j] = new Node(this, distances[index][newSet[j]], distances, newSet, newSet[j]);
        }
        return children;
    }

    public int[] getPath() {
        int depth = distances.length - activeSet.length + 1;
        int[] path = new int[depth];
        getPathIndex(path, depth - 1);
        return path;
    }

    public void getPathIndex(int[] path, int i) {
        path[i] = index;
        if (parentNode != null) {
            parentNode.getPathIndex(path, i - 1);
        }
    }

    public double calculateLowerBound() {
        double value = 0;

        if (activeSet.length == 2) {
            return getPathCost() + distances[activeSet[0]][activeSet[1]];
        }

        for (int point : activeSet) {
            double min1 = Double.MAX_VALUE;
            double min2 = Double.MAX_VALUE;

            for (int otherPoint : activeSet) {
                if (otherPoint == point) {
                    continue;
                }

                double cost = distances[point][otherPoint];
                if (cost < min1) {
                    min2 = min1;
                    min1 = cost;
                } else if (cost < min2) {
                    min2 = cost;
                }
            }

            value += min1 + min2;
        }

        return getParentCost() + value / 2;
    }

    public double getPathCost() {
        return distances[0][index] + getParentCost();
    }

    public double getParentCost() {
        if (parentNode == null) {
            return 0;
        }

        return parentCost + parentNode.getParentCost();
    }
}
