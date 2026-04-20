package com.onefly.flight.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onefly.flight.config.OneflyApiConfig;
import com.onefly.flight.dto.request.OrderDetailRequest;
import com.onefly.flight.dto.response.OrderDetailResponse;
import com.onefly.flight.exception.BusinessException;
import com.onefly.flight.service.FlightOrderDetailService;
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
public class FlightOrderDetailServiceImpl implements FlightOrderDetailService {

    private final OneflyApiConfig apiConfig;
    private final ObjectMapper objectMapper;
    private final HttpClient httpClient;

    @Override
    public OrderDetailResponse getOrderDetail(OrderDetailRequest request) {
        try {
            String requestBody = objectMapper.writeValueAsString(request);
            log.info("OrderDetail API request: {}", requestBody);

            String url = apiConfig.getBaseUrl() + "?action=orderdetail";
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "text/plain")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .timeout(Duration.ofSeconds(30))
                    .build();

            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            log.info("OrderDetail API response: {}", response.body());

            return objectMapper.readValue(response.body(), OrderDetailResponse.class);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("OrderDetail API call failed", e);
            throw new BusinessException(-4, "Service error: " + e.getMessage(), e);
        }
    }
}
