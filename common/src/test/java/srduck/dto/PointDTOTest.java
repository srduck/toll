package srduck.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.*;

public class PointDTOTest {


    @Test
    public void toJson() throws Exception{
        PointDTO point = new PointDTO();
        point.setLat(56);
        point.setLon(74);
        point.setTrackerId("o567gfd");
        point.setTime(System.currentTimeMillis());
        assertTrue(point.toJson().contains("\"lat\":56"));
        assertTrue(point.toJson().contains("\"time\":"));
        System.out.println(point.toJson()) ;
    }

    @Test
    public void decodeDTO() throws Exception{
        final String expected = "{\"lat\":56.0,\"lon\":74.0,\"trackerId\":\"o567gfd\",\"time\":1499366527878}";
        final String trackerId = "o567gfd";
        ObjectMapper mapper = new ObjectMapper();
        PointDTO dto = mapper.readValue(expected, PointDTO.class);
        assertEquals(trackerId, dto.getTrackerId());
        assertEquals(1499366527878L, dto.getTime());
    }

}