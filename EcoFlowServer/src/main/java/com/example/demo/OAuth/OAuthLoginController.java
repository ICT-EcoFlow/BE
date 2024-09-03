package com.example.demo.OAuth;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class OAuthLoginController {

    // final 필드는 @RequiredArgsConstructor로 자동으로 주입됨
    private final NaverService naverService;
    private final KakaoService kakaoService;

    @GetMapping("/api/index")
    public String login(Model model) {
        model.addAttribute("naverUrl", naverService.getNaverLogin());
        model.addAttribute("kakaoUrl", kakaoService.getKakaoLogin());
        return "index";
    }
}