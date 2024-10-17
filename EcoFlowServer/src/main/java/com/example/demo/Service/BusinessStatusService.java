package com.example.demo.Service;

import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.example.demo.DTO.BusinessStatusRequestDTO;
import com.example.demo.DTO.BusinessStatusResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

@Service
public class BusinessStatusService {

    @Value("${api.serviceKey}")
    private String serviceKey;

    private final String API_URL = "https://api.odcloud.kr/api/nts-businessman/v1/status";
    private final OkHttpClient client = new OkHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public BusinessStatusResponseDTO getBusinessStatus(BusinessStatusRequestDTO requestDTO) {
        String jsonRequest;
        try {
            jsonRequest = objectMapper.writeValueAsString(requestDTO);
        } catch (IOException e) {
            throw new RuntimeException("Failed to serialize request DTO", e);
        }

        RequestBody body = RequestBody.create(jsonRequest, MediaType.get("application/json; charset=utf-8"));

        String fullUrl = API_URL + "?serviceKey=" + serviceKey;

        Request request = new Request.Builder()
                .url(fullUrl)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected response code " + response);
            }

            String responseBody = response.body().string();
            return objectMapper.readValue(responseBody, BusinessStatusResponseDTO.class);
        } catch (IOException e) {
            // IOException 처리
            throw new RuntimeException("Failed to call external API", e);
        }
    }
}
