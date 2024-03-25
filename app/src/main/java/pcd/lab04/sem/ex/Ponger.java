package pcd.lab04.sem.ex;

import java.util.concurrent.Semaphore;

public class Ponger extends Thread {

	private Semaphore pongDone;
	private Semaphore pingDone;

	public Ponger(Semaphore pongDone, Semaphore pingDone) {
		this.pongDone = pongDone;
		this.pingDone = pingDone;
	}	
	
	public void run() {
		while (true) {
			try {
				pingDone.acquire();
				System.out.println("pong!");
				pongDone.release();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
}