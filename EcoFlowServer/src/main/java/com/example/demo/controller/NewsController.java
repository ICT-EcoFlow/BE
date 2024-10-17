package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTO.NewsDTO;
import com.example.demo.Service.NewsService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor //controller와 같이. final 멤버변수 생성자 만드는 역할 
@RestController
public class NewsController {

    private final NewsService newsService;



    @GetMapping("/api/news")
    public ResponseEntity<List<NewsDTO>> getNews(@RequestParam(required = false, defaultValue = "전기차") String query) {
        try {
            List<NewsDTO> newsList = newsService.news(query); // NewsService의 news 메소드 호출
            return ResponseEntity.ok(newsList); // JSON 형태로 응답 반환
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null); // 예외 발생 시 500 에러 반환
        }
    }
}