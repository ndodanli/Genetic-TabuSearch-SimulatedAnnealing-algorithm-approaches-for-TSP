package GeneticAlgorithm;

import Display.WindowGraph;
import Display.WindowTSP;
import ObjectModels.Chromosome;
import ObjectModels.City;
import ObjectModels.Population;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Random;
/**
 *
 * @author emcs
 */
public class GeneticAlgorithm {

    private Population population;
    private Population initialPop;
    private int maxGen;
    private int k;
    private int elitismValue;
    private double crossoverRate;
    private double mutationRate;
    private boolean forceUniqueness;
    private double localSearchRate;
    private Random random;
    private CrossoverType crossoverType = CrossoverType.UNIFORM_ORDER;
    private MutationType mutationType = MutationType.INSERTION;

    private boolean finished;

    private int averageDistanceOfFirstGeneration;
    private int averageDistanceOfLastGeneration;
    private int bestDistanceOfFirstGeneration;
    private int bestDistanceOfLastGeneration;
    private ArrayList<Integer> averageDistanceOfEachGeneration;
    private ArrayList<Integer> bestDistanceOfEachGeneration;
    private int areaUnderAverageDistances;
    private int areaUnderBestDistances;

    public GeneticAlgorithm() {
        initialPop = Population.getRandomPopulation(10, 10, new Random());
        population = initialPop.deepCopy();
        maxGen = 10;
        k = 3;
        elitismValue = 1;
        crossoverRate = 0.95;
        mutationRate = 0.05;
        forceUniqueness = false;
        localSearchRate = 0.0;
        random = new Random();
        crossoverType = CrossoverType.UNIFORM_ORDER;
        mutationType = MutationType.INSERTION;
        finished = false;

        averageDistanceOfEachGeneration = new ArrayList<>();
        bestDistanceOfEachGeneration = new ArrayList<>();
        areaUnderAverageDistances = 0;
        areaUnderBestDistances = 0;
    }

    public void setPopulation(Population population) {
        if (population == null) {
            throw new IllegalArgumentException("null parameter");
        }
        initialPop = population;
        this.population = initialPop.deepCopy();
        averageDistanceOfFirstGeneration = population.getAverageDistance();
        bestDistanceOfFirstGeneration = population.getMostFit().getDistance();
    }

    public void setMaxGen(int maxGen) {
        if (maxGen < 0) {
            throw new IllegalArgumentException("neg paramater");
        }
        this.maxGen = maxGen;
    }

    public void setK(int k) {
        if (k < 0) {
            throw new IllegalArgumentException("neg paramater");
        }
        this.k = k;
    }

    public void setElitismValue(int elitismValue) {
        if (elitismValue > population.size()) {
            throw new IllegalArgumentException("elitism value "
                    + "cannot be greater than population size");
        }
        this.elitismValue = elitismValue;
    }

    public void setCrossoverRate(double crossoverRate) {
        if (crossoverRate < 0 || crossoverRate > 1) {
            throw new IllegalArgumentException("parameter must be between 1 and 0 inclusive");
        }
        this.crossoverRate = crossoverRate;
    }

    public void setMutationRate(double mutationRate) {
        if (mutationRate < 0 || mutationRate > 1) {
            throw new IllegalArgumentException("parameter must be between 1 and 0 inclusive");
        }
        this.mutationRate = mutationRate;
    }

    public void setLocalSearchRate(double localSearchRate) {
        if (localSearchRate < 0 || localSearchRate > 1) {
            throw new IllegalArgumentException("parameter must be between 1 and 0 inclusive");
        }
        this.localSearchRate = localSearchRate;
    }

    public void setRandom(Random random) {
        if (random == null) {
            throw new IllegalArgumentException("parameter cannot be null");
        }
        this.random = random;
    }

    public void forceUniqueness(boolean forceUniqueness) {

        int cities = population.getCities().length;
        int popSize = population.size();

        if ((cities == 1 && popSize > 1)
                || (cities == 2 && popSize > 2)
                || (cities == 3 && popSize > 6)
                || (cities == 4 && popSize > 24)
                || (cities == 5 && popSize > 120)
                || (cities == 6 && popSize > 720)
                || (cities == 7 && popSize > 5_040)
                || (cities == 8 && popSize > 40_320)
                || (cities == 9 && popSize > 362_880)) {
            throw new IllegalStateException("cannot force uniqueness when"
                    + " the population size is greater than the factorial"
                    + " of the total number of cities");
        }

        this.forceUniqueness = forceUniqueness;
    }

    public void setCrossoverType(CrossoverType crossoverType) {
        this.crossoverType = crossoverType;
    }

    public void setMutationType(MutationType mutationType) {
        this.mutationType = mutationType;
    }

    public int getAverageDistanceOfFirstGeneration() {
        if (!finished) {
            throw new IllegalArgumentException("genetic algorithm was never run");
        }
        return averageDistanceOfFirstGeneration;
    }

    public int getAverageDistanceOfLastGeneration() {
        if (!finished) {
            throw new IllegalArgumentException("genetic algorithm was never run");
        }
        return averageDistanceOfLastGeneration;
    }

    public int getBestDistanceOfFirstGeneration() {
        if (!finished) {
            throw new IllegalArgumentException("genetic algorithm was never run");
        }
        return bestDistanceOfFirstGeneration;
    }

    public int getBestDistanceOfLastGeneration() {
        if (!finished) {
            throw new IllegalArgumentException("genetic algorithm was never run");
        }
        return bestDistanceOfLastGeneration;
    }

    public ArrayList<Integer> getAverageDistanceOfEachGeneration() {
        if (!finished) {
            throw new IllegalArgumentException("genetic algorithm was never run");
        }
        return averageDistanceOfEachGeneration;
    }

    public ArrayList<Integer> getBestDistanceOfEachGeneration() {
        if (!finished) {
            throw new IllegalArgumentException("genetic algorithm was never run");
        }
        return bestDistanceOfEachGeneration;
    }

    public int getAreaUnderAverageDistances() {
        if (!finished) {
            throw new IllegalArgumentException("genetic algorithm was never run");
        }
        return areaUnderAverageDistances;
    }

    public int getAreaUnderBestDistances() {
        if (!finished) {
            throw new IllegalArgumentException("genetic algorithm was never run");
        }
        return areaUnderBestDistances;
    }

    public void showInWindow() {
        if (!finished) {
            throw new IllegalArgumentException("genetic algorithm was never run");
        }
        WindowTSP win = new WindowTSP(population.getCities(), "Genetic");
        win.draw(population.getMostFit().getArray());
    }

    public void showGraphInWindow() {
        ArrayList<ArrayList<Integer>> yValues = new ArrayList<>();
        yValues.add(averageDistanceOfEachGeneration);
        yValues.add(bestDistanceOfEachGeneration);
        ArrayList<String> legend = new ArrayList<>();
        legend.add("Average evaluation of entire population");
        legend.add("Evaluation of fittest member");
        new WindowGraph(yValues, legend);
    }

    public void run() {
        for (int i = 0; i < maxGen; i++) {
            population = createNextGeneration();
            averageDistanceOfEachGeneration.add(population.getAverageDistance());
            areaUnderAverageDistances += population.getAverageDistance();
            bestDistanceOfEachGeneration.add(population.getMostFit().getDistance());
            areaUnderBestDistances += population.getMostFit().getDistance();
        }
        finished = true;
        averageDistanceOfLastGeneration = population.getAverageDistance();
        bestDistanceOfLastGeneration = population.getMostFit().getDistance();
    }

    public void runGenetic() {
        WindowTSP win = new WindowTSP(population.getCities(), "Genetic");
        delay(500);

        Chromosome mostFitLast = population.getMostFit();
        win.draw(mostFitLast.getArray());
        printArray(mostFitLast.getArray());
        for (int i = 0; i < maxGen; i++) {
            population = createNextGeneration();
            Chromosome mostFit = population.getMostFit();
            if (!mostFit.equals(mostFitLast)) {
                printArray(mostFit.getArray());
                win.draw(mostFit.getArray());
            }
            mostFitLast = mostFit;

            

            averageDistanceOfEachGeneration.add(population.getAverageDistance());
            areaUnderAverageDistances += population.getAverageDistance();
            bestDistanceOfEachGeneration.add(population.getMostFit().getDistance());
            areaUnderBestDistances += population.getMostFit().getDistance();
        }

        finished = true;
        averageDistanceOfLastGeneration = population.getAverageDistance();
        bestDistanceOfLastGeneration = population.getMostFit().getDistance();
    }
    
    public void printArray(City[] mostFit){
        String solutionString = "";
        for (int i = 0; i < mostFit.length - 1; i++) {
            solutionString += mostFit[i].getCityName() + " ->";
        }
        solutionString += mostFit[mostFit.length-1].getCityName();
        System.out.println(solutionString);
    }

    private Population createNextGeneration() {

        Population nextGen = new Population(population.size());

        performElitism(nextGen);

        HashSet<Chromosome> chromosomesAdded = new HashSet<>();

        while (nextGen.size() < population.size() - 1) {

            Chromosome p1 = Selection.tournamentSelection(population, k, random);
            Chromosome p2 = Selection.tournamentSelection(population, k, random);

            boolean doCrossover = (random.nextDouble() <= crossoverRate);
            boolean doMutate1 = (random.nextDouble() <= mutationRate);
            boolean doMutate2 = (random.nextDouble() <= mutationRate);
            boolean doLocalSearch1 = (random.nextDouble() <= localSearchRate);
            boolean doLocalSearch2 = (random.nextDouble() <= localSearchRate);

            if (doCrossover) {
                ArrayList<Chromosome> children = crossover(p1, p2);
                p1 = children.get(0);
                p2 = children.get(1);
            }

            if (doMutate1) {
                p1 = mutate(p1);
            }
            if (doMutate2) {
                p2 = mutate(p2);
            }

            if (doLocalSearch1) {
                p1 = performLocalSearch(p1);
            }
            if (doLocalSearch2) {
                p2 = performLocalSearch(p2);
            }

            if (forceUniqueness) {
                if (!chromosomesAdded.contains(p1)) {
                    chromosomesAdded.add(p1);
                    nextGen.add(p1);
                }

                if (!chromosomesAdded.contains(p2)) {
                    chromosomesAdded.add(p2);
                    nextGen.add(p2);
                }
            } else {
                nextGen.add(p1);
                nextGen.add(p2);
            }

        }

        if (nextGen.size() != population.size()) {
            nextGen.add(Selection.tournamentSelection(population, k, random));
        }

        if (nextGen.size() != population.size()) {
            throw new AssertionError("next generation population should be full.");
        }

        return nextGen;
    }

    private void performElitism(Population nextGen) {
        PriorityQueue<Chromosome> priorityQueue = new PriorityQueue<>();

        for (Chromosome chromosome : population) {
            priorityQueue.add(chromosome);
        }

        for (int i = 0; i < elitismValue; i++) {

            Chromosome chromosome = priorityQueue.poll();

            if (localSearchRate > 0) {
                chromosome = performLocalSearch(chromosome);
            }

            nextGen.add(chromosome);
        }
    }

    private Chromosome performLocalSearch(Chromosome chromosome) {

        int bestDistance = chromosome.getDistance();
        City[] array = chromosome.getArray();
        City[] bestArray = array.clone();

        for (int i = 0; i < array.length - 1; i++) {
            for (int k = i + 1; k < array.length; k++) {

                City[] temp = array.clone();

                for (int j = i; j <= (i + k) / 2; j++) {
                    swap(temp, j, k - (j - i));
                }

                Chromosome c = new Chromosome(temp);

                int distance = c.getDistance();
                if (distance < bestDistance) {
                    bestDistance = distance;
                    bestArray = c.getArray();
                }

            }
        }

        return new Chromosome(bestArray);
    }

    private static void swap(City[] array, int i, int j) {
        City temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    private Chromosome mutate(Chromosome chromosome) {
        if (mutationType == MutationType.INSERTION) {
            return Mutation.insertion(chromosome, random);
        } else if (mutationType == MutationType.RECIPROCAL_EXCHANGE) {
            return Mutation.reciprocalExchange(chromosome, random);
        } else if (mutationType == MutationType.SCRAMBLE) {
            return Mutation.scrambleMutation(chromosome, random);
        } else {
            return Mutation.insertion(chromosome, random);
        }
    }

    private ArrayList<Chromosome> crossover(Chromosome p1, Chromosome p2) {
        ArrayList<Chromosome> children;
        if (crossoverType == CrossoverType.UNIFORM_ORDER) {
            children = Crossover.uniformOrder(p1, p2, random);
        } else if (crossoverType == CrossoverType.ONE_POINT) {
            children = Crossover.onePointCrossover(p1, p2, random);
        } else {
            children = Crossover.orderCrossover(p1, p2, random);
        }
        return children;
    }

    private static void delay(int ms) {
        try {
            Thread.sleep(ms);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public enum MutationType {
        INSERTION,
        RECIPROCAL_EXCHANGE,
        SCRAMBLE
    }

    public enum CrossoverType {
        UNIFORM_ORDER,
        ONE_POINT,
        TWO_POINT
    }

    public void reset() {
        population = initialPop.deepCopy();
        averageDistanceOfEachGeneration = new ArrayList<>();
        bestDistanceOfEachGeneration = new ArrayList<>();
        areaUnderAverageDistances = 0;
        areaUnderBestDistances = 0;
        finished = false;
    }

    public void printProperties() {
        System.out.println("----------Genetic algorithm properties----------");
        System.out.println("Number of Cities:   " + population.getMostFit().getArray().length);
        System.out.println("Population Size:    " + population.size());
        System.out.println("Max. Generation:    " + maxGen);
        System.out.println("k Value:            " + k);
        System.out.println("Elitism Value:      " + elitismValue);
        System.out.println("Force Uniqueness:   " + forceUniqueness);
        System.out.println("Local Search Rate:  " + localSearchRate);
        System.out.println("Crossover Type:     " + crossoverType);
        System.out.println("Crossover Rate:     " + (crossoverRate * 100) + "%");
        System.out.println("Mutation Type:      " + mutationType);
        System.out.println("Mutation Rate:      " + (mutationRate * 100) + "%");
    }

    public void printResults() {

        if (!finished) {
            throw new IllegalArgumentException("genetic algorithm was never run");
        }

        System.out.println("-----------Genetic algorithm results------------");
        System.out.println("Average distance of first generation:  "
                + getAverageDistanceOfFirstGeneration());
        System.out.println("Average distance of last generation:   "
                + getAverageDistanceOfLastGeneration());
        System.out.println("Best distance of first generation:     "
                + getBestDistanceOfFirstGeneration());
        System.out.println("Best distance of last generation:      "
                + getBestDistanceOfLastGeneration());
        System.out.println("Area under average distance:           "
                + getAreaUnderAverageDistances());
        System.out.println("Area under average distance:           "
                + getAreaUnderBestDistances());
    }

}
