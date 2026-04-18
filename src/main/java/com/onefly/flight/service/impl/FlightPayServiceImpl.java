package com.onefly.flight.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onefly.flight.config.OneflyApiConfig;
import com.onefly.flight.dto.request.PayRequest;
import com.onefly.flight.dto.response.PayResponse;
import com.onefly.flight.exception.BusinessException;
import com.onefly.flight.service.FlightPayService;
import com.onefly.flight.util.AesUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

@Slf4j
@Service
@RequiredArgsConstructor
public class FlightPayServiceImpl implements FlightPayService {

    private final OneflyApiConfig apiConfig;
    private final ObjectMapper objectMapper;
    private final HttpClient httpClient;

    @Override
    public PayResponse payAndTicket(PayRequest request) {
        try {
            request.setCid(apiConfig.getCid());
            String requestBody = objectMapper.writeValueAsString(request);
            log.info("Pay API request (plain): {}", requestBody);

            String encryptedBody = AesUtil.encrypt(requestBody, apiConfig.getAesKey());
            log.info("Pay API request (encrypted): {}", encryptedBody);

            String url = apiConfig.getBaseUrl() + "?action=pay";
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "text/plain")
                    .POST(HttpRequest.BodyPublishers.ofString(encryptedBody))
                    .timeout(Duration.ofSeconds(60))
                    .build();

            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            log.info("Pay API response (encrypted): {}", response.body());

            String decryptedResponse = AesUtil.decrypt(response.body(), apiConfig.getAesKey());
            log.info("Pay API response (decrypted): {}", decryptedResponse);

            return objectMapper.readValue(decryptedResponse, PayResponse.class);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("Pay API call failed", e);
            throw new BusinessException(-4, "Service error: " + e.getMessage(), e);
        }
    }
}
