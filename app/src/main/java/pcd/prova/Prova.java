package pcd.prova;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public class Prova {

    public static void main(String[] args) {
        // Creazione della CyclicBarrier per i thread A e B
        CyclicBarrier barrier = new CyclicBarrier(9);

        // Creazione del thread A
        Thread threadA = new Thread(new ThreadA(barrier));

        // Creazione degli 8 threadB
        Thread[] threadBs = new Thread[8];
        for (int i = 0; i < threadBs.length; i++) {
            threadBs[i] = new Thread(new ThreadB(barrier));
        }

        // Avvio del thread A
        threadA.start();

        // Avvio degli 8 threadB
        for (Thread threadB : threadBs) {
            threadB.start();
        }
    }
}

class ThreadA implements Runnable {
    private CyclicBarrier barrier;

    public ThreadA(CyclicBarrier barrier) {
        this.barrier = barrier;
    }

    @Override
    public void run() {
        while (true) {
            try {
                // Esecuzione del metodo step()
                step();
                // Attesa che tutti gli 8 threadB e il threadA completino il loro passo
                barrier.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void step() {
        System.out.println("Thread A");
    }
}

class ThreadB implements Runnable {
    private CyclicBarrier barrier;

    public ThreadB(CyclicBarrier barrier) {
        this.barrier = barrier;
    }

    @Override
    public void run() {
        while (true) {
            try {
                // Esecuzione del metodo step()
                step();
                // Attesa che tutti gli 8 threadB e il threadA completino il loro passo
                barrier.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void step() {
        System.out.println("Thread B");
    }
}