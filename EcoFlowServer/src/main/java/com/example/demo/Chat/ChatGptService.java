package com.example.demo.Chat;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Service
public class ChatGptService {

    @Value("${openai.api.key}")
    private String key;

    public String getChatGptResponse(String messageContent) {
        // Check if the messageContent is null or empty
        if (messageContent == null || messageContent.trim().isEmpty()) {
            throw new IllegalArgumentException("Message content cannot be null or empty");
        }

        RestTemplate restTemplate = new RestTemplate();

        URI uri = UriComponentsBuilder
                .fromUriString("https://api.openai.com/v1/chat/completions")
                .build()
                .encode()
                .toUri();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + key);

        List<Message> messages = new ArrayList<>();
        messages.add(new Message("user", messageContent));

        Body body = new Body("ft:gpt-3.5-turbo-1106:personal::9cGE81zd", messages);

        RequestEntity<Body> httpEntity = new RequestEntity<>(body, httpHeaders, HttpMethod.POST, uri);

        ResponseEntity<ChatGptResponse> responseEntity = restTemplate.exchange(httpEntity, ChatGptResponse.class);
        ChatGptResponse chatGptResponse = responseEntity.getBody();

        if (chatGptResponse != null && !chatGptResponse.getChoices().isEmpty()) {
            return chatGptResponse.getChoices().get(0).getMessage().getContent();
        } else {
            throw new RuntimeException("Invalid response from OpenAI API");
        }
    }
}