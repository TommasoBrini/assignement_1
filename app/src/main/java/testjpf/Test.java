package testjpf;

import pcd.ass01.simulation.SimulationWorker;

import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {

        int numCars = 16;
        int nSteps = 10;

        List<Agent> agents = new ArrayList<>();
        for(int i = 0; i < numCars; i++) {
            agents.add(new Agent(i));
        }

        List<Worker> workers = new ArrayList<>();
        int nWorkers = Math.min(Runtime.getRuntime().availableProcessors(), agents.size());
        int jobSize = (int) Math.ceil((double) agents.size() / nWorkers);
        System.out.println("env step at 0 step");
        Runnable envRunnable = new Runnable() {
            int step = 0;
            @Override
            public void run() {
                System.out.println("env step at " + step + " step");
            }
        };
        CyclicBarrier barrier = new CyclicBarrier(nWorkers, envRunnable);

        for (int i = 0; i < nWorkers; i++) {
            int startIndex = i * jobSize;
            int endIndex = Math.min((i + 1) * jobSize, agents.size());
            if(startIndex >= agents.size()) {
                break;
            }
            var simulation = new Worker((i + 1), nSteps, agents.subList(startIndex, endIndex), barrier);
            workers.add(simulation);
            simulation.start();
        }
        for (var simulation : workers) {
            try {
                simulation.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
