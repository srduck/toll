package srduck.blocking.livelock;

/**
 * Created by igor on 16.09.2017.
 */
public class LiveLock {
    static final WaitingPerson firstPerson = new WaitingPerson("Первый");

    static final WaitingPerson secondPerson = new WaitingPerson("Второй");

    public static void main(String[] args) {

        new Thread(new Runnable() {
            public void run() {
                firstPerson.waitStart(secondPerson);
            }
        }).start();


        new Thread(new Runnable() {
            public void run() {
                secondPerson.waitStart(firstPerson);
            }
        }).start();

    }
}
