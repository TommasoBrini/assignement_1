package pcd.prova;

public class LatchImpl implements Latch{

    private int count;
    private int resetCount;

    public LatchImpl(int count) {
        this.count = count;
        this.resetCount = count;
    }

    @Override
    public void countDown() {
        count--;
        if (count == 0) {
            synchronized (this) {
                notifyAll();
            }
        }
    }

    @Override
    public void await() throws InterruptedException {
        while (count > 0) {
            synchronized (this) {
                wait();
            }
        }
    }

    @Override
    public void reset() {
        count = resetCount;
    }
}
