package srduck.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import srduck.dto.Coordinates;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;

/**
 * Created by igor on 05.08.2017.
 */
@RestController
public class TrackerController {

    private static Logger log = LoggerFactory.getLogger(TrackerController.class);

    @RequestMapping(value = "/coords", method = RequestMethod.POST, consumes = "application/json")
    public Boolean getCoordinates (@RequestBody String jsonCoordinates){
        try {
            ObjectMapper mapper = new ObjectMapper();
            Coordinates coordinates = mapper.readValue(jsonCoordinates, Coordinates.class);
            log.info("getCoordinates: lat = " + coordinates.getLat() + "; lon = " + coordinates.getLon());
            return true;
        }
        catch (Exception e){
            e.getStackTrace();
            return false;
        }
    }
}
