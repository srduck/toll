package srduck.dto;

/**
 * Created by igor on 07.07.2017.
 */
public class PointDTO extends Coordinates{

    private String autoId;
    private long time;

    public String getAutoId() {
        return autoId;
    }

    public void setAutoId(String autoId) {
        this.autoId = autoId;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "PointDTO{" +
                "lat=" + this.getLat() +
                ", lon=" + this.getLon() +
                ", autoId='" + autoId + '\'' +
                ", time=" + time +
                '}';
    }
}
