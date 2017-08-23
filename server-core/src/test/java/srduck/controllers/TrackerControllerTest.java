package srduck.controllers;

import org.junit.After;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

/**
 * Created by igor on 24.08.2017.
 */
public class TrackerControllerTest {

    @Test
    public void getCoordinates() throws Exception {

        TrackerController trackerController = new TrackerController();
        trackerController.init();
        assertTrue(trackerController.getCoordinates("{\"lat\":47.345,\"lon\":54.3345}"));
        assertFalse(trackerController.getCoordinates("I'm your nightmare, controller!"));
        assertFalse(trackerController.getCoordinates(null));
        trackerController.distroy();
    }

    @After
    public void tearDown() throws Exception{

        File file = new File("coordinates.txt");
        if (file.exists()) {
            file.delete();
        }
    }

}