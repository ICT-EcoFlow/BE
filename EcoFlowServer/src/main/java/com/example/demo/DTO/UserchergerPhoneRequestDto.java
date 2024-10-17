package com.example.demo.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserchergerPhoneRequestDto {
    
    @NotEmpty(message = "유저 아이디 값")
    private String username;
    
    @NotEmpty(message = "충전기 아이디를 입력해주세요.")
    private Integer id;
    
    
}