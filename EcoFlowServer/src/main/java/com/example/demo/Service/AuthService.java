package com.example.demo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder; // PasswordEncoder 임포트
import org.springframework.stereotype.Service;

import com.example.demo.DTO.LoginRequestDTO;
import com.example.demo.DTO.LoginResponseDTO;
import com.example.demo.DTO.SignUpRequestDTO;
import com.example.demo.DTO.SignUpResponseDTO;
import com.example.demo.DTO.UpdateUserRequestDTO;
import com.example.demo.DTO.UserResponseDTO;
import com.example.demo.DTO.UserResponseDTO2;
import com.example.demo.Entity.UserEntity;
import com.example.demo.Jwt.JwtUtil;
import com.example.demo.Repository.UserRepository;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;  // PasswordEncoder 인터페이스로 변경
    
    @Autowired
    private JwtUtil jwtUtil;  // JwtUtil 주입

    public LoginResponseDTO authenticateUser(LoginRequestDTO loginRequest) {
        Optional<UserEntity> optionalUser = userRepository.findByUsername(loginRequest.getUsername());

        if (optionalUser.isPresent()) {
            UserEntity user = optionalUser.get();
            // 해시된 비밀번호 비교
            if (passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
                return new LoginResponseDTO("Success", "User logged in successfully", generateJwtToken(user));
            } else {
                return new LoginResponseDTO("Failure", "Invalid password", null);
            }
        } else {
            return new LoginResponseDTO("Failure", "User not found", null);
        }
    }

    private String generateJwtToken(UserEntity user) {
        // JWT 토큰 생성하는 메서드
        return jwtUtil.generateToken(user.getUsername());
    }
    
    public SignUpResponseDTO registerUser(SignUpRequestDTO signUpRequest) {
        try {
            // 새 사용자 객체 생성
            UserEntity user = new UserEntity();
            user.setUsername(signUpRequest.getUsername());
            // 비밀번호 해싱하여 저장
            user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));  // 여기서 비밀번호를 해싱합니다.
            user.setNickname(signUpRequest.getNickname());
            user.setCar(signUpRequest.getCar());
            user.setCarnumber(signUpRequest.getCarnumber());
            user.setPhone(signUpRequest.getPhone());

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
    
    // 유저 ID로 유저 정보를 가져오는 메서드
    public UserResponseDTO2 getUserById(String id) {
        UserEntity userEntity = userRepository.findByUsername(id)
            .orElseThrow(() -> new RuntimeException("User not found"));
       
        // UserResponseDTO로 변환하여 반환
        return new UserResponseDTO2(userEntity.getId(), userEntity.getUsername(), 
                                   userEntity.getNickname(), userEntity.getCar(), 
                                   userEntity.getCarnumber(), userEntity.getBusiness(), userEntity.getPassword(), userEntity.getPhone());
    }
    
    // 회원 정보 수정 메서드
    public UserResponseDTO updateUser(String id, UpdateUserRequestDTO updateUserRequest) {
        UserEntity userEntity = userRepository.findByUsername(id)
            .orElseThrow(() -> new RuntimeException("User not found"));

        // 업데이트할 정보 설정
        if (updateUserRequest.getNickname() != null) {
            userEntity.setNickname(updateUserRequest.getNickname());
        }
        if (updateUserRequest.getCar() != null) {
            userEntity.setCar(updateUserRequest.getCar());
        }
        if (updateUserRequest.getCarnumber() != null) {
            userEntity.setCarnumber(updateUserRequest.getCarnumber());
        }
        if (updateUserRequest.getPassword() != null) {
            // 비밀번호를 해싱하여 저장
            userEntity.setPassword(passwordEncoder.encode(updateUserRequest.getPassword()));
        }

        // 업데이트된 정보를 저장
        userRepository.save(userEntity);

        // 업데이트된 정보를 UserResponseDTO로 반환
        return new UserResponseDTO(userEntity.getId(), userEntity.getUsername(), 
                                   userEntity.getNickname(), userEntity.getCar(), 
                                   userEntity.getCarnumber(), userEntity.getBusiness(), userEntity.getPassword());
    }
    
    // 유저의 business 값을 0으로 변경하는 메서드
    public UserResponseDTO updateBusinessStatus(String id) {
        // 유저 조회
        UserEntity userEntity = userRepository.findByUsername(id)
            .orElseThrow(() -> new RuntimeException("User not found"));

        // business 값을 0으로 변경
        userEntity.setBusiness("0");

        // 변경된 정보를 저장
        userRepository.save(userEntity);

        // 업데이트된 정보를 UserResponseDTO로 반환
        return new UserResponseDTO(userEntity.getId(), userEntity.getUsername(),
                                   userEntity.getNickname(), userEntity.getCar(),
                                   userEntity.getCarnumber(), userEntity.getBusiness(), userEntity.getPassword());
    }
    
}
