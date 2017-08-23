package srduck.services;

import org.junit.Before;
import org.junit.Test;
import srduck.dto.GPSRecordDTO;

import java.lang.reflect.Field;
import java.util.concurrent.BlockingDeque;

import static org.junit.Assert.*;

/**
 * Created by igor on 24.08.2017.
 */
public class MessageStorageServiceTest {

    MessageStorageService storageService;
    BlockingDeque<GPSRecordDTO> queue;
    GPSRecordDTO record;
    GPSRecordDTO actualRecord;

    @Before
    public void setUp() throws Exception {

        storageService = new MessageStorageService();
        Field privateQueue = MessageStorageService.class.getDeclaredField("queue");
        privateQueue.setAccessible(true);
        queue = (BlockingDeque<GPSRecordDTO>) privateQueue.get(storageService);

        record = new GPSRecordDTO();
        record.setLat(45.345);
        record.setLon(87.329);
        record.setInstSpeed(34.23);
        record.setBearing(135.97);
    }

    private void check(){

        assertEquals(record.getLon(), actualRecord.getLon(), 0);
        assertEquals(record.getLat(), actualRecord.getLat(), 0);
        assertEquals(record.getBearing(), actualRecord.getBearing(), 0);
        assertEquals(record.getInstSpeed(), actualRecord.getInstSpeed(), 0);
        if (queue.size() > 0) {
            queue.clear();
        }

    }

    @Test
    public void put() throws Exception {

        storageService.put(record);
        actualRecord = queue.take();
        check();
    }


    @Test
    public void getQueueSize() throws Exception {

        for (int i = 0; i < 35; i++) {
            queue.put(new GPSRecordDTO());
            queue.take();
            queue.put(new GPSRecordDTO());
            queue.putFirst(queue.take());
        }
        assertTrue(storageService.getQueueSize() == 35);
        queue.clear();
    }

    @Test
    public void take() throws Exception {

        queue.put(record);
        queue.put(new GPSRecordDTO());
        queue.put(new GPSRecordDTO());

        actualRecord = storageService.take();
        check();
    }

    @Test
    public void putFirst() throws Exception {

        queue.put(record);
        queue.putFirst(new GPSRecordDTO());
        queue.putLast(new GPSRecordDTO());
        queue.take();
        storageService.putFirst(queue.take());
        queue.put(new GPSRecordDTO());

        actualRecord = queue.take();
        check();

    }

}