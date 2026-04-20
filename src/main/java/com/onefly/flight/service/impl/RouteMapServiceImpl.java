package com.onefly.flight.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onefly.flight.config.OneflyApiConfig;
import com.onefly.flight.dto.response.RouteMapResponse;
import com.onefly.flight.exception.BusinessException;
import com.onefly.flight.service.RouteMapService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class RouteMapServiceImpl implements RouteMapService {

    private final OneflyApiConfig apiConfig;
    private final ObjectMapper objectMapper;
    private final HttpClient httpClient;

    @Override
    public RouteMapResponse getRouteMap(Map<String, List<String>> routeMapRequest) {
        try {
            String requestBody = objectMapper.writeValueAsString(routeMapRequest);
            log.info("RouteMap API request: {}", requestBody);

            String url = apiConfig.getBaseUrl() + "?action=allline";
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "text/plain")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .timeout(Duration.ofSeconds(30))
                    .build();

            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            log.info("RouteMap API response: {}", response.body());

            @SuppressWarnings("unchecked")
            Map<String, List<String>> routes = objectMapper.readValue(response.body(), Map.class);

            return RouteMapResponse.builder()
                    .status(0)
                    .msg("success")
                    .routes(routes)
                    .build();
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("RouteMap API call failed", e);
            throw new BusinessException(-4, "Service error: " + e.getMessage(), e);
        }
    }
}
