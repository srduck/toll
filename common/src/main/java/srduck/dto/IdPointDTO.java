package srduck.dto;

import java.io.Serializable;

public class IdPointDTO implements Serializable{

    private String trackerId;
    private long time;

    public IdPointDTO() {}
    public IdPointDTO(String trackerId, long time) {
        this.trackerId = trackerId;
        this.time = time;
    }

    public String getTrackerId() {
        return trackerId;
    }

    public long getTime() {
        return time;
    }

    public boolean equals(Object o) {
        return ((o instanceof IdPointDTO) &&
                trackerId.equals(((IdPointDTO)o).getTrackerId()) &&
                time == ((IdPointDTO)o).getTime());
    }
    public int hashCode() {
        return trackerId.hashCode();
    }
}
