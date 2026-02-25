package com.springboot.journalApp.apiResponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class WeatherResponse {

    private Request request;
    private Location location;
    private Current current;


    @Getter
    @Setter
    public class Current{
        private String observation_time;
        private int temperature;
        private int weather_code;
        private List<String> weather_descriptions;
        private int precip;
        private int humidity;
        private int cloudcover;
        private int feelslike;
        private String is_day;
    }

    @Getter
    @Setter
    public class Location{
        private String name;
        private String country;
        private String region;
        private String lat;
        private String lon;
        private String timezone_id;
        private String localtime;

    }

    @Getter
    @Setter
    public class Request{
        private String type;
        private String query;
        private String language;
        private String unit;
    }

}





