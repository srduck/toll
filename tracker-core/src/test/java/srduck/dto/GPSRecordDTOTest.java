package srduck.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.*;

/**
 * Created by igor on 24.08.2017.
 */
public class GPSRecordDTOTest {

    private final double lon = 74;
    private final double lat = 56;
    private final double bearing = 345.34;
    private final double instSpeed = 125.3;
    private final String expected = "{\"lat\":56.0,\"lon\":74.0,\"bearing\":345.34,\"instSpeed\":125.3}";

    @Test
    public void toJson() throws Exception {
        GPSRecordDTO record = new GPSRecordDTO();
        record.setLat(lat);
        record.setLon(lon);
        record.setBearing(bearing);
        record.setInstSpeed(instSpeed);
        assertTrue(record.toJson().contains("\"lat\":56"));
        assertTrue(record.toJson().contains("\"lon\":74"));
        assertTrue(record.toJson().contains("\"bearing\":345.34"));
        assertTrue(record.toJson().contains("\"instSpeed\":125.3"));
    }

    @Test
    public void decodeDTO() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        GPSRecordDTO dto = mapper.readValue(expected, GPSRecordDTO.class);
        assertEquals(lon, dto.getLon());
        assertEquals(lat, dto.getLat());
        assertEquals(bearing, dto.getBearing());
        assertEquals(instSpeed, dto.getInstSpeed());

    }

}