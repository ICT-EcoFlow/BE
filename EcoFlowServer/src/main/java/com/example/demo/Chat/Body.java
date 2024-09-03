package com.example.demo.Chat;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Body {
    private String model;
    private List<Message> messages;
}