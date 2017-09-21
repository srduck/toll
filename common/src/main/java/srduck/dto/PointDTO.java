package srduck.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

/**
 * Created by igor on 07.07.2017.
 */
@Entity(name="POINT_DTO")
@IdClass(IdPointDTO.class)
public class PointDTO {
    @Id
    @Column(name="TRACKER_ID")
    private String trackerId;
    @Id
    private long time;
    private double lon;
    private double lat;
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

    public String getTrackerId() {
        return trackerId;
    }

    public void setTrackerId(String trackerId) {
        this.trackerId = trackerId;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getTime() {
        return time;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public String toJson() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(this);
    }

    @Override
    public String toString() {
        return "PointDTO{" +
                "trackerId='" + trackerId + '\'' +
                ", time=" + time +
                ", lon=" + lon +
                ", lat=" + lat +
                ", bearing=" + bearing +
                ", instSpeed=" + instSpeed +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PointDTO pointDTO = (PointDTO) o;

        if (time != pointDTO.time) return false;
        return trackerId.equals(pointDTO.trackerId);
    }

    @Override
    public int hashCode() {
        int result = trackerId.hashCode();
        result = 31 * result + (int) (time ^ (time >>> 32));
        return result;
    }
}
