package srduck.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.ResourceAccessException;
import srduck.dto.Coordinates;
import srduck.dto.GPSRecordDTO;


/**
 * Created by igor on 21.07.2017.
 */
@Service
public class MessageSendService {

    private static Logger log = LoggerFactory.getLogger(MessageSendService.class);


    private final MessageStorageService messageStorageService;
    private final RestTemplate restTemplate;

    public MessageSendService(@Autowired MessageStorageService messageStorageService,
                              @Autowired RestTemplate restTemplate){
        this.messageStorageService = messageStorageService;
        this.restTemplate = restTemplate;
    }

    @Scheduled(cron = "${cron.prop.get}")
    private byte send () throws InterruptedException{

        int messageQuantity = messageStorageService.getQueueSize();

        for(int i = 0; i < messageQuantity; i++){

            GPSRecordDTO record = messageStorageService.take();

            try {
                Coordinates coordinates = new Coordinates();
                coordinates.setLon(record.getLon());
                coordinates.setLat(record.getLat());

                String requestJson = coordinates.toJson();

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                HttpEntity<String> coord = new HttpEntity<String>(requestJson, headers);

                ResponseEntity<Boolean> answer = restTemplate.exchange("http://localhost:8080/coords", HttpMethod.POST, coord, Boolean.class);

                if(!answer.getBody()){
                    returnToQueue(record, "Server Error");
                    return 1;
                }


                log.info(record.toJson());

            }
            catch (JsonProcessingException jpe) {
                jpe.printStackTrace();
            }
            catch (ResourceAccessException ex){
                returnToQueue(record, "ResourceAccessException");
                return -1;
            }

        }
        return 0;
    }

    private void returnToQueue(GPSRecordDTO record, String error) throws InterruptedException{
        log.info("Returning of coordinates to queue : " + error);
        messageStorageService.putFirst(record);
    }

}
