package pcd.lab01.ex01.mysol;

public class MySortedThread extends Thread{
    private int[] v;
    private int start;
    private int end;

    public MySortedThread(String name, int[] v, int start, int end) {
        super(name);
        this.v = v;
        this.start = start;
        this.end = end;
    }

    public void run() {
        log("started - sorting from " + start + " " + end);
        java.util.Arrays.sort(v, start, end);
        log("completed.");
    }

    protected void log(String msg) {
        System.out.println("[ " + this.getName()+ " ][ " + System.currentTimeMillis() + " ] " + msg);
    }
}
