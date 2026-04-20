package com.onefly.flight.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onefly.flight.config.OneflyApiConfig;
import com.onefly.flight.dto.request.SearchRequest;
import com.onefly.flight.dto.response.SearchResponse;
import com.onefly.flight.entity.RoutingEntity;
import com.onefly.flight.exception.BusinessException;
import com.onefly.flight.service.impl.FlightSearchServiceImpl;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FlightSearchServiceTest {

    @Mock
    private HttpClient httpClient;

    @Mock
    private HttpResponse<String> httpResponse;

    private OneflyApiConfig apiConfig;
    private ObjectMapper objectMapper;
    private FlightSearchServiceImpl flightSearchService;

    @BeforeEach
    void setUp() {
        apiConfig = new OneflyApiConfig();
        apiConfig.setBaseUrl("http://api.oneflya.com:4055/price");
        apiConfig.setCid("test");
        apiConfig.setAesKey("UQVKPI5ATFNQQIA5");
        objectMapper = new ObjectMapper();
        flightSearchService = new FlightSearchServiceImpl(apiConfig, objectMapper, httpClient);
    }

    @SuppressWarnings("unchecked")
    @Test
    void searchFlights_withSuccessResponse_shouldReturnSearchResponse() throws Exception {
        SearchRequest request = SearchRequest.builder()
                .cid("test")
                .tripType("1")
                .adultNum(2)
                .childNum(1)
                .fromCity("BJS")
                .toCity("HKG")
                .fromDate("20230729")
                .build();

        SearchResponse expectedResponse = SearchResponse.builder()
                .status(0)
                .msg("success")
                .routings(List.of(RoutingEntity.builder()
                        .data("testdata")
                        .adultPrice(800.0)
                        .adultTax(66.0)
                        .currency("CNY")
                        .build()))
                .build();

        when(httpResponse.body()).thenReturn(objectMapper.writeValueAsString(expectedResponse));
        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(httpResponse);

        SearchResponse result = flightSearchService.searchFlights(request);

        assertNotNull(result);
        assertEquals(0, result.getStatus());
        assertEquals("success", result.getMsg());
        assertEquals(1, result.getRoutings().size());
        assertEquals(800.0, result.getRoutings().get(0).getAdultPrice());
    }

    @SuppressWarnings("unchecked")
    @Test
    void searchFlights_withErrorResponse_shouldReturnErrorStatus() throws Exception {
        SearchRequest request = SearchRequest.builder()
                .cid("test")
                .tripType("1")
                .adultNum(2)
                .childNum(1)
                .fromCity("BJS")
                .toCity("HKG")
                .fromDate("20230729")
                .build();

        SearchResponse errorResponse = SearchResponse.builder()
                .status(-101)
                .msg("route not supported")
                .build();

        when(httpResponse.body()).thenReturn(objectMapper.writeValueAsString(errorResponse));
        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(httpResponse);

        SearchResponse result = flightSearchService.searchFlights(request);

        assertEquals(-101, result.getStatus());
        assertEquals("route not supported", result.getMsg());
    }

    @SuppressWarnings("unchecked")
    @Test
    void searchFlights_withNetworkError_shouldThrowBusinessException() throws Exception {
        SearchRequest request = SearchRequest.builder()
                .cid("test")
                .tripType("1")
                .adultNum(2)
                .childNum(1)
                .fromCity("BJS")
                .toCity("HKG")
                .fromDate("20230729")
                .build();

        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenThrow(new IOException("Connection refused"));

        assertThrows(BusinessException.class, () ->
                flightSearchService.searchFlights(request));
    }
}
