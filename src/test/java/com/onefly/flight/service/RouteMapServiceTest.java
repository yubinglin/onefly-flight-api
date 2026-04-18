package com.onefly.flight.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onefly.flight.config.OneflyApiConfig;
import com.onefly.flight.dto.response.RouteMapResponse;
import com.onefly.flight.exception.BusinessException;
import com.onefly.flight.service.impl.RouteMapServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RouteMapServiceTest {

    @Mock
    private HttpClient httpClient;

    @Mock
    private HttpResponse<String> httpResponse;

    private OneflyApiConfig apiConfig;
    private ObjectMapper objectMapper;
    private RouteMapServiceImpl routeMapService;

    @BeforeEach
    void setUp() {
        apiConfig = new OneflyApiConfig();
        apiConfig.setBaseUrl("http://api.oneflya.com:4055/price");
        apiConfig.setCid("test");
        objectMapper = new ObjectMapper();
        routeMapService = new RouteMapServiceImpl(apiConfig, objectMapper, httpClient);
    }

    @SuppressWarnings("unchecked")
    @Test
    void getRouteMap_withSuccessResponse_shouldReturnRoutes() throws Exception {
        Map<String, List<String>> requestMap = Map.of(
                "F8", List.of("YVR-SFO", "AZA-YVR")
        );

        when(httpResponse.body()).thenReturn(objectMapper.writeValueAsString(requestMap));
        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(httpResponse);

        RouteMapResponse result = routeMapService.getRouteMap(requestMap);

        assertNotNull(result);
        assertEquals(0, result.getStatus());
        assertTrue(result.getRoutes().containsKey("F8"));
        assertEquals(2, result.getRoutes().get("F8").size());
    }

    @SuppressWarnings("unchecked")
    @Test
    void getRouteMap_withNetworkError_shouldThrowBusinessException() throws Exception {
        Map<String, List<String>> requestMap = Map.of("F8", List.of("YVR-SFO"));

        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenThrow(new IOException("timeout"));

        assertThrows(BusinessException.class, () ->
                routeMapService.getRouteMap(requestMap));
    }
}
