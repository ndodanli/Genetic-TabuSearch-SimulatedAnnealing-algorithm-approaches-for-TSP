package ObjectModels;

import java.nio.BufferOverflowException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Random;
/**
 *
 * @author emcs
 */
public class Population implements Iterable<Chromosome> {

    private PriorityQueue<Chromosome> chromosomes;
    private int maxSize;

    public Population(int maxSize) {
        this.maxSize = maxSize;
        chromosomes = new PriorityQueue<>();
    }

    public void add(Chromosome chromosome) {
        if (chromosomes.size() == maxSize) {
            throw new BufferOverflowException();
        }
        chromosomes.add(chromosome);
    }

    public void populate(City[] cities, Random random) {

        if (chromosomes.size() == maxSize) {
            throw new BufferOverflowException();
        }

        if ((cities.length == 1 && maxSize > 1)
                || (cities.length == 2 && maxSize > 2)
                || (cities.length == 3 && maxSize > 6)
                || (cities.length == 4 && maxSize > 24)
                || (cities.length == 5 && maxSize > 120)
                || (cities.length == 6 && maxSize > 720)
                || (cities.length == 7 && maxSize > 5_040)
                || (cities.length == 8 && maxSize > 40_320)
                || (cities.length == 9 && maxSize > 362_880)) {
            throw new IllegalStateException("error");
        }

        HashSet<Chromosome> hashSet = new HashSet<>();

        while (chromosomes.size() < maxSize) {
            Chromosome chromo = new Chromosome(cities, random);
            if (!hashSet.contains(chromo)) {
                hashSet.add(chromo);
                add(chromo);
            }
        }

    }

    public void clear() {
        chromosomes.clear();
    }

    public City[] getCities() {
        return chromosomes.peek().getArray().clone();
    }

    public Chromosome[] getChromosomes() {
        Chromosome[] array = new Chromosome[chromosomes.size()];

        int i = 0;
        for (Chromosome chromo : chromosomes) {
            array[i++] = chromo;
        }

        return array;
    }

    public int size() {
        return chromosomes.size();
    }

    public int getAverageDistance() {

        int averageDistance = 0;

        for (Chromosome chromosome : chromosomes) {
            averageDistance += chromosome.getDistance();
        }

        return averageDistance / chromosomes.size();
    }

    public static Population fromDataSet(int popSize, Random r) {
        City[] cities = IO.Import.getCities();
        Population population = new Population(popSize);
        population.populate(cities, r);
        return population;
    }

    public static Population getRandomPopulation(int numOfCities, int sizeOfPop, Random random) {
        City[] cities = new City[numOfCities];

        for (int i = 0; i < numOfCities; i++) {
            cities[i] = City.getRandomCity(random);
        }

        Population population = new Population(sizeOfPop);

        for (int i = 0; i < sizeOfPop; i++) {
            population.add(new Chromosome(cities, random));
        }

        return population;
    }

    public Chromosome getMostFit() {
        return chromosomes.peek();
    }

    public Iterator<Chromosome> iterator() {
        return chromosomes.iterator();
    }

    public Population deepCopy() {
        Population population = new Population(maxSize);
        chromosomes.forEach((chromosome) -> population.add(chromosome));
        return population;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Population:");

        for (Chromosome chromosome : chromosomes) {
            sb.append("\n");
            sb.append(chromosome);
            sb.append(" Value: ");
            sb.append(chromosome.getDistance());
        }

        return new String(sb);
    }

}
