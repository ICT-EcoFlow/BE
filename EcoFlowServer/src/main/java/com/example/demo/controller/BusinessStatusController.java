package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.DTO.BusinessStatusRequestDTO;
import com.example.demo.DTO.BusinessStatusResponseDTO;
import com.example.demo.Service.BusinessStatusService;

@RestController
@RequestMapping("/api/status")
public class BusinessStatusController {

    @Autowired
    private BusinessStatusService businessStatusService;

    @PostMapping
    public ResponseEntity<BusinessStatusResponseDTO> getBusinessStatus(@RequestBody BusinessStatusRequestDTO requestDTO) {
        BusinessStatusResponseDTO responseDTO = businessStatusService.getBusinessStatus(requestDTO);
        return ResponseEntity.ok(responseDTO);
    }
}