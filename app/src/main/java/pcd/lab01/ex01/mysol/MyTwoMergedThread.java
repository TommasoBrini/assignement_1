package pcd.lab01.ex01.mysol;

public class MyTwoMergedThread extends Thread{
    private int[] v;
    private MySortedThread t1;
    private MySortedThread t2;

    public MyTwoMergedThread(String name, int[] v, MySortedThread t1, MySortedThread t2) {
        super(name);
        this.v = v;
        this.t1 = t1;
        this.t2 = t2;
    }

    public void run() {
        log("started - merging");
        try {
            long t0 = System.currentTimeMillis();
            t1.join();
            t2.join();
            log("subparts sorted, going to merge...");
            int[] merged = this.merge(t1, t2);
            for (int i = 0; i < merged.length; i++) {
                v[i] = merged[i];
            }
            long t1 = System.currentTimeMillis();
            log("completed -- " + (t1 - t0) + " ms for merging.");
        } catch(InterruptedException ex) {
            log("exception.");
        }
    }

    private int[] merge(MySortedThread t1, MySortedThread t2) {
        int[] vnew = new int[v.length];
        int i1 = 0;
        int max1 = v.length/2;
        int i2 = max1;
        int max2 = v.length;
        int i3 = 0;
        while ((i1 < max1) && (i2 < max2)) {
            if (v[i1] < v[i2]) {
                vnew[i3] = v[i1];
                i1++;
            } else {
                vnew[i3] = v[i2];
                i2++;
            }
            i3++;
        }
        if (i1 < max1) {
            while (i1 < max1) {
                vnew[i3] = v[i1];
                i1++;
                i3++;
            }
        } else {
            while ((i2 < max2)) {
                vnew[i3] = v[i2];
                i2++;
                i3++;
            }
        }
        return vnew;
    }

    protected void log(String msg) {
        System.out.println("[ " + this.getName()+ " ][ " + System.currentTimeMillis() + " ] " + msg);
    }

}
