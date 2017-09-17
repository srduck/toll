package srduck.yield;

/**
 * Created by main on 14.09.17.
 */
public class CommonRun extends Thread {

    String name;

    public CommonRun (String name){
        this.name = name;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Завершение потока с именем: " + name);
    }
}
