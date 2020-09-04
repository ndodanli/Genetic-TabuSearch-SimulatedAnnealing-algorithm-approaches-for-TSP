package SimulatedAnealingAlgorithm;

import ObjectModels.City;
/**
 *
 * @author emcs
 */
public class TourManager {

    public static City[] cities;

    public static void init() {
        cities = IO.Import.getCities();
    }

    public static City getCity(int index) {
        return cities[index];
    }

    public static City[] getAllCities() {
        return cities;
    }

    public static int numberOfCities() {
        return cities.length;
    }

}
