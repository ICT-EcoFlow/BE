package com.example.demo.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo.DTO.WeatherDTO;
import com.example.demo.Entity.WeatherApiResponse;

@Service
public class WeatherService {

    @Value("${weather.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    public WeatherService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    
    

    public WeatherDTO getWeeklyWeather(String location) {
        String url = String.format("https://api.weatherapi.com/v1/forecast.json?key=%s&q=%s&days=7", apiKey, location);
        try {
            WeatherApiResponse response = restTemplate.getForObject(url, WeatherApiResponse.class);
            if (response != null && response.getForecast() != null) {
                return response.getForecast();
            }
        } catch (Exception e) {
            // 로그를 추가하여 오류를 기록합니다
            e.printStackTrace();
        }
        return null;
    }

    public WeatherDTO.ForecastDay getTodayWeather(String location) {
        WeatherDTO forecast = getWeeklyWeather(location);
        if (forecast != null && !forecast.getForecastday().isEmpty()) {
            return forecast.getForecastday().get(0); // 오늘의 날씨
        }
        return null;
    }
}