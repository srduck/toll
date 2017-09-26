package srduck.blocking.starvation;

/**
 * Created by main on 15.09.17.
 */
public class Worker {

    public  void work(String name){
        while (true) {

            synchronized (this) {
                System.out.println("Hi! My name is " + name + ". I'm working! And i like this work!");
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("My name is " + name + ". I'm stoping for a little while! Bye.");
            }

        }
    }
}
