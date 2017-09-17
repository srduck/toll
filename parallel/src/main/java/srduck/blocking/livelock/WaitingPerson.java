package srduck.blocking.livelock;

/**
 * Created by igor on 16.09.2017.
 */
public class WaitingPerson {

    boolean work = false;
    String name;

    public WaitingPerson(String name){
        this.name = name;
    }

    public void waitStart(WaitingPerson waitingPerson){
        while(!waitingPerson.isWork()){
            System.out.println(this.name + " ждет, пока " + waitingPerson.getName() + " не начнет что-нибудь делать");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Наконец " + this.name + " дождался! Теперь он начнет что-нибудь делать");
        work = true;
    }

    public boolean isWork(){
        return work;
    }

    public String getName(){
        return name;
    }
}
