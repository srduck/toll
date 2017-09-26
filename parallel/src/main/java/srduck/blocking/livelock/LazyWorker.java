package srduck.blocking.livelock;

/**
 * Created by igor on 16.09.2017.
 */
public class LazyWorker {

    boolean isLasy;
    String name;

    public LazyWorker(String name){
        this.name = name;
        this.isLasy = true;
    }

    public void waitStart(LazyWorker lazyWorker, Hummer hummer){

        while(isLazy()) {

            if (hummer.getWorker() != this) {
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    continue;
                }
                continue;
            }

            if (lazyWorker.isLazy()){
                System.out.println(lazyWorker.getName() + ", вот тебе кувалда. Иди работай");
                hummer.setWorker(lazyWorker);
                continue;
            }

            isLasy = false;
            System.out.println("Я закончил, " + lazyWorker.getName());
            hummer.setWorker(lazyWorker);

        }

    }

    public boolean isLazy(){
        return isLasy;
    }

    public String getName(){
        return name;
    }
}
