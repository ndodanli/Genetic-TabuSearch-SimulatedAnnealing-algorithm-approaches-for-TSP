package ObjectModels;

import java.util.Arrays;
import java.util.Random;
/**
 *
 * @author emcs
 */
public class Chromosome implements Comparable<Chromosome> {

    private City[] cities;
    private int distance = -1;
    private Random random;

    public Chromosome(City[] cities) {
        this.cities = cities.clone();
    }

    public Chromosome(City[] cities, Random random) {
        this.cities = cities.clone();
        this.random = random;
        shuffle();
    }

    private void shuffle() {
        for (int i = 0; i < cities.length; i++) {
            swap(i, random.nextInt(cities.length));
        }
    }

    private void swap(int i, int j) {
        City temp = cities[i];
        cities[i] = cities[j];
        cities[j] = temp;
    }

    public City[] getArray() {
        return cities.clone();
    }

    @Override
    public int compareTo(Chromosome chromosome) {
        return getDistance() - chromosome.getDistance();
    }

    @Override
    public int hashCode() {
        StringBuilder sb = new StringBuilder();
        for (City city : cities) {
            sb.append(city);
        }
        return (new String(sb)).hashCode();
    }

    public int getDistance() {

        if (distance != -1) {
            return distance;
        }

        double distanceTravelled = 0;

        for (int i = 1; i < cities.length; i++) {
            distanceTravelled += City.distance(cities[i - 1], cities[i]);
        }

        distanceTravelled += City.distance(cities[cities.length - 1], cities[0]);
        this.distance = (int) distanceTravelled;
        return distance;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Chromosome)) {
            return false;
        }

        Chromosome c = (Chromosome) o;

        return Arrays.equals(c.cities, cities);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[ ");
        for (City item : cities) {
            sb.append(item.getName());
            sb.append(" ");
        }
        sb.append("]");
        return new String(sb);
    }
}
