package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Chat.ChatGptService;
import com.example.demo.Chat.MessageRequest;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ChatGptController {

    @Autowired
    private ChatGptService chatGptService;

    @PostMapping("/api/send")
    public ResponseEntity<Map<String, String>> send(@RequestBody MessageRequest request) {
        String message = request.getMessage();

        // Validate that message is not null or empty
        if (message == null || message.trim().isEmpty()) {
            throw new IllegalArgumentException("Message cannot be null or empty");
        }

        String content = chatGptService.getChatGptResponse(message);

        Map<String, String> response = new HashMap<>();
        response.put("content", content);

        return ResponseEntity.ok(response);
    }
}