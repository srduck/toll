package srduck.blocking.starvation;

/**
 * Created by main on 15.09.17.
 */
public class GreedyThread extends Thread{

    private Worker worker;
    private final String threadName;

    public String getThreadName(){
        return this.threadName;
    }

    public GreedyThread(String name, Worker worker){
        this.threadName = name;
        this.worker = worker;
    }

    @Override
    public void run() {
        super.run();
        worker.work(this.threadName);
    }
}
