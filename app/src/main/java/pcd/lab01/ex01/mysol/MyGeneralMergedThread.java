package pcd.lab01.ex01.mysol;

import java.util.List;

public class MyGeneralMergedThread extends Thread{

    private int[] v;
    private List<MySortedThread> threads;

    public MyGeneralMergedThread(String name, int[] v, List<MySortedThread> threads) {
        super(name);
        this.v = v;
        this.threads = threads;
    }

    public void run() {
        int nParts = threads.size();
        log("started - merging " + nParts + " parts");
        try {
            for (var t: threads) {
                t.join();
            }
            log("subparts sorted, going to merge...");
            long t0 = System.currentTimeMillis();
            int[] merged = this.merge(v, nParts);
            for (int i = 0; i < merged.length; i++) {
                v[i] = merged[i];
            }
            long t1 = System.currentTimeMillis();
            log("completed -- " + (t1 - t0) + " ms for merging.");
        } catch(InterruptedException ex) {
            log("exception.");
        }
    }

    private int[] merge(int[] v, int nParts) {
        int[] vnew = new int[v.length];

        int partSize = v.length/nParts;
        int from = 0;

        int[] indexes = new int[nParts];
        int[] max = new int[nParts];
        for (int i = 0; i < indexes.length - 1; i++) {
            indexes[i] = from;
            max[i] = from + partSize;
            from = max[i];
        }
        indexes[indexes.length - 1] = from;
        max[indexes.length - 1] = v.length;

        int i3 = 0;
        boolean allFinished = false;
        while (!allFinished) {
            int min = Integer.MAX_VALUE;
            int index = -1;
            for (int i = 0; i < indexes.length; i++) {
                if (indexes[i] < max[i]) {
                    if (v[indexes[i]] < min) {
                        index = i;
                        min = v[indexes[i]];
                    }
                }
            }

            if (index != -1) {
                vnew[i3] = v[indexes[index]];
                indexes[index]++;
                i3++;
            } else {
                allFinished = true;
            }
        }
        return vnew;
    }

    protected void log(String msg) {
        System.out.println("[ " + this.getName()+ " ][ " + System.currentTimeMillis() + " ] " + msg);
    }
}
