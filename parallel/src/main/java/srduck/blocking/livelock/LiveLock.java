package srduck.blocking.livelock;

/**
 * Created by igor on 16.09.2017.
 */
public class LiveLock {
    static final LazyWorker firstWorker = new LazyWorker("Первый");

    static final LazyWorker secondWorker = new LazyWorker("Второй");

    public static void main(String[] args) {

        Hummer hummer = new Hummer(firstWorker);

        new Thread(new Runnable() {
            public void run() {
                firstWorker.waitStart(secondWorker, hummer);
            }
        }).start();


        new Thread(new Runnable() {
            public void run() {
                secondWorker.waitStart(firstWorker, hummer);
            }
        }).start();

    }
}
