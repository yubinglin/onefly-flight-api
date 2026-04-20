package com.onefly.flight.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onefly.flight.config.OneflyApiConfig;
import com.onefly.flight.dto.request.OrderRequest;
import com.onefly.flight.dto.response.OrderResponse;
import com.onefly.flight.entity.ContactEntity;
import com.onefly.flight.entity.PassengerEntity;
import com.onefly.flight.entity.RoutingEntity;
import com.onefly.flight.exception.BusinessException;
import com.onefly.flight.service.impl.FlightOrderServiceImpl;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FlightOrderServiceTest {

    @Mock
    private HttpClient httpClient;

    @Mock
    private HttpResponse<String> httpResponse;

    private OneflyApiConfig apiConfig;
    private ObjectMapper objectMapper;
    private FlightOrderServiceImpl flightOrderService;

    @BeforeEach
    void setUp() {
        apiConfig = new OneflyApiConfig();
        apiConfig.setBaseUrl("http://api.oneflya.com:4055/price");
        apiConfig.setCid("test");
        apiConfig.setAesKey("UQVKPI5ATFNQQIA5");
        objectMapper = new ObjectMapper();
        flightOrderService = new FlightOrderServiceImpl(apiConfig, objectMapper, httpClient);
    }

    @SuppressWarnings("unchecked")
    @Test
    void createOrder_withSuccessResponse_shouldReturnOrderResponse() throws Exception {
        OrderRequest request = OrderRequest.builder()
                .cid("test")
                .tripType("2")
                .sessionId("5163645d8e")
                .routing(RoutingEntity.builder()
                        .data("testdata")
                        .adultPrice(426.96)
                        .currency("EUR")
                        .fromSegments(Collections.emptyList())
                        .retSegments(Collections.emptyList())
                        .build())
                .passengers(List.of(PassengerEntity.builder()
                        .name("LU/MANCHENG")
                        .ageType(0)
                        .birthday("19900114")
                        .gender("M")
                        .build()))
                .contact(ContactEntity.builder()
                        .name("LU/MANCHENG")
                        .email("test@test.com")
                        .build())
                .build();

        OrderResponse expectedResponse = OrderResponse.builder()
                .status(0)
                .msg("success")
                .orderNo("F2023062718391849681fa52a")
                .pnrCode("2h3r12")
                .ticketPrice(427.96)
                .build();

        String encryptedResponse = AesUtil.encrypt(
                objectMapper.writeValueAsString(expectedResponse), apiConfig.getAesKey());

        when(httpResponse.body()).thenReturn(encryptedResponse);
        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(httpResponse);

        OrderResponse result = flightOrderService.createOrder(request);

        assertNotNull(result);
        assertEquals(0, result.getStatus());
        assertEquals("F2023062718391849681fa52a", result.getOrderNo());
        assertEquals("2h3r12", result.getPnrCode());
    }

    @SuppressWarnings("unchecked")
    @Test
    void createOrder_withNetworkError_shouldThrowBusinessException() throws Exception {
        OrderRequest request = OrderRequest.builder()
                .cid("test")
                .tripType("1")
                .sessionId("session1")
                .routing(RoutingEntity.builder().data("test").build())
                .passengers(List.of(PassengerEntity.builder()
                        .name("TEST/USER").ageType(0).birthday("19900101").gender("M").build()))
                .contact(ContactEntity.builder().name("TEST").email("t@t.com").build())
                .build();

        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenThrow(new IOException("Connection refused"));

        assertThrows(BusinessException.class, () ->
                flightOrderService.createOrder(request));
    }
}
