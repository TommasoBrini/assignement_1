package pcd.prova;

import java.util.List;

public class SimulationWorker extends Thread {

    private String name;
    private List<Integer> cars;
    private int jobSize;

    private Barrier barrier;

    public SimulationWorker(String name, List<Integer> cars, int jobSize, Barrier stepBarrier) {
        this.name = name;
        this.cars = cars;
        this.jobSize = jobSize;
        this.barrier = stepBarrier;
    }

    public void run() {
        try{
            System.out.println("Worker " + name + " started");

            System.out.println(name + " processing car " + cars);

            System.out.println("Worker " + name + " completed");
            barrier.hitAndWaitAll();
        } catch (InterruptedException ex) {
            System.out.println("Interrupted!");
        }
    }
}
