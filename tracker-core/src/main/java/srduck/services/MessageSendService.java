package srduck.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import srduck.dto.GPSRecordDTO;

import java.util.List;

/**
 * Created by igor on 21.07.2017.
 */
@Service
public class MessageSendService {
    private static Logger log = LoggerFactory.getLogger(MessageSendService.class);

    @Autowired
    MessageStorageService messageStorageService;

    @Scheduled(cron = "${cron.prop.get}")
    private void send () throws InterruptedException{

        List<GPSRecordDTO> recordsDTO = messageStorageService.take();

        if (recordsDTO != null && recordsDTO.size() > 0){
            for(GPSRecordDTO record : recordsDTO){
                try {
                    log.info(record.toJson());

                } catch (JsonProcessingException jpe) {
                    jpe.printStackTrace();
                }
            }
        }

    }
}
