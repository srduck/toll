package srduck.services;

import com.fasterxml.jackson.core.JsonProcessingException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import srduck.dto.PointDTO;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.LinkedList;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class MessageSendServiceTest {

    private static Logger log = LoggerFactory.getLogger(MessageSendService.class);

    @Mock
    MessageStorageService messageStorageService;
    @Mock
    RestTemplate restTemplate;
    @InjectMocks
    MessageSendService sendService;

    @Test
    public void send() throws Exception{

        LinkedList<PointDTO> list = new LinkedList<>();

        for (int i = 1; i < 4; i++){
            PointDTO point = new PointDTO();
            point.setTrackerId("ab123cd");
            point.setTime(i);
            messageStorageService.put(point);
            list.add(point);
        }

        Method send = MessageSendService.class.getDeclaredMethod("send");
        send.setAccessible(true);

        Field privateTime = MessageSendService.class.getDeclaredField("time");
        privateTime.setAccessible(true);

        ResponseEntity<Boolean> goodAnswer = new ResponseEntity<Boolean>(true, HttpStatus.OK);
        ResponseEntity<Boolean> badAnswer = new ResponseEntity<Boolean>(false, HttpStatus.OK);


        // Хороший ответ
        when(restTemplate.exchange("http://localhost:8080/coords", HttpMethod.POST, getHttpEntity(list.get(0)), Boolean.class))
                .thenReturn(goodAnswer);

        when(restTemplate.exchange("http://localhost:8080/coords", HttpMethod.POST, getHttpEntity(list.get(1)), Boolean.class))
                // Что-то не так
                .thenReturn(badAnswer)
                // Сервер не отвечает
                .thenThrow(ResourceAccessException.class);


        for (int i = 0; i < 2; i++) {
            when(messageStorageService.getTail(anyLong()))
                    .thenReturn(list.subList(((Long)privateTime.get(sendService)).intValue(), list.size()));
            send.invoke(sendService);
        }

        long listTime = (long) privateTime.get(sendService);
        assertEquals(1, listTime);

    }

    private HttpEntity<String> getHttpEntity(PointDTO record){

        String requestJson = null;
        try {
            requestJson = record.toJson();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> coord = new HttpEntity<String>(requestJson, headers);
        return coord;
    }

}