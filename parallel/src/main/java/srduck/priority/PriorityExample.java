package srduck.priority;

/**
 * Created by main on 14.09.17.
 */
public class PriorityExample {
    public static void main(String... args) throws  InterruptedException{

        for (int j= 0; j < 10; j++) {

            Thread[] thread = {new CommonRun("MIN"),
                    new CommonRun("NORM_1"),
                    new CommonRun("NORM_2"),
                    new CommonRun("MAX")};

            thread[0].setPriority(Thread.MIN_PRIORITY);
            thread[1].setPriority(Thread.NORM_PRIORITY);
            thread[2].setPriority(Thread.NORM_PRIORITY);
            thread[3].setPriority(Thread.MAX_PRIORITY);

            for (int i = 0; i < thread.length; i++) {
                thread[i].start();
            }

            for (int i = 0; i < thread.length; i++) {
                thread[i].join();
            }

            System.out.println();
        }

    }
}

