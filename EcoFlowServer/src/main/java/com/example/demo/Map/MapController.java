package com.example.demo.Map;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class MapController {
	
	private final MapService mapService;
	
	
    @GetMapping("/updateInfo")
    public ResponseEntity<?> getInfo() {
        try {
            mapService.updateLocationInfo();
            mapService.updateStatInfo();
            return ResponseEntity.ok().body("{\"status\":\"success\",\"message\":\"정보가 성공적으로 업데이트되었습니다.\"}");
        } catch (Exception e) {
            e.printStackTrace(); // 로그를 남기거나 필요시 로깅 프레임워크를 사용할 수 있음
            return ResponseEntity.status(500).body("{\"status\":\"failure\",\"message\":\"정보 업데이트 중 오류가 발생했습니다.\"}");
        }
    }
    
    @GetMapping("/infoByStatNm")
    public ResponseEntity<?> getInfoByStatNm(@RequestParam String statNm) {
        try {
            Map<String, Object> result = mapService.getInfoByStatNm(statNm);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            e.printStackTrace(); // 로그를 남기거나 필요시 로깅 프레임워크를 사용할 수 있음
            return ResponseEntity.status(500).body("{\"status\":\"failure\",\"message\":\"정보 조회 중 오류가 발생했습니다.\"}");
        }
    }
    
    @GetMapping("/locationsNear")
    public ResponseEntity<?> getLocationsNear(
            @RequestParam double latitude,
            @RequestParam double longitude,
            @RequestParam(defaultValue = "0.5") double radius) {
        try {
            Map<String, Object> result = mapService.getLocationsNear(latitude, longitude, radius);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("{\"status\":\"failure\",\"message\":\"위치 검색 중 오류가 발생했습니다.\"}");
        }
    }
    

}
