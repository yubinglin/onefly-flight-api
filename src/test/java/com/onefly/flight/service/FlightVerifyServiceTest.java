package com.onefly.flight.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onefly.flight.config.OneflyApiConfig;
import com.onefly.flight.dto.request.VerifyRequest;
import com.onefly.flight.dto.response.VerifyResponse;
import com.onefly.flight.entity.RoutingEntity;
import com.onefly.flight.exception.BusinessException;
import com.onefly.flight.service.impl.FlightVerifyServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FlightVerifyServiceTest {

    @Mock
    private HttpClient httpClient;

    @Mock
    private HttpResponse<String> httpResponse;

    private OneflyApiConfig apiConfig;
    private ObjectMapper objectMapper;
    private FlightVerifyServiceImpl flightVerifyService;

    @BeforeEach
    void setUp() {
        apiConfig = new OneflyApiConfig();
        apiConfig.setBaseUrl("http://api.oneflya.com:4055/price");
        apiConfig.setCid("test");
        apiConfig.setAesKey("UQVKPI5ATFNQQIA5");
        objectMapper = new ObjectMapper();
        flightVerifyService = new FlightVerifyServiceImpl(apiConfig, objectMapper, httpClient);
    }

    @SuppressWarnings("unchecked")
    @Test
    void verifyPrice_withSuccessResponse_shouldReturnVerifyResponse() throws Exception {
        VerifyRequest request = VerifyRequest.builder()
                .cid("test")
                .tripType("1")
                .adultNum(1)
                .routing(RoutingEntity.builder()
                        .data("testdata")
                        .adultPrice(138.98)
                        .adultTax(1.0)
                        .currency("EUR")
                        .fromSegments(Collections.emptyList())
                        .retSegments(Collections.emptyList())
                        .build())
                .build();

        VerifyResponse expectedResponse = VerifyResponse.builder()
                .status(0)
                .msg("verify success")
                .sessionId("5163645d8e")
                .maxSeats(1)
                .routing(request.getRouting())
                .build();

        when(httpResponse.body()).thenReturn(objectMapper.writeValueAsString(expectedResponse));
        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(httpResponse);

        VerifyResponse result = flightVerifyService.verifyPrice(request);

        assertNotNull(result);
        assertEquals(0, result.getStatus());
        assertEquals("5163645d8e", result.getSessionId());
        assertEquals(1, result.getMaxSeats());
    }

    @SuppressWarnings("unchecked")
    @Test
    void verifyPrice_withSoldOut_shouldReturnErrorStatus() throws Exception {
        VerifyRequest request = VerifyRequest.builder()
                .cid("test")
                .tripType("1")
                .adultNum(1)
                .routing(RoutingEntity.builder().data("test").build())
                .build();

        VerifyResponse errorResponse = VerifyResponse.builder()
                .status(-201)
                .msg("flight sold out")
                .build();

        when(httpResponse.body()).thenReturn(objectMapper.writeValueAsString(errorResponse));
        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(httpResponse);

        VerifyResponse result = flightVerifyService.verifyPrice(request);
        assertEquals(-201, result.getStatus());
    }

    @SuppressWarnings("unchecked")
    @Test
    void verifyPrice_withNetworkError_shouldThrowBusinessException() throws Exception {
        VerifyRequest request = VerifyRequest.builder()
                .cid("test")
                .tripType("1")
                .adultNum(1)
                .routing(RoutingEntity.builder().data("test").build())
                .build();

        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenThrow(new IOException("timeout"));

        assertThrows(BusinessException.class, () ->
                flightVerifyService.verifyPrice(request));
    }
}
