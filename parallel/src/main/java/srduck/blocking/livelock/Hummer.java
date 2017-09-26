package srduck.blocking.livelock;

/**
 * Created by main on 25.09.17.
 */
public class Hummer {

    private LazyWorker worker;

    public Hummer(LazyWorker worker){
        this.worker = worker;
    }

    public synchronized LazyWorker getWorker(){
        return worker;
    }

    public synchronized void  setWorker(LazyWorker worker){
        this.worker = worker;
    }

}
