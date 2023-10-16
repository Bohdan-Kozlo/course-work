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
public class Supermarket {
    private Integer id;
    private String address;
    private double area;
    private String retailChain;
    private String openingHours;
    private int averageVisitors;
    private Integer panelId;
    private static final String HEADERS = "id,address,area,retailChain,openingHours,averageVisitors,panelId";

    @JsonIgnore
    public static String getHeaders() {
        return HEADERS;
    }

    public  String toCSV() {
        return id + "," + address + "," + area + "," + retailChain + "," + openingHours + "," + averageVisitors  + "," + panelId;
    }

}
