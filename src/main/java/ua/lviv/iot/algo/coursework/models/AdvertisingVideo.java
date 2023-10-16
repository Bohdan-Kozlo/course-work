package ua.lviv.iot.algo.coursework.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AdvertisingVideo {
    private Integer id;
    private String manufacturer;
    private String brand;
    private int videoDuration;
    private static final String HEADERS = "id,manufacturer,brand, videoDuration";

    @JsonIgnore
    public static String getHeaders(){
        return HEADERS;
    }

    public String toCSV() {
        return id + "," +  manufacturer + "," + brand + "," + videoDuration;
    }
}
