package com.example.demo.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTO.ApiResponse;
import com.example.demo.DTO.WeatherDTO;
import com.example.demo.Service.WeatherService;


@RestController
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/api/weather/weekly")
    public ResponseEntity<WeatherDTO> getWeeklyWeather(@RequestParam String location) {
        WeatherDTO weatherDTO = weatherService.getWeeklyWeather(location);
        if (weatherDTO != null) {
            return ResponseEntity.ok(weatherDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/api/weather/today")
    public ResponseEntity<WeatherDTO.ForecastDay> getTodayWeather(@RequestParam String location) {
        WeatherDTO.ForecastDay todayWeather = weatherService.getTodayWeather(location);
        if (todayWeather != null) {
            return ResponseEntity.ok(todayWeather);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}