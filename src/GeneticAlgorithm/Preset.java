package GeneticAlgorithm;

import ObjectModels.Population;

import java.util.Random;

import static ObjectModels.Population.fromDataSet;
/**
 *
 * @author emcs
 */
public class Preset {

    private Preset() {}

    public static GeneticAlgorithm getDefaultGA () {

        Random random = new Random();
        long seed = random.nextLong();
        System.out.println("Seed: " + seed);
        Random r = new Random();
        r.setSeed(seed);

       
        int     popSize         = 500;     //calibrated values, don't change if not needed
        int     maxGen          = 500;     
        double  crossoverRate   = 0.90;    
        double  mutationRate    = 0.04;    

        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm();

        geneticAlgorithm.setPopulation(fromDataSet(popSize, r));
       
        geneticAlgorithm.setMaxGen(maxGen);
        geneticAlgorithm.setK(3);
        geneticAlgorithm.setElitismValue(1);
        geneticAlgorithm.setCrossoverRate(crossoverRate);
        geneticAlgorithm.setMutationRate(mutationRate);
        geneticAlgorithm.setRandom(r);
        geneticAlgorithm.forceUniqueness(false);
        geneticAlgorithm.setLocalSearchRate(0.00);
        geneticAlgorithm.setCrossoverType(GeneticAlgorithm.CrossoverType.UNIFORM_ORDER);
        geneticAlgorithm.setMutationType(GeneticAlgorithm.MutationType.INSERTION);

        return geneticAlgorithm;
    }

}
