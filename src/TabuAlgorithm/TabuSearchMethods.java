package TabuAlgorithm;

import Display.WindowTSP;
import ObjectModels.City;
import java.util.ArrayList;

public class TabuSearchMethods {

    public static double distances[][];
    public static City[] cities = Node.initCities();
    public static City[] drawCity = Node.initCities();
    WindowTSP win = new WindowTSP(cities, "Tabu");

    public TabuSearchMethods(double[][] distances) {
        this.distances = distances;
    }

    public TabuSearchMethods(ArrayList<City> cities) {
        distances = new double[cities.size()][cities.size()];
        for (int i = 0; i < cities.size(); i++) {
            for (int j = 0; j < cities.size(); j++) {
                distances[i][j] = cities.get(i).distance(cities.get(j), cities.get(i));
            }
        }
    }

    public double getPathCost(int solution[], double distances[][]) {
        double cost = 0;

        for (int i = 0; i < solution.length - 1; i++) {
            cost += distances[solution[i]][solution[i + 1]];
        }

        return cost;
    }

    public String prepareSoultion(int[] solution) {
        String solutionString = "";
        for (int i = 0; i < solution.length - 1; i++) {
            solutionString += cities[solution[i]].getName() + " ->";
            drawCity[i] = cities[solution[i]];
        }
        solutionString += cities[solution[solution.length - 1]].getName();
        win.draw(drawCity);
        return solutionString;
    }
}
