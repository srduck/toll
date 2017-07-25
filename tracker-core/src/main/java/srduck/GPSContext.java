package srduck;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * Created by igor on 21.07.2017.
 */
@Configuration
@EnableScheduling
@ComponentScan("srduck.services")
@PropertySource("classpath:/app.properties")
public class GPSContext {

    @Bean
    public TaskScheduler poolSheduler(){
        ThreadPoolTaskScheduler sheduler = new ThreadPoolTaskScheduler();
        sheduler.setThreadNamePrefix("poolSheduler");
        sheduler.setPoolSize(20);
        return sheduler;
    }
}
