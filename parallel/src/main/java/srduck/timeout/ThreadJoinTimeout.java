package srduck.timeout;

/**
 * Created by main on 14.09.17.
 */
public class ThreadJoinTimeout {
    public static void main(String... args) throws InterruptedException{

        StopedThread thread = new StopedThread();
        System.out.println("Thread после создания и до запуска: " + thread.getState());

        thread.start();
        System.out.println("Thread после запуска и до join(timeout): " + thread.getState());

        thread.join(10000);
        System.out.println("Thread после join(timeout): " + thread.getState());

        thread.setNeedStop(true);
        thread.join();
        System.out.println("Thread после завершения: " + thread.getState());


    }
}
