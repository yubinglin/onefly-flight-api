package com.onefly.flight.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onefly.flight.config.OneflyApiConfig;
import com.onefly.flight.dto.request.OrderDetailRequest;
import com.onefly.flight.dto.response.OrderDetailResponse;
import com.onefly.flight.exception.BusinessException;
import com.onefly.flight.service.impl.FlightOrderDetailServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FlightOrderDetailServiceTest {

    @Mock
    private HttpClient httpClient;

    @Mock
    private HttpResponse<String> httpResponse;

    private OneflyApiConfig apiConfig;
    private ObjectMapper objectMapper;
    private FlightOrderDetailServiceImpl flightOrderDetailService;

    @BeforeEach
    void setUp() {
        apiConfig = new OneflyApiConfig();
        apiConfig.setBaseUrl("http://api.oneflya.com:4055/price");
        apiConfig.setCid("test");
        objectMapper = new ObjectMapper();
        flightOrderDetailService = new FlightOrderDetailServiceImpl(apiConfig, objectMapper, httpClient);
    }

    @SuppressWarnings("unchecked")
    @Test
    void getOrderDetail_withSuccessResponse_shouldReturnOrderDetail() throws Exception {
        OrderDetailRequest request = OrderDetailRequest.builder()
                .orderno("F202306191157088557459c1e")
                .build();

        OrderDetailResponse expectedResponse = OrderDetailResponse.builder()
                .orderno("F202306191157088557459c1e")
                .ticketPrice(447.96)
                .bagPrice(130.0)
                .seatPrice(49.7)
                .cabinBagPrice(102.0)
                .status(3)
                .currency("EUR")
                .build();

        when(httpResponse.body()).thenReturn(objectMapper.writeValueAsString(expectedResponse));
        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(httpResponse);

        OrderDetailResponse result = flightOrderDetailService.getOrderDetail(request);

        assertNotNull(result);
        assertEquals("F202306191157088557459c1e", result.getOrderno());
        assertEquals(447.96, result.getTicketPrice());
        assertEquals(3, result.getStatus());
    }

    @SuppressWarnings("unchecked")
    @Test
    void getOrderDetail_withNetworkError_shouldThrowBusinessException() throws Exception {
        OrderDetailRequest request = OrderDetailRequest.builder()
                .orderno("testorder")
                .build();

        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenThrow(new IOException("timeout"));

        assertThrows(BusinessException.class, () ->
                flightOrderDetailService.getOrderDetail(request));
    }
}
