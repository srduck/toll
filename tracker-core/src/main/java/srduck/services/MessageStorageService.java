package srduck.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import srduck.dto.PointDTO;

import java.util.List;


/**
 * Created by igor on 21.07.2017.
 */
@Service
public class MessageStorageService {

    @Autowired
    PointDTOService pointService;

    private static Logger log = LoggerFactory.getLogger(GPSService.class);


    public void put(PointDTO recordDTO) throws InterruptedException {

        pointService.create(recordDTO);
        log.info("MessageStorageService.put " + recordDTO.getLat());

    }

    public List<PointDTO> getTail(long time){
        return pointService.findByTimeGreaterThan(time);
    }


}
