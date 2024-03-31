package pcd.prova;

public class BarrierImpl implements Barrier{
    private int nparticipants;
    private int countWorkers = 0;
    private boolean canPass = false;

    public BarrierImpl(int nParticipants) {
        this.nparticipants = nParticipants;
    }

    @Override
    public synchronized void hitAndWaitAll() throws InterruptedException {
        countWorkers++;
        if(countWorkers == nparticipants){
            canPass = true;
            notifyAll();
        } else {
            while(!canPass){
                wait();
            }
        }
    }
}
