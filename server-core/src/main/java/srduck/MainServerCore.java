package srduck;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by igor on 08.07.2017.
 */

@SpringBootApplication
@ComponentScan({"srduck.controllers"})
public class MainServerCore {
    public static void main (String... args){// throws Exception {
//        MainCommon.main("MainCommon.main from MainServerCore.main");
        SpringApplication.run(MainServerCore.class);
    }
}
