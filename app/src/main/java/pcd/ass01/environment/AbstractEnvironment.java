package pcd.ass01.environment;

import pcd.ass01.agent.Action;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 *   
 * Base class to define the environment of the simulation
 *   
 */
public abstract class AbstractEnvironment {

	private String id;
	protected ConcurrentHashMap<String, Action> submittedActions;

	protected AbstractEnvironment(String id) {
		this.id = id;		
		this.submittedActions = new ConcurrentHashMap<>();
	}
	
	public String getId() {
		return id;
	}
	
	/**
	 * 
	 * Called at the beginning of the simulation
	 */
	public abstract void init();
	
	/**
	 * 
	 * Called at each step of the simulation
	 * 
	 * @param dt
	 */
	public abstract void step(int dt);

	/**
	 * 
	 * Called by an agent to get its percepts 
	 * 
	 * @param agentId - identifier of the agent
	 * @return agent percept
	 */
	public abstract Percept getCurrentPercepts(String agentId);

	/**
     * Called by agent to submit an action to the environment
     *
     * @param agentId
     * @param act     - the action
     */
	public void submitAction(String agentId, Action act) {
		submittedActions.put(agentId, act);
	}
	
	/**
	 * 
	 * Called at each simulation step to clean the list of actions
	 * submitted by agents
	 * 
	 */
	public void cleanActions() {
		submittedActions.clear();
	}

	/**
	 * 
	 * Called at each simulation step to process the actions 
	 * submitted by agents. 
	 * 
	 */
	public abstract void processActions();

}
