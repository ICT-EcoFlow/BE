package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.DTO.UserchergerDTO;
import com.example.demo.Service.UserchergerService;

@RestController
@RequestMapping("/api/usercherger")
public class UserchergerController {

    @Autowired
    private UserchergerService userchergerService;

    // 데이터 저장 (id는 자동 증가)
    @PostMapping("/save")
    public ResponseEntity<String> saveUsercherger(@RequestBody UserchergerDTO userchergerDTO) {
        userchergerService.saveUsercherger(userchergerDTO);  // 클라이언트가 id를 제외하고 전송
        return ResponseEntity.ok("User charger information saved successfully");
    }

    // 모든 유저 정보를 가져오기
    @GetMapping("/list")
    public ResponseEntity<List<UserchergerDTO>> getAllUserChargerInfo() {
        List<UserchergerDTO> userChargerList = userchergerService.getAllUserChargerInfo();
        return ResponseEntity.ok(userChargerList);
    }

    // 특정 유저 정보 가져오기
    @GetMapping("/{username}")
    public ResponseEntity<List<UserchergerDTO>> getUserChargerByUsername(@PathVariable String username) {
        List<UserchergerDTO> userChargerList = userchergerService.getUserChargerByUsername(username);
        return ResponseEntity.ok(userChargerList);
    }

    // 충전기의 stat 값을 변경하는 API
    @PutMapping("/updateStat/{id}")
    public ResponseEntity<String> updateChargerStat(@PathVariable Integer id) {
        userchergerService.updateChargerStat(id);
        return ResponseEntity.ok("Charger stat updated successfully");
    }
    
    // 특정 id로 충전기 정보 삭제
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUserchergerById(@PathVariable Integer id) {
        userchergerService.deleteUserchergerById(id);
        return ResponseEntity.ok("Charger deleted successfully");
    }
    
}
