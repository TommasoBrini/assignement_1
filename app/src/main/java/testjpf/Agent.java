package testjpf;

public class Agent {
    private int id;

    public Agent(int id) {
        this.id = id;
    }

    public void step(int id2){
        System.out.println("WORKER-"+ id2 + ": Agent " + id + " is stepping");
    }
}
