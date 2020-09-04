package SimulatedAnealingAlgorithm;

import Display.WindowTSP;
import ObjectModels.City;
/**
 *
 * @author emcs
 */
public class SimulatedAnnealing {

    public static void runSimulatedAnnealing() {

        TourManager.init();

        double temp = 100000;

        double coolingRate = 0.003;

        Tour currentSolution = new Tour();
        currentSolution.generateIndividual();

        System.out.println("Total distance of initial solution: " + currentSolution.getTotalDistance());
        System.out.println("Tour: " + currentSolution);

        Tour best = new Tour(currentSolution.getTour());

        WindowTSP win = new WindowTSP(TourManager.getAllCities(), "Simulated Annealing");

        while (temp > 1) {

            Tour newSolution = new Tour(currentSolution.getTour());

            int tourPos1 = Utility.randomInt(0, newSolution.tourSize());
            int tourPos2 = Utility.randomInt(0, newSolution.tourSize());

            while (tourPos1 == tourPos2) {
                tourPos2 = Utility.randomInt(0, newSolution.tourSize());
            }

            City citySwap1 = newSolution.getCity(tourPos1);
            City citySwap2 = newSolution.getCity(tourPos2);

            newSolution.setCity(tourPos2, citySwap1);
            newSolution.setCity(tourPos1, citySwap2);

            int currentDistance = currentSolution.getTotalDistance();
            int neighbourDistance = newSolution.getTotalDistance();

            double rand = Utility.randomDouble();
            if (Utility.acceptanceProbability(currentDistance, neighbourDistance, temp) > rand) {
                currentSolution = new Tour(newSolution.getTour());
                win.draw(best.getTourArray());
            }

            if (currentSolution.getTotalDistance() < best.getTotalDistance()) {
                best = new Tour(currentSolution.getTour());
                System.out.println(best);
                win.draw(best.getTourArray());
            }

            temp *= 1 - coolingRate;
        }

        System.out.println("Final solution distance: " + best.getTotalDistance());
        System.out.println("Tour: " + best);
    }

}
