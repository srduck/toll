package srduck.timeout;

/**
 * Created by main on 08.09.17.
 */
public class StopedThread extends Thread {

    private boolean needStop = false;

    public void setNeedStop(boolean needStop){
        this.needStop = needStop;
    }

    @Override
    public void run() {
        super.run();
        while(!needStop){

        }
        System.out.println("needStop = " + needStop + " => exit");
    }
}
