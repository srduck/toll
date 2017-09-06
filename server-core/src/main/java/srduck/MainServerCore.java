package srduck;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.web.context.request.RequestContextListener;

/**
 * Created by igor on 08.07.2017.
 */

@SpringBootApplication
@ComponentScan({"srduck.controllers","srduck.repositories","srduck.dao","srduck.services"})
public class MainServerCore {
    public static void main (String... args){
        SpringApplication.run(MainServerCore.class);
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer placeholderConfigurer(){
        return new PropertySourcesPlaceholderConfigurer();
    }

}
