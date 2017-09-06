package srduck.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Created by igor on 12.08.2017.
 */
@SpringBootApplication
@EnableJpaRepositories(basePackages = {"srduck.repositories"})
@EntityScan(basePackages={"srduck.dao","srduck.dto"})
@ComponentScan({"srduck.config","srduck.services"})
public class SecurityApplication {
    public static void main (String... args) throws Throwable{
        SpringApplication.run(SecurityApplication.class, args);
    }
}
