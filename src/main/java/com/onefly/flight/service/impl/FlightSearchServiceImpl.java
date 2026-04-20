package com.onefly.flight.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onefly.flight.config.OneflyApiConfig;
import com.onefly.flight.dto.request.SearchRequest;
import com.onefly.flight.dto.response.SearchResponse;
import com.onefly.flight.exception.BusinessException;
import com.onefly.flight.service.FlightSearchService;
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
public class FlightSearchServiceImpl implements FlightSearchService {

    private final OneflyApiConfig apiConfig;
    private final ObjectMapper objectMapper;
    private final HttpClient httpClient;

    @Override
    public SearchResponse searchFlights(SearchRequest request) {
        try {
            request.setCid(apiConfig.getCid());
            String requestBody = objectMapper.writeValueAsString(request);
            log.info("Search API request: {}", requestBody);

            String url = apiConfig.getBaseUrl() + "?action=search";
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "text/plain")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .timeout(Duration.ofSeconds(30))
                    .build();

            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            log.info("Search API response: {}", response.body());

            return objectMapper.readValue(response.body(), SearchResponse.class);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("Search API call failed", e);
            throw new BusinessException(-4, "Service error: " + e.getMessage(), e);
        }
    }
}
