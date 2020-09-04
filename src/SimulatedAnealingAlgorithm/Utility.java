package SimulatedAnealingAlgorithm;

import ObjectModels.City;
import java.util.Random;
/**
 *
 * @author emcs
 */
public class Utility {

    public static double distance(City city1, City city2) {
        int xDistance = Math.abs(city1.getX() - city2.getX());
        int yDistance = Math.abs(city1.getY() - city2.getY());
        double distance = Math.sqrt((xDistance * xDistance) + (yDistance * yDistance));

        return distance;
    }

    public static double acceptanceProbability(int currentDistance, int newDistance, double temperature) {

        if (newDistance < currentDistance) {
            return 1.0;
        }

        return Math.exp((currentDistance - newDistance) / temperature);
    }

    static double randomDouble() {
        Random r = new Random();
        return r.nextInt(1000) / 1000.0;
    }

    public static int randomInt(int min, int max) {
        Random r = new Random();
        double d = min + r.nextDouble() * (max - min);
        return (int) d;
    }
}
