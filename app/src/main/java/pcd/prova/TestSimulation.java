package pcd.prova;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TestSimulation {

    public static void main(String[] args){
        int nWorkers = Runtime.getRuntime().availableProcessors();
        System.out.println("Number of workers: " + nWorkers);

        int numCars = 24;
        int nSteps = 3;

        // Create list of 0 size numCars
        List<Integer> cars = IntStream.rangeClosed(1, numCars)
                .boxed()
                .collect(Collectors.toList());;

        System.out.println(cars);
        int jobSize = numCars/nWorkers;

        Barrier stepBarrier = new BarrierImpl(nWorkers);

        for (int i = 0; i < nSteps; i++) {
            List<SimulationWorker> workers = new ArrayList<>();
            System.out.println("Step " + (i + 1) + " started");
            for (int j = 0; j < nWorkers; j++) {
                int startIndex = j * jobSize;
                int endIndex = Math.min((j + 1) * jobSize, numCars);
                var simulation = new SimulationWorker("worker-" + (j + 1), cars.subList(startIndex, endIndex), jobSize, stepBarrier);
                workers.add(simulation);
                simulation.start();
            }
            try {
                stepBarrier.hitAndWaitAll();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            // Interrupt all the workers to stop them
            for (SimulationWorker worker : workers) {
                worker.interrupt();
            }
        }


    }

}
