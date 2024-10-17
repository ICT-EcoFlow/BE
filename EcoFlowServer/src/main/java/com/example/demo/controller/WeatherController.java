package com.example.demo.controller;


import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTO.WeatherDTO;
import com.example.demo.Map.LocationRepository;
import com.example.demo.Map.StatRepository;
import com.example.demo.Service.WeatherService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor //controller와 같이. final 멤버변수 생성자 만드는 역할 
@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    private final WeatherService weatherService;


    @GetMapping("/weekly")
    public ResponseEntity<WeatherDTO> getWeeklyWeather() {
        WeatherDTO weatherDTO = weatherService.getWeeklyWeather("seoul");
        if (weatherDTO != null) {
            return ResponseEntity.ok(weatherDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/today")
    public ResponseEntity<WeatherDTO.ForecastDay> getTodayWeather() {
        WeatherDTO.ForecastDay todayWeather = weatherService.getTodayWeather("seoul");
        if (todayWeather != null) {
            return ResponseEntity.ok(todayWeather);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/carWashRecommendation")
    public ResponseEntity<Map<String, String>> getCarWashRecommendation() {
        WeatherDTO.ForecastDay todayWeather = weatherService.getTodayWeather("seoul");

        if (todayWeather == null) {
            return ResponseEntity.notFound().build();
        }

        String conditionText = todayWeather.getDay().getCondition().getText().trim();

        Map<String, String> response = new HashMap<>();
        if (weatherService.isGoodForCarWash(conditionText)) {
            response.put("recommendation", "세차하기 좋은 날씨입니다.");
        } else {
            response.put("recommendation", "세차하기 안 좋은 날씨입니다.");
        }

        return ResponseEntity.ok(response);
    }
}