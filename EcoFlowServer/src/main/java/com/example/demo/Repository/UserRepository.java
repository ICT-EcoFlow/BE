package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    // username으로 사용자 검색
    UserEntity findByUsername(String username);
}