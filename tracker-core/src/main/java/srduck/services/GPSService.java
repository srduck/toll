package srduck.services;

import de.micromata.opengis.kml.v_2_2_0.Coordinate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import srduck.dto.GPSRecordDTO;

import javax.annotation.PostConstruct;
import java.util.ArrayList;

/**
 * Created by igor on 21.07.2017.
 */
@Service
public class GPSService {
    //Алтай, Чуйский, Чемальский тракты, озеро Телецкое
    //https://www.gpslib.ru/tracks/info/10164.html
    //Среднее врмемя между передачей координат
    private static final double MEDIUM_TIME = (37*3600+41*60+22)/12274;
    //Отношение фута к метру
    private static final double FOOT_TO_METER = 0.3048;

    private static Logger log = LoggerFactory.getLogger(GPSService.class);

    private ArrayList<Coordinate> coordinateList;
    private int count;
    private int size;
    private double oldLat;
    private double oldLong;
    private double oldAltitude;
    private GPSRecordDTO record;

    @Autowired
    GPSToolService toolService;

    @Autowired
    MessageStorageService messageStorageService;

    @PostConstruct
    private void init(){
        coordinateList = toolService.getGps();
        size = coordinateList.size();
        oldLat = coordinateList.get(0).getLatitude();
        oldLong = coordinateList.get(0).getLongitude();
        record = new GPSRecordDTO();
        oldAltitude = coordinateList.get(0).getAltitude() * FOOT_TO_METER;
    }

    @Scheduled(cron = "${cron.prop.put}")
    private void track(){
        double currentLat = 0;
        double currentLong = 0;
        double currentAltitude = 0;
        double bearing = 0;
        double distance = 0;
        double instSpeed = 0;
        if (size > count) {
            Coordinate coordinate = coordinateList.get(count);
            currentLat = coordinate.getLatitude();
            currentLong = coordinate.getLongitude();
            currentAltitude = coordinate.getAltitude() * FOOT_TO_METER;
            bearing = toolService.bearing(oldLat, oldLong, currentLat, currentLong);
            distance = toolService.distance(oldLat,currentLat,oldLong,currentLong,oldAltitude,currentAltitude);
            instSpeed = distance / MEDIUM_TIME * 3600 / 1000;

            record.setLat(currentLat);
            record.setLon(currentLong);
            record.setBearing(bearing);
            record.setInstSpeed(instSpeed);

            oldLat = currentLat;
            oldLong = currentLong;
            oldAltitude = currentAltitude;
            count++;

            try {
                messageStorageService.put(record);
            }
            catch (InterruptedException ie){
                ie.printStackTrace();
            }

        }
    }
}
