package com.example.demo.Main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTO.LoginRequestDTO;
import com.example.demo.DTO.LoginResponseDTO;
import com.example.demo.DTO.SignUpRequestDTO;
import com.example.demo.DTO.SignUpResponseDTO;
import com.example.demo.DTO.UpdateUserRequestDTO;
import com.example.demo.DTO.UserResponseDTO;
import com.example.demo.DTO.UserResponseDTO2;
import com.example.demo.Jwt.JwtUtil;
import com.example.demo.Service.AuthService;


@RestController
@RequestMapping("/api")
public class LoginController {
	
    @Autowired
    private AuthService authService;
    
    @Autowired
    private JwtUtil jwtUtil;  // JwtUtil 클래스 추가

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody LoginRequestDTO loginRequest) {
        LoginResponseDTO response = authService.authenticateUser(loginRequest);
        
        if ("Success".equals(response.getStatus())) {
            // 로그인 성공 시 JWT 토큰 생성
            String jwt = jwtUtil.generateToken(loginRequest.getUsername());
            response.setToken(jwt);  // JWT 토큰 추가
        }
        
        return response;
    }
    
    @PostMapping("/signup")
    public SignUpResponseDTO signUp(@RequestBody SignUpRequestDTO signUpRequest) {
       return authService.registerUser(signUpRequest);
    }
    
    @GetMapping("/user/{id}")
    public UserResponseDTO2 getUserById(@PathVariable String id) {
        return authService.getUserById(id);
    }
    
    // 회원 정보 수정 API
    @PutMapping("/userupdate/{id}")
    public UserResponseDTO updateUser(@PathVariable String id, @RequestBody UpdateUserRequestDTO updateUserRequest) {
        return authService.updateUser(id, updateUserRequest);
    }
    
    // id로 유저의 business 값을 0으로 변경하는 API
    @PutMapping("/businessupdate/{id}")
    public UserResponseDTO updateBusinessStatus(@PathVariable String id) {
        return authService.updateBusinessStatus(id);
    }
    
    
}
