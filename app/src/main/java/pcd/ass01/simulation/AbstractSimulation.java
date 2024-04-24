package pcd.ass01.simulation;

import pcd.ass01.PausableThreadPoolExecutor;
import pcd.ass01.agent.AbstractAgent;
import pcd.ass01.environment.AbstractEnvironment;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import pcd.ass01.monitor.CyclicBarrier;

/**
 * Base class for defining concrete simulations
 */
public abstract class AbstractSimulation {

    /* environment of the simulation */
    private AbstractEnvironment env;

    /* list of the agents */
    private List<AbstractAgent> agents;

    /* simulation listeners */
    private List<SimulationListener> listeners;

    /* logical time step */
    private int dt;

    /* initial logical time */
    private int t0;

    /* in the case of sync with wall-time */
    private boolean toBeInSyncWithWallTime;
    private int nStepsPerSec;

    /* for time statistics*/
    private long currentWallTime;
    private long startWallTime;
    private long endWallTime;
    private long averageTimePerStep;
    int t;
    long timePerStep;
    int nSteps;
    private List<SimulationWorker> workers = new ArrayList<>();
    private PausableThreadPoolExecutor executorService;
    private boolean isPaused = false;
    private boolean isStopped = false;

    protected AbstractSimulation() {
        agents = new ArrayList<AbstractAgent>();
        listeners = new ArrayList<SimulationListener>();
        toBeInSyncWithWallTime = false;
    }

    /**
     * Method used to configure the simulation, specifying env and agents
     */
    protected abstract void setup();

    public void init() {
        env.init();
        for (var a : agents) {
            a.init(env);
        }
        this.notifyReset(t, agents, env);
    }

    /**
     * Method running the simulation for a number of steps,
     * using a sequential approach
     *
     * @param numSteps
     */
    public void run(int numSteps) {
        nSteps = numSteps;
        startWallTime = System.currentTimeMillis();

        /* initialize the env and the agents inside */
        t = t0;

        init();

        timePerStep = 0;

        int nWorkers = Math.min(Runtime.getRuntime().availableProcessors(), agents.size());

        int jobSize = (int) Math.ceil((double) agents.size() / nWorkers);

        Runnable envRunnable = new Runnable() {
            int step = 0;

            @Override
            public void run() {
                step++;
                System.out.println("doStep()");
                doStep(step);
            }
        };

        CyclicBarrier barrier = new CyclicBarrier(nWorkers, envRunnable);

        executorService = new PausableThreadPoolExecutor(new ArrayBlockingQueue<>(nWorkers));

        //TO DO
        env.step(dt);

        for (int i = 0; i < nWorkers; i++) {
            int startIndex = i * jobSize;
            int endIndex = Math.min((i + 1) * jobSize, agents.size());
            if (startIndex >= agents.size()) {
                break;
            }
            Runnable worker = createTask(agents.subList(startIndex, endIndex), dt, numSteps);
            executorService.execute(worker);
        }

        endWallTime = System.currentTimeMillis();
        this.averageTimePerStep = timePerStep / numSteps;
    }

    private Runnable createTask(List<AbstractAgent> agents, int dt, int numSteps) {
        return new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < numSteps; i++) {
                    for (var agent : agents) {
                        agent.step(dt);
                    }
                }
            }
        };
    }


    public void pause() {
        isPaused = true;
        executorService.pause();
    }

    public void resume() {
        isPaused = true;
        executorService.resume();
    }

    public boolean isStopped() {
        return isStopped;
    }

    public void stop() {
        isStopped = true;
        executorService.shutdownNow();
    }

    protected void doStep(int step) {
        if (isStopped) {
            return;
        }
        while (isPaused) {
            System.out.println("Pausa"); // Puoi impostare un tempo di attesa arbitrario
        }
        currentWallTime = System.currentTimeMillis();
        t += dt;
        /* process actions submitted to the environment */

        env.processActions();

        notifyNewStep(t, agents, env);

        timePerStep += System.currentTimeMillis() - currentWallTime;

        if (toBeInSyncWithWallTime) {
            syncWithWallTime();
        }

        if (step != nSteps) {
            env.step(dt);
            env.cleanActions();
        }
        if (nSteps == step) {
            isStopped = true;
        }

    }

    public long getSimulationDuration() {
        return endWallTime - startWallTime;
    }

    public long getAverageTimePerCycle() {
        return averageTimePerStep;
    }

    /* methods for configuring the simulation */

    protected void setupTimings(int t0, int dt) {
        this.dt = dt;
        this.t0 = t0;
    }

    protected void syncWithTime(int nCyclesPerSec) {
        this.toBeInSyncWithWallTime = true;
        this.nStepsPerSec = nCyclesPerSec;
    }

    protected void setupEnvironment(AbstractEnvironment env) {
        this.env = env;
    }

    protected void addAgent(AbstractAgent agent) {
        agents.add(agent);
    }

    /* methods for listeners */

    public void addSimulationListener(SimulationListener l) {
        this.listeners.add(l);
    }

    private void notifyReset(int t0, List<AbstractAgent> agents, AbstractEnvironment env) {
        for (var l : listeners) {
            l.notifyInit(t0, agents, env);
        }
    }

    private void notifyNewStep(int t, List<AbstractAgent> agents, AbstractEnvironment env) {
        for (var l : listeners) {
            System.out.println("notify step done");
            l.notifyStepDone(t, agents, env);
        }
    }

    /* method to sync with wall time at a specified step rate */

    private void syncWithWallTime() {
        try {
            long newWallTime = System.currentTimeMillis();
            long delay = 1000 / this.nStepsPerSec;
            long wallTimeDT = newWallTime - currentWallTime;
            if (wallTimeDT < delay) {
                Thread.sleep(delay - wallTimeDT);
            }
        } catch (Exception ex) {
        }
    }
}
