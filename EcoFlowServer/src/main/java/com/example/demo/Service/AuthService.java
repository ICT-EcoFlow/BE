package com.example.demo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.example.demo.DTO.LoginRequestDTO;
import com.example.demo.DTO.LoginResponseDTO;
import com.example.demo.DTO.SignUpRequestDTO;
import com.example.demo.DTO.SignUpResponseDTO;
import com.example.demo.Entity.UserEntity;
import com.example.demo.Repository.UserRepository;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    public LoginResponseDTO authenticateUser(LoginRequestDTO loginRequest) {
        UserEntity user = userRepository.findByUsername(loginRequest.getUsername());

        if (user != null && user.getPassword().equals(loginRequest.getPassword())) {
            return new LoginResponseDTO("Success", "User logged in successfully");
        } else {
            return new LoginResponseDTO("Failure", "Invalid username or password");
        }
    }
    
    public SignUpResponseDTO registerUser(SignUpRequestDTO signUpRequest) {
        try {
            // 새 사용자 객체 생성
            UserEntity user = new UserEntity();
            user.setUsername(signUpRequest.getUsername());
            user.setPassword(signUpRequest.getPassword()); // 비밀번호는 실제로는 해시화해야 합니다.
            user.setNickname(signUpRequest.getNickname());
            user.setCar(signUpRequest.getCar());
            user.setCarnickname(signUpRequest.getCarnickname());

            // 데이터베이스에 사용자 저장
            userRepository.save(user);
            return new SignUpResponseDTO(true, "회원가입 성공");
        } catch (DataIntegrityViolationException e) {
            // 중복된 사용자명 등 데이터베이스 관련 오류 처리
            return new SignUpResponseDTO(false, "회원가입 실패: 중복된 사용자명");
        } catch (Exception e) {
            // 일반적인 오류 처리
            return new SignUpResponseDTO(false, "회원가입 실패: " + e.getMessage());
        }
    }
    
    
    
}
