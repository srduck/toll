package srduck.yield;

/**
 * Created by main on 14.09.17.
 */
public class YieldExample {
    public static void main(String... args) throws  InterruptedException{

        for (int j= 0; j < 10; j++) {

            Thread[] thread = {new YieldRun("YIELD"),
                    new CommonRun("first"),
                    new CommonRun("second"),
                    new CommonRun("third")};

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

