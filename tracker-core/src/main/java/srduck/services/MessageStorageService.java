package srduck.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import srduck.dto.GPSRecordDTO;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by igor on 21.07.2017.
 */
public class MessageStorageService {
    private static Logger log = LoggerFactory.getLogger(GPSService.class);

    private BlockingDeque<GPSRecordDTO> queue = new LinkedBlockingDeque<>(100);

    //Эмулируем базу данных если необходимо
//    private LinkedList<GPSRecordDTO> aLaDB = new LinkedList<GPSRecordDTO>();

    public void put(GPSRecordDTO recordDTO) throws InterruptedException {

        GPSRecordDTO record = new GPSRecordDTO();

        record.setInstSpeed(recordDTO.getInstSpeed());
        record.setBearing(recordDTO.getBearing());
        record.setLon(recordDTO.getLon());
        record.setLat(recordDTO.getLat());
//        log.info("MessageStorageService.put " + recordDTO.getLat());
        queue.put(record);

    }

    public List<GPSRecordDTO> take () throws InterruptedException {
        List<GPSRecordDTO> sendedRecords = new LinkedList<GPSRecordDTO>();
        GPSRecordDTO record;
        for(GPSRecordDTO rec : queue){
            record = queue.take();
            sendedRecords.add(record);
            //Эмуляция БД
//            aLaDB.add(record);
//            log.info("MessageStorageService.take " + record.getLat());
        }
        return sendedRecords;
    }
}
