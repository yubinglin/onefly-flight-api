package com.onefly.flight.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onefly.flight.config.OneflyApiConfig;
import com.onefly.flight.dto.request.OrderRequest;
import com.onefly.flight.dto.response.OrderResponse;
import com.onefly.flight.exception.BusinessException;
import com.onefly.flight.service.FlightOrderService;
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
public class FlightOrderServiceImpl implements FlightOrderService {

    private final OneflyApiConfig apiConfig;
    private final ObjectMapper objectMapper;
    private final HttpClient httpClient;

    @Override
    public OrderResponse createOrder(OrderRequest request) {
        try {
            request.setCid(apiConfig.getCid());
            String requestBody = objectMapper.writeValueAsString(request);
            log.info("Order API request (plain): {}", requestBody);

            String encryptedBody = AesUtil.encrypt(requestBody, apiConfig.getAesKey());
            log.info("Order API request (encrypted): {}", encryptedBody);

            String url = apiConfig.getBaseUrl() + "?action=order";
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "text/plain")
                    .POST(HttpRequest.BodyPublishers.ofString(encryptedBody))
                    .timeout(Duration.ofSeconds(60))
                    .build();

            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            log.info("Order API response (encrypted): {}", response.body());

            String decryptedResponse = AesUtil.decrypt(response.body(), apiConfig.getAesKey());
            log.info("Order API response (decrypted): {}", decryptedResponse);

            return objectMapper.readValue(decryptedResponse, OrderResponse.class);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("Order API call failed", e);
            throw new BusinessException(-4, "Service error: " + e.getMessage(), e);
        }
    }
}
