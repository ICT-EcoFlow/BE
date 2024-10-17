package com.example.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserRequestDTO {
	
    private String username;
    private String nickname;
    private String car;
    private String password;
    private String carnumber;
    private String business;
    
    
    

}
