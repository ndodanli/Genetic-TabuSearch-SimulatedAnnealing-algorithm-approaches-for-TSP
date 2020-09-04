package TabuAlgorithm;

import ObjectModels.City;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;

public class TabuAlgorithm {

    public static void runTabuAlgorithm() {

        ArrayList<City> cities = new ArrayList<>();
        Collections.addAll(cities, Node.initCities());

        Solver solver = new Solver(cities);
        int[] path = solver.calculateShortestPath();
        DecimalFormat costFormat = new DecimalFormat("#.##");

        TabuSearch ts = new TabuSearch();
        System.out.println(ts.proceed(cities));

        String summary = "Path: " + cities.get(path[0]).getName();
        for (int i = 1; i < path.length; i++) {
            summary += " -> " + cities.get(path[path.length - i]).getName();
        }
        summary += " -> " + cities.get(path[0]).getName()
                + "\nCost: " + costFormat.format(solver.getBestCost());

    }

}
