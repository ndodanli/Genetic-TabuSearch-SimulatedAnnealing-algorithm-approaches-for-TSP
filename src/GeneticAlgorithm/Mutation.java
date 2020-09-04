package GeneticAlgorithm;

import ObjectModels.Chromosome;
import ObjectModels.City;

import java.util.Random;

/**
 *
 * @author emcs
 */
class Mutation {

    
    private Mutation () {}

    
    static Chromosome insertion (Chromosome chromosome, Random random) {
        City[] cities = chromosome.getArray();
        int randomIndex = random.nextInt(cities.length);
        int randomDestination = random.nextInt(cities.length);

        if (randomIndex < randomDestination) {
            City temp = cities[randomIndex];
            for (int i = randomIndex; i < randomDestination; i++) {
                cities[i] = cities[i+1];
            }
            cities[randomDestination] = temp;
        } else {
            City temp = cities[randomIndex];
            for (int i = randomIndex; i > randomDestination; i--) {
                cities[i] = cities[i-1];
            }
            cities[randomDestination] = temp;
        }
        return new Chromosome(cities);
    }

    
    static Chromosome reciprocalExchange (Chromosome chromosome, Random random) {
        City[] cities = chromosome.getArray();
        int l = cities.length;
        swap(cities, random.nextInt(l), random.nextInt(l));
        return new Chromosome(cities);
    }

    
    static Chromosome scrambleMutation (Chromosome chromosome, Random random) {

        

        City[] cities = chromosome.getArray();
        int randomIndexStart = random.nextInt(cities.length);
        int randomIndexEnd = random.nextInt(cities.length);

        for (int i = randomIndexStart; i%cities.length != randomIndexEnd; i++) {
            int r = random.nextInt(Math.abs(i%cities.length - randomIndexEnd));
            swap(cities, i%cities.length, (i+r)%cities.length);
        }

        return new Chromosome(cities);
    }

    
    private static void swap (City[] array, int i, int j) {
        City temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

}
