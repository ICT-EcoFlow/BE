package com.example.demo.OAuth;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@Data
public class NaverDTO {
    private String id;
    private String email;
    private String name;

    // 기본 생성자
    public NaverDTO() {
    }

    // 모든 필드에 대한 생성자
    public NaverDTO(String id, String email, String name) {
        this.id = id;
        this.email = email;
        this.name = name;
    }

    // Getter 및 Setter
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // 수동으로 빌더 패턴 구현
    public static class Builder {
        private String id;
        private String email;
        private String name;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public NaverDTO build() {
            return new NaverDTO(id, email, name);
        }
    }

    // 빌더 인스턴스 생성 메서드
    public static Builder builder() {
        return new Builder();
    }
}