package testjpf;

import java.util.List;

public class Worker extends Thread {
    private int step;
    private int id;
    private List<Agent> agents;
    private CyclicBarrier barrier;

    public Worker(int id, int step, List<Agent> agents, CyclicBarrier barrier) {
        this.step = step;
        this.id = id;
        this.agents = agents;
        this.barrier = barrier;
    }

    public void run() {
        for (int i = 0; i < step; i++) {
            for (var agent : agents) {
                agent.step(id);
            }
            try {
                barrier.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
