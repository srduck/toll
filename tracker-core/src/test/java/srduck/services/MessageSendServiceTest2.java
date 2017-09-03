package srduck.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import srduck.dto.Coordinates;
import srduck.dto.GPSRecordDTO;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.concurrent.BlockingDeque;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Created by igor on 25.08.2017.
 */
@RunWith(MockitoJUnitRunner.Silent.class)
public class MessageSendServiceTest2 {

    MessageStorageService storageService;
    BlockingDeque<GPSRecordDTO> queue;
    Method send;
    RestTemplate restTemplate;
    GPSRecordDTO recordOne;
    GPSRecordDTO recordTwo;
    GPSRecordDTO recordThree;

   @Before
   public void setup() throws Exception{

       restTemplate = new RestTemplate();
       storageService = new MessageStorageService();

       recordOne = new GPSRecordDTO();
       recordOne.setLat(51.769112);
       recordOne.setLon(85.736131);

       recordTwo = new GPSRecordDTO();
       recordTwo.setLat(51.76894);
       recordTwo.setLon(85.736078);

       recordThree = new GPSRecordDTO();
       recordThree.setLat(51.768996);
       recordThree.setLon(85.736165);

       storageService.put(recordOne);
       storageService.put(recordTwo);
       storageService.put(recordThree);

       Field privateQueue = MessageStorageService.class.getDeclaredField("queue");
       privateQueue.setAccessible(true);
       queue = (BlockingDeque<GPSRecordDTO>) privateQueue.get(storageService);

       send = MessageSendService.class.getDeclaredMethod("send");
       send.setAccessible(true);
   }

    @Test
    public void sendServerCoreUp() throws Exception {
        MessageSendService sendService = new MessageSendService(storageService, restTemplate);
        send.invoke(sendService);
        assertEquals(0, queue.size());

    }


    @Mock
    RestTemplate mockRestTemplate;
    @Test
    public void sendServerCoreDownOrError() throws Exception{

        MessageSendService sendService = new MessageSendService(storageService, mockRestTemplate);
        ResponseEntity<Boolean> answer;
        final String localhostURL = "http://localhost:8080/coords";

        try {
            answer = restTemplate.exchange("http://localhost:8092/coords", HttpMethod.POST, getHttpEntity(recordOne), Boolean.class);
        }
        catch (ResourceAccessException rax){
            answer = null;
        }

        if (answer == null){
            when(mockRestTemplate.exchange(localhostURL, HttpMethod.POST, getHttpEntity(recordOne), Boolean.class))
                    .thenThrow(ResourceAccessException.class);
        }
        else  if (!answer.getBody()) {
            when(mockRestTemplate.exchange(localhostURL, HttpMethod.POST, getHttpEntity(recordOne), Boolean.class))
                    .thenReturn(answer);
        }
        else {
            when(mockRestTemplate.exchange(localhostURL, HttpMethod.POST, getHttpEntity(recordOne), Boolean.class))
                    .thenReturn(answer);
            when(mockRestTemplate.exchange(localhostURL, HttpMethod.POST, getHttpEntity(recordTwo), Boolean.class))
                    .thenReturn(answer);
            when(mockRestTemplate.exchange(localhostURL, HttpMethod.POST, getHttpEntity(recordThree), Boolean.class))
                    .thenReturn(answer);
        }

        send.invoke(sendService);

        assertEquals(3, queue.size());
        assertEquals(recordOne.getLat(), queue.getFirst().getLat(),0);
        assertEquals(recordOne.getLon(), queue.getFirst().getLon(), 0);
        assertEquals(recordThree.getLat(), queue.getLast().getLat(), 0);
        assertEquals(recordThree.getLon(), queue.getLast().getLon(), 0);

    }

    private HttpEntity<String> getHttpEntity(GPSRecordDTO record){
        Coordinates coordinates = new Coordinates();
        coordinates.setLon(record.getLon());
        coordinates.setLat(record.getLat());

        String requestJson = null;
        try {
            requestJson = coordinates.toJson();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> coord = new HttpEntity<String>(requestJson, headers);
        return coord;
    }

}
