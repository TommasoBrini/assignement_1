package pcd.lab01.ex01.mysol;

import java.util.Random;

public class ConcurrentSortWithTwoThreads {
    static final int VECTOR_SIZE = 400_000_000;

    public static void main(String[] args) {

        log("Generating array...");
        int[] v = genArray(VECTOR_SIZE);

        log("Array generated.");
        log("Create two threads to sort " + VECTOR_SIZE + " elements...");

        MySortedThread th1 = new MySortedThread("sorter-1", v, 0, v.length / 2 - 1);
        MySortedThread th2 = new MySortedThread("sorter-2", v, v.length / 2, v.length - 1);
        MyTwoMergedThread m = new MyTwoMergedThread("merger", v, th1, th2);

        long t0 = System.currentTimeMillis();
        th1.start();
        th2.start();
        m.start();
        try {
            m.join();
            long t1 = System.currentTimeMillis();
            log("Done. Time elapsed: " + (t1 - t0) + " ms");
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

    }

    private static int[] genArray(int n) {
        Random gen = new Random(System.currentTimeMillis());
        int v[] = new int[n];
        for (int i = 0; i < v.length; i++) {
            v[i] = gen.nextInt();
        }
        return v;
    }

    private static void dumpArray(long[] v) {
        for (long l : v) {
            System.out.print(l + " ");
        }
        System.out.println();
    }

    private static void log(String msg) {
        System.out.println(msg);
    }
}

