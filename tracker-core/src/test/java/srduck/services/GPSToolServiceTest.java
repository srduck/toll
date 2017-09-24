package srduck.services;

import de.micromata.opengis.kml.v_2_2_0.Coordinate;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class GPSToolServiceTest {
    GPSToolService gpsToolService;

    @Before
    public void setUp() throws Exception {
        gpsToolService = new GPSToolService();
    }

    @Test
    public void bearing() throws Exception {

        //Восток
        double bearing = gpsToolService.bearing(0,0,0,90);
        assertEquals(90, bearing, 0);

        //Москва-Владивосток
        bearing = gpsToolService.bearing(55.753215,37.622504,43.115141,131.885341);
        assertEquals(59.45, bearing, 0.03);

    }

    @Test
    public void distance() throws Exception {

        double distance = 0;

        //Вверх на 125м. по вертикали
        distance = gpsToolService.distance(1,1,1,1,125,250);
        assertEquals(125, distance, 0);

        //Вниз на 125м. по вертикали
        distance = gpsToolService.distance(1,1,1,1,250,125);
        assertEquals(125, distance, 0);

        //Москва-Владивосток
        distance = gpsToolService.distance(55.753215,43.115141,37.622504,131.885341,149.3,6.8);
        assertEquals(6416,distance/1000, 0.7);
    }

    @Test
    public void getGps() throws Exception {

        Coordinate firstCoordinate = new Coordinate(85.73613100,51.76911200,1008.0000);
        Coordinate secondCoordinate = new Coordinate(85.73607800,51.76894000,1199.0000);
        Coordinate thirdCoordinate = new Coordinate(85.73616500,51.76899600,1207.0000);
        Coordinate beforeLastCoordinate = new Coordinate(87.27191000,51.79405300,1475.0000);
        Coordinate lastCoordinate = new Coordinate(87.27190600,51.79405300,1473.0000);

        ArrayList<Coordinate> list = gpsToolService.getGps();
        int length = list.size();

        assertEquals(12274, length );
        assertEquals(firstCoordinate, list.get(0));
        assertEquals(secondCoordinate, list.get(1));
        assertEquals(thirdCoordinate, list.get(2));
        assertEquals(beforeLastCoordinate, list.get(length - 2));
        assertEquals(lastCoordinate, list.get(length-1));

    }

}