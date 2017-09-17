package srduck.yield;

/**
 * Created by main on 14.09.17.
 */
public class YieldRun extends Thread {

    String name;

    public YieldRun(String name){
        this.name = name;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Thread.yield();
        System.out.println("Завершение потока с именем: " + name);
    }
}
