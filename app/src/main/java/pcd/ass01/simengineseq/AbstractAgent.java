package pcd.ass01.simengineseq;

/**
 * 
 * Base  class for defining types of agents taking part to the simulation
 * 
 */
public abstract class AbstractAgent extends Thread{

	private int dt;
	private int numSteps;

	private String myId;
	private AbstractEnvironment env;
	
	/**
	 * Each agent has an identifier
	 * 
	 * @param id
	 */
	protected AbstractAgent(String id) {
		this.myId = id;
	}
	
	/**
	 * This method is called at the beginning of the simulation
	 * 
	 * @param env
	 */
	public void init(AbstractEnvironment env, int numSteps) {
		this.env = env;
		this.numSteps = numSteps;
	}
	
	/**
	 * This method is called at each step of the simulation
	 * 
	 * @param dt - logical time step
	 */
	abstract public void step(int dt);
	

	public String getAgentId() {
		return myId;
	}

	public void setdt(int dt) {
		this.dt = dt;
	}

	public void run() {
		int nStep = 0;
		while (nStep < numSteps) {
			step(dt);
			nStep++;
		}
	}
	
	protected AbstractEnvironment getEnv() {
		return this.env;
	}
}
