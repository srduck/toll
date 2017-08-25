package srduck.services;

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

    @Test
    public void send() throws Exception {

        MessageStorageService storageService = new MessageStorageService();
        RestTemplate restTemplate = new RestTemplate();
        MessageSendService sendService = new MessageSendService(storageService, restTemplate);

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


        Method send = MessageSendService.class.getDeclaredMethod("send");
        send.setAccessible(true);

        byte status = (byte) send.invoke(sendService);
        if (status == 0){
            assertEquals(0, queue.size());
        }
        else if (status == -1) {
            assertEquals(3, queue.size());
            assertEquals(recordOne.getLat(), queue.getFirst().getLat(),0);
            assertEquals(recordOne.getLon(), queue.getFirst().getLon(), 0);
            assertEquals(recordThree.getLat(), queue.getLast().getLat(), 0);
            assertEquals(recordThree.getLon(), queue.getLast().getLon(), 0);
        }
    }

}
