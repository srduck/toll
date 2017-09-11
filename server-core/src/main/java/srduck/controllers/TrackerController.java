package srduck.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import srduck.dto.PointDTO;
import srduck.requests.ConstraintTrack;
import srduck.services.PointDTOService;

import java.util.Collections;
import java.util.List;

/**
 * Created by igor on 05.08.2017.
 */
@RestController
public class TrackerController {

    private static Logger log = LoggerFactory.getLogger(TrackerController.class);

    @Autowired
    PointDTOService pointDTOService;

    @RequestMapping(value = "/coords", method = RequestMethod.POST, consumes = "application/json")
    public Boolean getCoordinates (@RequestBody String jsonPointDTO){
        try {
            ObjectMapper mapper = new ObjectMapper();
            PointDTO pointDTO = mapper.readValue(jsonPointDTO, PointDTO.class);
            log.info("getCoordinates: lat = " + pointDTO.getLat() + "; lon = " + pointDTO.getLon());
            PointDTO savePointDTO = pointDTOService.create(pointDTO);
            log.info("saved coordinate: id = " + savePointDTO.getTrackerId() + "; lat = " + savePointDTO.getLat() + "; lon = " + savePointDTO.getLon());
            return true;
        }
        catch (Exception e){
            e.getStackTrace();
            return false;
        }
    }

    @CrossOrigin(origins = "http://localhost:8092", maxAge = 3600)
    @RequestMapping(value="/track", method = RequestMethod.POST, produces ="application/json")
    public List<PointDTO> getLastNRecords(@RequestBody String jsonTrack){
        try {
            ObjectMapper mapper = new ObjectMapper();
            ConstraintTrack constraintTrack = mapper.readValue(jsonTrack, ConstraintTrack.class);
            List<PointDTO> points =  pointDTOService.getLastNRecords(constraintTrack.getTrackerId(),constraintTrack.getPointCount());
            Collections.reverse(points);
            return points;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
