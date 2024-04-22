import pcd.ass01.simulation.SimulationThread;
import pcd.ass01.simulation.implementation.TrafficSimulationSingleRoadMassiveNumberOfCars;
import pcd.ass01.simulation.implementation.TrafficSimulationWithCrossRoads;
import pcd.ass01.simulation.implementation.RoadSimStatistics;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MassiveTests {

	@BeforeEach
	public void setup() {
		File file = new File("log.txt");
		if (file.exists()) {
			file.delete();
		}
	}

	@Test
	public void testMassive() throws InterruptedException {
		int numCars = 5000;
		int nSteps = 100;

		var simulation = new TrafficSimulationSingleRoadMassiveNumberOfCars(numCars);
		simulation.setup();
		RoadSimStatistics stat = new RoadSimStatistics();
		simulation.addSimulationListener(stat);
		SimulationThread t = new SimulationThread(simulation);
		t.setStep(nSteps);
		t.start();
		t.join();
		while(!simulation.isStopped()){
			Thread.sleep(1000);
		}

		// /app/log.txt must be the same of resources/log.txt
		boolean areFilesEqual = FileComparator.compareFiles("log.txt", "src/test/java/resources/log_massive_improved.txt");
		assertTrue(areFilesEqual, "The files /app/log.txt and /app/src/test/java/resources/log_massive_improved.txt are not the same");

	}

	@Test
	public void testTrafficLights() throws InterruptedException {

		int nSteps = 500;

		var simulation = new TrafficSimulationWithCrossRoads();
		simulation.setup();
		RoadSimStatistics stat = new RoadSimStatistics();
		simulation.addSimulationListener(stat);
		Thread t = new SimulationThread(simulation);
		t.start();
		t.join();

		// /app/log.txt must be the same of resources/log.txt
		boolean areFilesEqual = FileComparator.compareFiles("log.txt", "src/test/java/resources/log_with_trafficLights_improved.txt");
		assertTrue(areFilesEqual, "The files /app/log.txt and /app/src/test/java/resources/log_with_trafficLights_improved.txt are not the same");

	}

	@Test
	public void testRandMassive() throws InterruptedException {
		int numCars = 5000;
		int nSteps = 100;

		var simulation = new TrafficSimulationSingleRoadMassiveNumberOfCars(numCars);

		simulation.setup();
		RoadSimStatistics stat = new RoadSimStatistics();
		simulation.addSimulationListener(stat);
		Thread t = new SimulationThread(simulation);
		t.start();
		t.join();

		// /app/log.txt must be the same of resources/log.txt
		boolean areFilesEqual = FileComparator.compareFiles("log.txt", "src/test/java/resources/rand_log.txt");
		assertTrue(areFilesEqual, "The files /app/log.txt and /app/src/test/java/resources/rand_log.txt are not the same");

	}

}
