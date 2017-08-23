package srduck.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import srduck.dto.GPSRecordDTO;

import java.lang.reflect.Method;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

/**
 * Created by igor on 24.08.2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class GPSServiceTest {

    @Mock
    MessageStorageService messageStorageService;
    @Mock
    GPSToolService gpsToolService;
    @InjectMocks
    GPSService service;

    @Test
    public void track() throws Exception{

        GPSToolService toolService = new GPSToolService();

        GPSRecordDTO recordOne = new GPSRecordDTO();
        recordOne.setLat(51.769112);
        recordOne.setLon(85.736131);

        GPSRecordDTO recordTwo = new GPSRecordDTO();
        recordTwo.setLat(51.76894);
        recordTwo.setLon(85.736078);

        GPSRecordDTO recordThree = new GPSRecordDTO();
        recordThree.setLat(51.768996);
        recordThree.setLon(85.736165);

        //Азимут Москва-Владивосток
        double bearing =  59.45;
        double instSpeed = 2099567;

        when(gpsToolService.getGps()).thenReturn(toolService.getGps());
        when(gpsToolService.bearing(anyDouble(), anyDouble(), anyDouble(), anyDouble()))
                .thenReturn(toolService.bearing(55.753215,37.622504,43.115141,131.885341));
        when(gpsToolService.distance(anyDouble(), anyDouble(), anyDouble(), anyDouble(),anyDouble(),anyDouble()))
                .thenReturn(toolService.distance(55.753215,43.115141,37.622504,131.885341,149.3,6.8));

        doAnswer(new Answer() {
            public GPSRecordDTO answer(InvocationOnMock invocation) {
                GPSRecordDTO recordDTO = (GPSRecordDTO) invocation.getArgument(0);
                assertEquals(recordOne.getLat(), recordDTO.getLat(),0);
                assertEquals(recordOne.getLon(), recordDTO.getLon(),0);
                assertEquals(bearing, recordDTO.getBearing(), 0.3);
                assertEquals(instSpeed, recordDTO.getInstSpeed(), 0.3);
                return null;
            }
        })
                .doAnswer(new Answer() {
                    public GPSRecordDTO answer(InvocationOnMock invocation) {
                        GPSRecordDTO recordDTO = (GPSRecordDTO) invocation.getArgument(0);
                        assertEquals(recordTwo.getLat(), recordDTO.getLat(),0);
                        assertEquals(recordTwo.getLat(), recordDTO.getLat(),0);
                        assertEquals(bearing, recordDTO.getBearing(), 0.3);
                        assertEquals(instSpeed, recordDTO.getInstSpeed(), 0.3);
                        return null;
                    }
                })
                .doAnswer(new Answer() {
                    public GPSRecordDTO answer(InvocationOnMock invocation) {
                        GPSRecordDTO recordDTO = (GPSRecordDTO) invocation.getArgument(0);
                        assertEquals(recordThree.getLat(), recordDTO.getLat(),0);
                        assertEquals(recordThree.getLat(), recordDTO.getLat(),0);
                        assertEquals(bearing, recordDTO.getBearing(), 0.3);
                        assertEquals(instSpeed, recordDTO.getInstSpeed(), 0.3);
                        return null;
                    }
                })
                .when(messageStorageService).put(any(GPSRecordDTO.class));

        Method init = GPSService.class.getDeclaredMethod("init");
        init.setAccessible(true);
        init.invoke(service);

        Method track = GPSService.class.getDeclaredMethod("track");
        track.setAccessible(true);
        for (int i = 0; i < 3; i++) {
            track.invoke(service);
        }
    }

}