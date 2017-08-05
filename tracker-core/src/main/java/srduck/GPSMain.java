package srduck;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.client.RestTemplate;

/**
 * Created by igor on 21.07.2017.
 */

@SpringBootApplication
@EnableScheduling
@ComponentScan({"srduck.services", "srduck.controllers"})
@PropertySource("classpath:/app.properties")
public class GPSMain {
    public static void main (String... args) {
//        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(GPSContext.class);
        SpringApplication.run(GPSMain.class);
    }

    @Bean
    public TaskScheduler poolSheduler(){
        ThreadPoolTaskScheduler sheduler = new ThreadPoolTaskScheduler();
        sheduler.setThreadNamePrefix("poolSheduler");
        sheduler.setPoolSize(20);
        return sheduler;
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder){
        return builder.build();
    }

}
