
import GeneticAlgorithm.GeneticAlgorithm;
import GeneticAlgorithm.Preset;
import java.util.Scanner;
import SimulatedAnealingAlgorithm.SimulatedAnnealing;
import TabuAlgorithm.TabuAlgorithm;
/**
 *
 * @author emcs
 */
public class Main {

    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        int type = -1;
        do {
            System.out.println("1-Genetic \n 2-Tabu \n 3-Simulated Annealing \n 4-Exit");
            System.out.print("Choose One:");
            type = scn.nextInt();
            switch (type) {
                case 1 -> {
                    GeneticAlgorithm geneticAlgorithm = Preset.getDefaultGA();
                    geneticAlgorithm.runGenetic();
                    geneticAlgorithm.showGraphInWindow();
                    geneticAlgorithm.printProperties();
                    geneticAlgorithm.printResults();
                }

                case 2 -> {
                    TabuAlgorithm.runTabuAlgorithm();
                }
                case 3 -> {
                    SimulatedAnnealing.runSimulatedAnnealing();
                }
                case 4 -> {
                    System.exit(0);
                }
                default -> {
                    if(type < 1 || type > 4) System.out.println("Choose between 1-4(1-4 included)");
                }
            }
            
        } while (true);
        
    }
}
