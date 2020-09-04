package GeneticAlgorithm;

import ObjectModels.Chromosome;
import ObjectModels.Population;

import java.util.ArrayList;
import java.util.Random;
/**
 *
 * @author emcs
 */
class Selection {

    private Selection() {
    }

    private static final int ODDS_OF_NOT_PICKING_FITTEST = 5;

    static Chromosome tournamentSelection(Population population, int k, Random random) {
        if (k < 1) {
            throw new IllegalArgumentException("K must be greater than 0");
        }

        Chromosome[] populationAsArray = population.getChromosomes();
        ArrayList<Chromosome> kChromosomes = getKChromosomes(populationAsArray, k, random);
        return getChromosome(kChromosomes, random);
    }

    private static ArrayList<Chromosome> getKChromosomes(Chromosome[] pop, int k, Random random) {

        ArrayList<Chromosome> kChromosomes = new ArrayList<>();

        for (int j = 0; j < k; j++) {
            Chromosome chromosome = pop[random.nextInt(pop.length)];
            kChromosomes.add(chromosome);
        }

        return kChromosomes;
    }

    private static Chromosome getChromosome(ArrayList<Chromosome> arrayList, Random random) {

        Chromosome bestChromosome = getBestChromosome(arrayList);

        if (random.nextInt(ODDS_OF_NOT_PICKING_FITTEST) == 0 && arrayList.size() != 1) {
            arrayList.remove(bestChromosome);
            return arrayList.get(random.nextInt(arrayList.size()));
        }

        return bestChromosome;
    }

    private static Chromosome getBestChromosome(ArrayList<Chromosome> arrayList) {

        Chromosome bestC = null;

        for (Chromosome c : arrayList) {
            if (bestC == null) {
                bestC = c;
            } else if (c.getDistance() < bestC.getDistance()) {
                bestC = c;
            }
        }

        return bestC;
    }

}
