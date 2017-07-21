package srduck;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by igor on 21.07.2017.
 */
public class GPSMain {
    ApplicationContext applicationContext = new AnnotationConfigApplicationContext(GPSContext.class);
}
