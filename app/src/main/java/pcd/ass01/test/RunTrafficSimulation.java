package pcd.ass01.test;

import pcd.ass01.simulation.implementation.*;

import java.util.ArrayList;

/**
 * 
 * Main class to create and run a simulation
 * 
 */
public class RunTrafficSimulation {

	public static void main(String[] args) {
		//var simulation = new TrafficSimulationSingleRoadTwoCars();
		//var simulation = new TrafficSimulationSingleRoadSeveralCars();
		//var simulation = new TrafficSimulationSingleRoadWithTrafficLightTwoCars();
		var simulation = new TrafficSimulationWithCrossRoads();
		simulation.setup();

		String sim = simulation.getClass().getName();
		String[] as = sim.split("\\.");
		sim = as[as.length - 1];


		RoadSimStatistics stat = new RoadSimStatistics();
		RoadSimView view = new RoadSimView(sim);
		view.display();
		
		simulation.addSimulationListener(stat);
		simulation.addSimulationListener(view);		
		simulation.init();
	}
}
