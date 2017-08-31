package srduck.services;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;
import srduck.dto.GPSRecordDTO;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.concurrent.BlockingDeque;

import static org.junit.Assert.assertEquals;

/**
 * Created by igor on 25.08.2017.
 */
public class MessageSendServiceTest2 {

    MessageStorageService storageService;
    RestTemplate restTemplate;
    MessageSendService sendService;
    BlockingDeque<GPSRecordDTO> queue;
    GPSRecordDTO recordOne;
    GPSRecordDTO recordTwo;
    GPSRecordDTO recordThree;
    Method send;

    @Before
    public void setup () throws Exception{
        storageService = new MessageStorageService();
        restTemplate = new RestTemplate();
        sendService = new MessageSendService(storageService, restTemplate);
        Field privateQueue = MessageStorageService.class.getDeclaredField("queue");
        privateQueue.setAccessible(true);
        queue = (BlockingDeque<GPSRecordDTO>) privateQueue.get(storageService);

        send = MessageSendService.class.getDeclaredMethod("send");
        send.setAccessible(true);

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

        send.invoke(sendService);
    }

    @Test
    public void sendServerCoreUp() throws Exception {

        assertEquals(0, queue.size());

    }

    @Test
    @Ignore("Checking in case if server is down or error")
    public void sendServerCoreDownOrError() throws Exception{

        assertEquals(3, queue.size());
        assertEquals(recordOne.getLat(), queue.getFirst().getLat(),0);
        assertEquals(recordOne.getLon(), queue.getFirst().getLon(), 0);
        assertEquals(recordThree.getLat(), queue.getLast().getLat(), 0);
        assertEquals(recordThree.getLon(), queue.getLast().getLon(), 0);

    }

}
