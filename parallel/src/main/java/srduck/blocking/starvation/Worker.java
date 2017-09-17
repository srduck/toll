package srduck.blocking.starvation;

/**
 * Created by main on 15.09.17.
 */
public class Worker {

    public synchronized void work(String name){
        while (true) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("My name is " + name + ". And i like this work!");
        }
    }
}
