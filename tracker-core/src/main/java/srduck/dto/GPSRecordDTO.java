package srduck.dto;

/**
 * Created by igor on 21.07.2017.
 */
public class GPSRecordDTO extends Coordinates {
    private double bearing;
    private double instSpeed;


    public double getBearing() {
        return bearing;
    }

    public void setBearing(double bearing) {
        this.bearing = bearing;
    }

    public double getInstSpeed() {
        return instSpeed;
    }

    public void setInstSpeed(double instSpeed) {
        this.instSpeed = instSpeed;
    }

    @Override
    public String toString() {
        return "GPSRecordDTO{" +
                "lat=" + this.getLat() +
                ", lon=" + this.getLon() +
                ", bearing=" + bearing +
                ", instSpeed=" + instSpeed +
                '}';
    }

}
