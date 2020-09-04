package TabuAlgorithm;

import ObjectModels.City;
import java.util.ArrayList;

public class TabuSearch {

    public static TabuSearchMethods methodSet;

    public static int[] getBestNeighbour(TabuTab tabuTab,
            int[] initSolution) {

        int[] bestSolution = new int[initSolution.length];
        System.arraycopy(initSolution, 0, bestSolution, 0, bestSolution.length);
        double bestCost = methodSet.getPathCost(initSolution, TabuSearchMethods.distances);
        int city1 = 0;
        int city2 = 0;
        boolean firstNeighbor = true;

        for (int i = 1; i < bestSolution.length - 1; i++) {
            for (int j = 2; j < bestSolution.length - 1; j++) {
                if (i == j) {
                    continue;
                }

                int[] newBestSolution = new int[bestSolution.length];
                System.arraycopy(bestSolution, 0, newBestSolution, 0, newBestSolution.length);

                newBestSolution = swapCities(i, j, initSolution);

                double newBestCost = methodSet.getPathCost(newBestSolution, TabuSearchMethods.distances);
                System.out.println(methodSet.prepareSoultion(newBestSolution));

                System.out.println(newBestCost);

                if ((newBestCost < bestCost || firstNeighbor) && tabuTab.tabuTabTab[i][j] == 0) {
                    firstNeighbor = false;
                    city1 = i;
                    city2 = j;
                    System.arraycopy(newBestSolution, 0, bestSolution, 0, newBestSolution.length);
                    bestCost = newBestCost;
                }
                if (city1 != 0) {
                    tabuTab.decrementTabu();
                    tabuTab.tabuMove(city1, city2);
                }
            }
        }

        return bestSolution;

    }

    public static int[] swapCities(int city1, int city2, int[] solution) {
        int temp = solution[city1];
        solution[city1] = solution[city2];
        solution[city2] = temp;
        return solution;
    }

    public static String proceed(ArrayList<City> cities) {
        methodSet = new TabuSearchMethods(cities);
        int currentSolution[] = new int[cities.size() + 1];
        currentSolution[0] = 0;
        for (int i = 1; i < cities.size(); i++) {
            currentSolution[i] = i;
        }
        currentSolution[cities.size()] = 0;

        int tabuLength = cities.size();
        int amountOfIterations = 100;

        TabuTab tabuTab = new TabuTab(tabuLength);

        int[] bestSolution = new int[currentSolution.length];
        System.arraycopy(currentSolution, 0, bestSolution, 0, tabuLength);
        double bestCost = methodSet.getPathCost(bestSolution, TabuSearchMethods.distances);

        for (int i = 0; i < amountOfIterations; i++) {

            currentSolution = getBestNeighbour(tabuTab, currentSolution);

            double currentCost = methodSet.getPathCost(currentSolution, TabuSearchMethods.distances);

            if (currentCost < bestCost) {
                System.arraycopy(currentSolution, 0, bestSolution, 0, bestSolution.length);
                bestCost = currentCost;
            }
        }

        return methodSet.prepareSoultion(bestSolution) + "\n" + "Cost: " + bestCost;
    }

    public static String proceedForTab(double[][] distances) {
        methodSet = new TabuSearchMethods(distances);
        int currentSolution[] = new int[distances.length + 1];
        currentSolution[0] = 0;
        for (int i = 1; i < distances.length; i++) {
            currentSolution[i] = i;
        }
        currentSolution[distances.length] = 0;

        int tabuLength = distances.length;
        int amountOfIterations = 500;

        TabuTab tabuTab = new TabuTab(tabuLength);

        int[] bestSolution = new int[currentSolution.length];
        System.arraycopy(currentSolution, 0, bestSolution, 0, tabuLength);
        double bestCost = methodSet.getPathCost(bestSolution, TabuSearchMethods.distances);

        for (int i = 0; i < amountOfIterations; i++) {

            currentSolution = getBestNeighbour(tabuTab, currentSolution);

            double currentCost = methodSet.getPathCost(currentSolution, TabuSearchMethods.distances);

            if (currentCost < bestCost) {
                System.arraycopy(currentSolution, 0, bestSolution, 0, bestSolution.length);
                bestCost = currentCost;
            }
        }

        return methodSet.prepareSoultion(bestSolution) + "\n" + "Cost: " + bestCost;
    }
}
