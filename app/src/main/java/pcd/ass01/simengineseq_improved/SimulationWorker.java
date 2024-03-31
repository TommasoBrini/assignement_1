package pcd.ass01.simengineseq_improved;

import pcd.prova.Barrier;

import java.util.List;

public class SimulationWorker extends Thread {

    private String name;
    private List<AbstractAgent> agents;
    private int dt;

    private Barrier barrier;

    public SimulationWorker(String name, List<AbstractAgent> agents, int dt, Barrier stepBarrier) {
        this.name = name;
        this.agents = agents;
        this.dt = dt;
        this.barrier = stepBarrier;
    }

    public void run() {
        try{
            System.out.println("Worker " + name + " started");
            for (var agent: agents) {
                agent.step(dt);
            }

            System.out.println("Worker " + name + " completed");
            barrier.hitAndWaitAll();
        } catch (InterruptedException ex) {
            System.out.println("Interrupted!");
        }
    }
}
