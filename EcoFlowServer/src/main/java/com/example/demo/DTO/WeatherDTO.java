package com.example.demo.DTO;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class WeatherDTO {
    @JsonProperty("forecastday")
    private List<ForecastDay> forecastday;
    
    // 수동으로 getter 메소드 추가
    public List<ForecastDay> getForecastday() {
        return forecastday;
    }
    

    @NoArgsConstructor
    @Getter
    @Setter
    @ToString
    public static class ForecastDay {
        @JsonProperty("date")
        private String date;
        @JsonProperty("day")
        private Day day;
    }

    @NoArgsConstructor
    @Getter
    @Setter
    @ToString
    public static class Day {
        @JsonProperty("maxtemp_c")
        private double maxtemp_c;
        @JsonProperty("mintemp_c")
        private double mintemp_c;
        @JsonProperty("condition")
        private Condition condition;
    }

    @NoArgsConstructor
    @Getter
    @Setter
    @ToString
    public static class Condition {
        @JsonProperty("text")
        private String text;
        @JsonProperty("icon")
        private String icon;
    }
}