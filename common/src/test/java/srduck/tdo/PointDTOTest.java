package srduck.tdo;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by igor on 07.07.2017.
 */
public class PointDTOTest {

    @Test
    public void toJson() throws Exception{
        PointDTO point = new PointDTO();
        point.setLat(56);
        point.setLon(74);
        point.setAutoId("o567gfd");
        assertTrue(point.toJson().contains("\"lat\":56"));
        System.out.println(point.toJson());
    }

}
