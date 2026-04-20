package com.onefly.flight.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onefly.flight.config.OneflyApiConfig;
import com.onefly.flight.dto.request.PayRequest;
import com.onefly.flight.dto.response.PayResponse;
import com.onefly.flight.entity.RoutingEntity;
import com.onefly.flight.exception.BusinessException;
import com.onefly.flight.service.impl.FlightPayServiceImpl;
import com.onefly.flight.util.AesUtil;
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
class FlightPayServiceTest {

    @Mock
    private HttpClient httpClient;

    @Mock
    private HttpResponse<String> httpResponse;

    private OneflyApiConfig apiConfig;
    private ObjectMapper objectMapper;
    private FlightPayServiceImpl flightPayService;

    @BeforeEach
    void setUp() {
        apiConfig = new OneflyApiConfig();
        apiConfig.setBaseUrl("http://api.oneflya.com:4055/price");
        apiConfig.setCid("test");
        apiConfig.setAesKey("UQVKPI5ATFNQQIA5");
        objectMapper = new ObjectMapper();
        flightPayService = new FlightPayServiceImpl(apiConfig, objectMapper, httpClient);
    }

    @SuppressWarnings("unchecked")
    @Test
    void payAndTicket_withSuccessResponse_shouldReturnPayResponse() throws Exception {
        PayRequest request = PayRequest.builder()
                .cid("test")
                .tripType("1")
                .sessionId("S12345")
                .orderNo("Ticket-AAA-2013031209-0001")
                .pnrCode("XXXXXX")
                .paymentType(1)
                .routing(RoutingEntity.builder()
                        .data("testdata")
                        .adultPrice(800.0)
                        .currency("CNY")
                        .fromSegments(Collections.emptyList())
                        .retSegments(Collections.emptyList())
                        .build())
                .build();

        PayResponse expectedResponse = PayResponse.builder()
                .status(0)
                .msg("success")
                .sessionId("S12345")
                .orderNo("Ticket-AAA-2013031209-0001")
                .pnrCode("XXXXXX")
                .build();

        String encryptedResponse = AesUtil.encrypt(
                objectMapper.writeValueAsString(expectedResponse), apiConfig.getAesKey());

        when(httpResponse.body()).thenReturn(encryptedResponse);
        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(httpResponse);

        PayResponse result = flightPayService.payAndTicket(request);

        assertNotNull(result);
        assertEquals(0, result.getStatus());
        assertEquals("Ticket-AAA-2013031209-0001", result.getOrderNo());
    }

    @SuppressWarnings("unchecked")
    @Test
    void payAndTicket_withNetworkError_shouldThrowBusinessException() throws Exception {
        PayRequest request = PayRequest.builder()
                .cid("test")
                .tripType("1")
                .sessionId("S12345")
                .orderNo("order1")
                .pnrCode("pnr1")
                .paymentType(1)
                .routing(RoutingEntity.builder().data("test").build())
                .build();

        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenThrow(new IOException("timeout"));

        assertThrows(BusinessException.class, () ->
                flightPayService.payAndTicket(request));
    }
}
