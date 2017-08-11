package srduck.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by igor on 12.08.2017.
 */
@SpringBootApplication
@ComponentScan("srduck.config")
public class SecurityApplication {
    public static void main (String... args) throws Throwable{
        SpringApplication.run(SecurityApplication.class, args);
    }
}
