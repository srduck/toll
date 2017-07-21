package srduck.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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

    public String toJson() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(this);
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
