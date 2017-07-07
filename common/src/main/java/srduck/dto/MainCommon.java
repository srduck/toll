package srduck.dto;

/**
 * Created by igor on 08.07.2017.
 */
public class MainCommon {
    public static void main(String... args) throws Exception {
        for (int i=0; i<5; i++) {
            if (args.length != 1) {
                System.out.println("Main.main from common");
            }
            else {
                System.out.println(args[0]);
            }
            PointDTO point = new PointDTO();
            point.setLat(45);
            System.out.println(point.toJson());
            Thread.sleep(1000);
        }
    }
}
