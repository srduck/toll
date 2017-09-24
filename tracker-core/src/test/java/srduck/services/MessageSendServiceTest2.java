package srduck.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import srduck.IntegrationTestMain;
import srduck.dto.PointDTO;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = IntegrationTestMain.class)
public class MessageSendServiceTest2 {
    Method send;
    Field privateTime;

    @Autowired
    MessageStorageService storageService;

    @Autowired
    MessageSendService sendService;

    @Before
    public void setup() throws Exception{

        for (int i = 1; i < 4; i++){
            PointDTO point = new PointDTO();
            point.setTrackerId("bb123cd");
            point.setTime(i);
            storageService.put(point);
        }

        send = MessageSendService.class.getDeclaredMethod("send");
        send.setAccessible(true);

        privateTime = MessageSendService.class.getDeclaredField("time");
        privateTime.setAccessible(true);

    }

    @Test
    public void sendServerCoreUp() throws Exception {
        send.invoke(sendService);
        long listTime = (long) privateTime.get(sendService);
        assertEquals(3, listTime);
    }


    @Mock
    RestTemplate mockRestTemplate;
    @Test
    public void sendServerCoreDownOrError() throws Exception{

        List<PointDTO> baseTail = storageService.getTail(0);
        RestTemplate restTemplate = new RestTemplate();
        MessageSendService mockSendService = new MessageSendService(storageService, mockRestTemplate);
        ResponseEntity<Boolean> answer;
        final String serverURL = "http://localhost:8080/coords";

        try {
            answer = restTemplate.exchange("http://localhost:8092/coords", HttpMethod.POST, getHttpEntity(baseTail.get(0)), Boolean.class);
        }
        catch (ResourceAccessException rax){
            answer = null;
        }

        if (answer == null){
            when(mockRestTemplate.exchange(serverURL, HttpMethod.POST, getHttpEntity(baseTail.get(0)), Boolean.class))
                    .thenThrow(ResourceAccessException.class);
        }
        else  if (!answer.getBody()) {
            when(mockRestTemplate.exchange(serverURL, HttpMethod.POST, getHttpEntity(baseTail.get(0)), Boolean.class))
                    .thenReturn(answer);
        }
        else {
            for (int i = 0; i < 3; i++) {
                when(mockRestTemplate.exchange(serverURL, HttpMethod.POST, getHttpEntity(baseTail.get(i)), Boolean.class))
                        .thenReturn(answer);
            }
        }

        send.invoke(mockSendService);
        long listTime = (long) privateTime.get(mockSendService);
        assertEquals(0, listTime);

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
