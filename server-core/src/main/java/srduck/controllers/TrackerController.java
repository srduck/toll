package srduck.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import srduck.dto.PointDTO;
import srduck.services.PointDTOService;

/**
 * Created by igor on 05.08.2017.
 */
@RestController
public class TrackerController {

    long i;
    private static Logger log = LoggerFactory.getLogger(TrackerController.class);

    @Autowired
    PointDTOService pointDTOService;
   /* private PrintWriter printWriter;

    @PostConstruct
    public void init(){

        try {
            printWriter = new PrintWriter("coordinates.txt");
        }
        catch(FileNotFoundException nfe) {
            nfe.getMessage();
        }

    }*/

    @RequestMapping(value = "/coords", method = RequestMethod.POST, consumes = "application/json")
    public Boolean getCoordinates (@RequestBody String jsonCoordinates){
        try {
            ObjectMapper mapper = new ObjectMapper();
            PointDTO pointDTO = mapper.readValue(jsonCoordinates, PointDTO.class);
            log.info("getCoordinates: lat = " +pointDTO.getLat() + "; lon = " + pointDTO.getLon());
           /* printWriter.println(coordinates.getLon() + "," + coordinates.getLat());
            printWriter.flush();*/
            pointDTO.setTime(i++);
            pointDTO.setTrackerId("46-64sgo");
            PointDTO savePointDTO = pointDTOService.create(pointDTO);
            log.info("saved coordinates: id = " + savePointDTO.getTrackerId() + "; lat = " + savePointDTO.getLat() + "; lon = " + savePointDTO.getLon());
            return true;
        }
        catch (Exception e){
            e.getStackTrace();
            return false;
        }
    }

   /* @PreDestroy
    public void distroy(){
        printWriter.close();
    }*/
}
