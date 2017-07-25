package srduck.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import srduck.dto.GPSRecordDTO;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by igor on 21.07.2017.
 */
@Service
public class MessageStorageService {

    private static Logger log = LoggerFactory.getLogger(GPSService.class);

    private BlockingDeque<GPSRecordDTO> queue = new LinkedBlockingDeque<>(100);

    public void put(GPSRecordDTO recordDTO) throws InterruptedException {

        GPSRecordDTO record = new GPSRecordDTO();

        record.setInstSpeed(recordDTO.getInstSpeed());
        record.setBearing(recordDTO.getBearing());
        record.setLon(recordDTO.getLon());
        record.setLat(recordDTO.getLat());
        log.info("MessageStorageService.put " + recordDTO.getLat());
        queue.put(record);

    }

    public int getQueueSize() {
        return queue.size();
    }

    public GPSRecordDTO take() throws InterruptedException {
        return queue.take();
    }
}
