package srduck;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import srduck.services.GPSService;
import srduck.services.GPSToolService;
import srduck.services.MessageSendService;
import srduck.services.MessageStorageService;

/**
 * Created by igor on 21.07.2017.
 */
@Configuration
@EnableScheduling
@PropertySource("classpath:/app.properties")
public class GPSContext {

    @Bean
    GPSService gpsService(){
        return new GPSService();
    }

    @Bean
    GPSToolService gpsToolService(){
        return new GPSToolService();
    }

    @Bean
    MessageSendService messageSendService(){
        return new MessageSendService();
    }

    @Bean
    MessageStorageService messageStorageService(){
        return new MessageStorageService();
    }

    @Bean
    public TaskScheduler poolSheduler(){
        ThreadPoolTaskScheduler sheduler = new ThreadPoolTaskScheduler();
        sheduler.setThreadNamePrefix("poolSheduler");
        sheduler.setPoolSize(20);
        return sheduler;
    }
}
