package com.onefly.flight.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onefly.flight.config.OneflyApiConfig;
import com.onefly.flight.dto.request.BagInfoRequest;
import com.onefly.flight.dto.response.BagInfoResponse;
import com.onefly.flight.entity.LuggageEntity;
import com.onefly.flight.entity.LuggageOptionEntity;
import com.onefly.flight.exception.BusinessException;
import com.onefly.flight.service.impl.BagInfoServiceImpl;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BagInfoServiceTest {

    @Mock
    private HttpClient httpClient;

    @Mock
    private HttpResponse<String> httpResponse;

    private OneflyApiConfig apiConfig;
    private ObjectMapper objectMapper;
    private BagInfoServiceImpl bagInfoService;

    @BeforeEach
    void setUp() {
        apiConfig = new OneflyApiConfig();
        apiConfig.setBaseUrl("http://api.oneflya.com:4055/price");
        apiConfig.setCid("test");
        objectMapper = new ObjectMapper();
        bagInfoService = new BagInfoServiceImpl(apiConfig, objectMapper, httpClient);
    }

    @SuppressWarnings("unchecked")
    @Test
    void getBagInfo_withSuccessResponse_shouldReturnBagInfo() throws Exception {
        BagInfoRequest request = BagInfoRequest.builder()
                .data("testdata")
                .flightNo("VY8016/VY6942")
                .depAirport("BCN")
                .arrAirport("LGW")
                .depTime("202401232020")
                .build();

        BagInfoResponse expectedResponse = BagInfoResponse.builder()
                .status(0)
                .msg("success")
                .luggage(LuggageEntity.builder()
                        .outbound(List.of(LuggageOptionEntity.builder()
                                .amount("25.0")
                                .currency("EUR")
                                .pc(1)
                                .weight("15kg")
                                .build()))
                        .serviceType(1)
                        .build())
                .build();

        when(httpResponse.body()).thenReturn(objectMapper.writeValueAsString(expectedResponse));
        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(httpResponse);

        BagInfoResponse result = bagInfoService.getBagInfo(request);

        assertNotNull(result);
        assertEquals(0, result.getStatus());
        assertNotNull(result.getLuggage());
        assertEquals(1, result.getLuggage().getOutbound().size());
    }

    @SuppressWarnings("unchecked")
    @Test
    void getBagInfo_withNetworkError_shouldThrowBusinessException() throws Exception {
        BagInfoRequest request = BagInfoRequest.builder()
                .data("testdata")
                .flightNo("VY8016")
                .depAirport("BCN")
                .arrAirport("LGW")
                .depTime("202401232020")
                .build();

        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenThrow(new IOException("timeout"));

        assertThrows(BusinessException.class, () ->
                bagInfoService.getBagInfo(request));
    }
}
