package com.example.demo.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class accountController {
	
	@GetMapping("/api/first")
	public String accountController(){
		
		return "hi";
		
	}
}
