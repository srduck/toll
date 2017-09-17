package srduck.blocking.starvation;

/**
 * Created by main on 15.09.17.
 */
public class Starvation {
    public static void main(String ... args){
        Worker worker = new Worker();
        GreedyThread[] greedy = { new GreedyThread("First Greedy", worker),
                            new GreedyThread("Second Greedy", worker),
                            new GreedyThread("Third Greedy", worker)};
        for (int i = 0; i < greedy.length; i++){
            greedy[i].start();
            System.out.println(greedy[i].getThreadName() + " started");
        }
    }
}
