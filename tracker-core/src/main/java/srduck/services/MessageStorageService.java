package srduck.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import srduck.dto.GPSRecordDTO;
import srduck.dto.IdPointDTO;
import srduck.dto.PointDTO;

import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by igor on 21.07.2017.
 */
@Service
public class MessageStorageService {

    @Autowired
    PointDTOService pointService;
   // private IdPointDTO lastSentPoint;
    private static Logger log = LoggerFactory.getLogger(GPSService.class);

    //private BlockingDeque<GPSRecordDTO> queue = new LinkedBlockingDeque<>(100);

    /*public void setLastSentPoint(IdPointDTO lastSentPoint) {
        this.lastSentPoint = lastSentPoint;
    }

    public IdPointDTO getLastSentPoint(){
        return this.lastSentPoint;
    }*/



    public void put(PointDTO recordDTO) throws InterruptedException {

//        GPSRecordDTO record = new GPSRecordDTO();

       /* record.setInstSpeed(recordDTO.getInstSpeed());
        record.setBearing(recordDTO.getBearing());
        record.setLon(recordDTO.getLon());
        record.setLat(recordDTO.getLat());*/
        pointService.create(recordDTO);
        log.info("MessageStorageService.put " + recordDTO.getLat());

        //queue.put(record);

    }

    public List<PointDTO> getTail(long time){
        return pointService.findByTimeGreaterThan(time);
    }

   /* public int getQueueSize() {
        return queue.size();
    }



    public GPSRecordDTO take() throws InterruptedException {
        return queue.take();
    }

    public void putFirst(GPSRecordDTO recordDTO) throws  InterruptedException{
        queue.putFirst(recordDTO);
    }*/

}
