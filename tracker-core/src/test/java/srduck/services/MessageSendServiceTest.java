package srduck.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
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

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

/**
 * Created by igor on 24.08.2017.
 */
@RunWith(MockitoJUnitRunner.Silent.class)
public class MessageSendServiceTest {

    @Mock
    MessageStorageService messageStorageService;
    @Mock
    RestTemplate restTemplate;
    @InjectMocks
    MessageSendService sendService;

    @Test
    public void send() throws Exception {

        MessageStorageService storageService = new MessageStorageService();
        BlockingDeque<GPSRecordDTO> queue;

        Field privateQueue = MessageStorageService.class.getDeclaredField("queue");
        privateQueue.setAccessible(true);
        queue = (BlockingDeque<GPSRecordDTO>) privateQueue.get(storageService);

        GPSRecordDTO recordOne = new GPSRecordDTO();
        recordOne.setLat(51.769112);
        recordOne.setLon(85.736131);

        GPSRecordDTO recordTwo = new GPSRecordDTO();
        recordTwo.setLat(51.76894);
        recordTwo.setLon(85.736078);

        GPSRecordDTO recordThree = new GPSRecordDTO();
        recordThree.setLat(51.768996);
        recordThree.setLon(85.736165);

        storageService.put(recordOne);
        storageService.put(recordTwo);
        storageService.put(recordThree);

        ResponseEntity<Boolean> goodAnswer = new ResponseEntity<Boolean>(true, HttpStatus.OK);
        ResponseEntity<Boolean> badAnswer = new ResponseEntity<Boolean>(false, HttpStatus.OK);

        when(messageStorageService.take()).thenReturn(storageService.take());

        // Хороший ответ
        when(restTemplate.exchange("http://localhost:8080/coords", HttpMethod.POST, getHttpEntity(recordOne), Boolean.class))
                .thenReturn(goodAnswer);

        when(restTemplate.exchange("http://localhost:8080/coords", HttpMethod.POST, getHttpEntity(recordTwo), Boolean.class))
                // Что-то не так
                .thenReturn(badAnswer)
                // Сервер не отвечает
                .thenThrow(ResourceAccessException.class);

        Method send = MessageSendService.class.getDeclaredMethod("send");
        send.setAccessible(true);

        for (int i = 0; i < 3; i++) {
            send.invoke(sendService);
            assertEquals(recordTwo.getLat(), queue.getFirst().getLat(), 0);
            assertEquals(recordTwo.getLon(), queue.getFirst().getLon(), 0);
            assertEquals(recordThree.getLat(), queue.getLast().getLat(), 0);
            assertEquals(recordThree.getLon(), queue.getLast().getLon(), 0);
        }
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