package pcd.lab04.sem.ex;

import java.util.concurrent.Semaphore;

/**
 * Unsynchronized version
 * 
 * @TODO make it sync 
 * @author aricci
 *
 */
public class TestPingPong {
	public static void main(String[] args) {
		Semaphore pingDone = new Semaphore(0);
		Semaphore pongDone = new Semaphore(0);
		new Pinger(pongDone, pingDone).start();
		new Ponger(pongDone, pingDone).start();
		pingDone.release();
	}

}
