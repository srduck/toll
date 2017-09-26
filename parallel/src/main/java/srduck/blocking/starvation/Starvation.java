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

        greedy[0].setPriority(Thread.MAX_PRIORITY);
        greedy[1].setPriority(Thread.MIN_PRIORITY);
        greedy[2].setPriority(Thread.MIN_PRIORITY);

        for (int i = 0; i < 3; i++){
            greedy[i].start();
        }

    }
}
