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
import srduck.dto.PointDTO;

import java.util.List;


/**
 * Created by igor on 21.07.2017.
 */
@Service
public class MessageSendService {

    private static Logger log = LoggerFactory.getLogger(MessageSendService.class);


    private final MessageStorageService messageStorageService;
    private final RestTemplate restTemplate;
    private long time;

    public MessageSendService(@Autowired MessageStorageService messageStorageService,
                              @Autowired RestTemplate restTemplate){
        this.messageStorageService = messageStorageService;
        this.restTemplate = restTemplate;
    }

    @Scheduled(cron = "${cron.prop.get}")
    private void send () throws InterruptedException{

        List<PointDTO> baseTail = messageStorageService.getTail(time);

        for (int i = 0; i < baseTail.size(); i++){
            PointDTO record = baseTail.get(i);
            try {

                String requestJson = record.toJson();

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                HttpEntity<String> coord = new HttpEntity<String>(requestJson, headers);

                ResponseEntity<Boolean> answer = restTemplate.exchange("http://localhost:8080/coords", HttpMethod.POST, coord, Boolean.class);

                if(!answer.getBody()){
                    log.info("Server Error");
                    return;
                }
                time = record.getTime();
                log.info(record.toJson());

            }
            catch (JsonProcessingException jpe) {
                jpe.printStackTrace();
            }
            catch (ResourceAccessException ex){
                log.info("ResourceAccessException");
                return;
            }
        }
    }


}
