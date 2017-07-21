package srduck.services;

import de.micromata.opengis.kml.v_2_2_0.*;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by igor on 21.07.2017.
 */
@Service
public class GPSToolService {

    public static double bearing(double lat1, double lon1, double lat2, double lon2){
        double longitude1 = lon1;
        double longitude2 = lon2;
        double latitude1 = Math.toRadians(lat1);
        double latitude2 = Math.toRadians(lat2);
        double longDiff= Math.toRadians(longitude2-longitude1);
        double y= Math.sin(longDiff)*Math.cos(latitude2);
        double x=Math.cos(latitude1)*Math.sin(latitude2)-Math.sin(latitude1)*Math.cos(latitude2)*Math.cos(longDiff);

        return (Math.toDegrees(Math.atan2(y, x))+360)%360;
    }

    /**
     * Calculate distance between two points in latitude and longitude taking
     * into account height difference. If you are not interested in height
     * difference pass 0.0. Uses Haversine method as its base.
     *
     * lat1, lon1 Start point lat2, lon2 End point el1 Start altitude in meters
     * el2 End altitude in meters
     * @returns Distance in Meters
     */
    public static double distance(double lat1, double lat2, double lon1,
                                  double lon2, double el1, double el2) {

        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        double height = el1 - el2;

        distance = Math.pow(distance, 2) + Math.pow(height, 2);

        return Math.sqrt(distance);
    }

    //Получение списка коодинат из kml-файла
    public ArrayList<Coordinate> getGps(){

        ArrayList<Coordinate> coordinateList = new ArrayList<Coordinate>();
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("10164.kml").getFile());

        final Kml kml = Kml.unmarshal(file);
        final Folder folder = (Folder) kml.getFeature();
        List<Feature> featureList = folder.getFeature();
        Placemark placemark = null;

        for (Feature f : featureList){
            if (f instanceof Placemark){
                placemark = (Placemark) f;
                LineString lineString = (LineString) placemark.getGeometry();
                List<Coordinate> coordinates = lineString.getCoordinates();
                coordinateList.addAll(coordinates);
            }
        }

        return coordinateList;
    }

}
