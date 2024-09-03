package com.example.demo.Service;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.demo.DTO.NaverNewsResponse;
import com.example.demo.DTO.NewsDTO;
import com.example.demo.Entity.NaverNewsItem;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service //스프링이 관리해주는 객체 == 스프링 빈
@RequiredArgsConstructor //controller와 같이. final 멤버변수 생성자 만드는 역할 
public class NewsService {

    @Value("${news.client.id}")
    private String apiKey;

    @Value("${news.client.secret}")
    private String apiSecretKey;

    public List<NewsDTO> news(String query) {
        String text;
        try {
            text = URLEncoder.encode(query, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("검색어 인코딩 실패", e);
        }

        String apiURL = "https://openapi.naver.com/v1/search/news.json?query=" + text
                + "&display=10&start=1&sort=date";

        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id", apiKey);
        requestHeaders.put("X-Naver-Client-Secret", apiSecretKey);
        String responseBody = get(apiURL, requestHeaders);

        return parseJsonToNewsDTOList(responseBody);
    }

    private List<NewsDTO> parseJsonToNewsDTOList(String responseBody) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            NaverNewsResponse response = objectMapper.readValue(responseBody, NaverNewsResponse.class);
            List<NewsDTO> newsList = new ArrayList<>();
            if (response != null && response.getItems() != null) {
                for (NaverNewsItem item : response.getItems()) {
                    NewsDTO newsDTO = new NewsDTO();
                    newsDTO.setTitle(item.getTitle());
                    newsDTO.setLink(item.getLink());
                    newsDTO.setDescription(item.getDescription());
                    newsDTO.setPubDate(item.getPubDate());
                    // 필요에 따라 다른 필드도 추가할 수 있음

                    newsList.add(newsDTO);
                }
            }
            return newsList;
        } catch (IOException e) {
            throw new RuntimeException("JSON 파싱 실패", e);
        }
    }

    private static String get(String apiUrl, Map<String, String> requestHeaders) {
        HttpURLConnection con = connect(apiUrl);
        try {
            con.setRequestMethod("GET");
            for (Map.Entry<String, String> header : requestHeaders.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());
            }

            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
                return readBody(con.getInputStream());
            } else {
                return readBody(con.getErrorStream());
            }
        } catch (IOException e) {
            throw new RuntimeException("API 요청과 응답 실패", e);
        } finally {
            con.disconnect();
        }
    }

    private static HttpURLConnection connect(String apiUrl) {
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection) url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("잘못된 API URL: " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("연결 실패: " + apiUrl, e);
        }
    }

    private static String readBody(InputStream body) {
        InputStreamReader streamReader = new InputStreamReader(body);

        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();

            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }

            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는 데 실패했습니다.", e);
        }
    }

	
}
