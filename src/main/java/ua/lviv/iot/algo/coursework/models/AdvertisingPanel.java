package ua.lviv.iot.algo.coursework.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AdvertisingPanel {
    private Integer id;
    private String department;
    private String manufacturer;
    private int screenDiagonal;
    private String resolution;
    private int graduateYear;
    private Integer supermarketId;
    private Integer videoId;

    @JsonIgnore
    private static final String HEADERS = "id, department, manufacturer, screenDiagonal, resolution" +
            ", graduateYear, supermarketId, videoId";

    @JsonIgnore
    public static String getHeaders() {
        return HEADERS;
    }

    public String toCSV() {
        return id + "," + department + "," + manufacturer + "," + screenDiagonal + "," + resolution
                + "," + graduateYear + "," + supermarketId + "," + videoId;
    }


}
