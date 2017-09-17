package srduck.blocking.deadlock;

/**
 * Created by main on 15.09.17.
 */
public class Deadlock {


    public static void main(String[] args) {

        final Friend alphonse = new Friend("Alphonse");

        final Friend gaston = new Friend("Gaston");

        new Thread(new Runnable() {
            public void run() {
                alphonse.bow(gaston);
                System.out.println("exit from alphonse");
            }
        }).start();
       /*try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        new Thread(new Runnable() {
            public void run() {
                gaston.bow(alphonse);
                System.out.println("exit from gaston");
            }
        }).start();
    }
}
