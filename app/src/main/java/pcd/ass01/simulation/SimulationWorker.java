package pcd.ass01.simulation;

import pcd.ass01.agent.AbstractAgent;

import java.util.List;
import pcd.ass01.monitor.CyclicBarrier;

public class SimulationWorker extends Thread {

    private String name;
    private List<AbstractAgent> agents;
    private int dt;
    private int step;
    private CyclicBarrier barrier;

    public SimulationWorker(String name, List<AbstractAgent> agents, int dt, int step, CyclicBarrier stepBarrier) {
        this.name = name;
        this.agents = agents;
        this.dt = dt;
        this.step = step;
        this.barrier = stepBarrier;
    }

    public void run() {
        for (int i = 0; i < step; i++) {
            for (var agent : agents) {
                agent.step(dt);
            }
            try {
                barrier.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
