package com.onefly.flight.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onefly.flight.config.OneflyApiConfig;
import com.onefly.flight.dto.request.SeatSelectionRequest;
import com.onefly.flight.dto.response.SeatSelectionResponse;
import com.onefly.flight.entity.AvailableSeatEntity;
import com.onefly.flight.entity.SeatGroupTypeEntity;
import com.onefly.flight.entity.SeatMapEntity;
import com.onefly.flight.exception.BusinessException;
import com.onefly.flight.service.impl.SeatSelectionServiceImpl;
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
class SeatSelectionServiceTest {

    @Mock
    private HttpClient httpClient;

    @Mock
    private HttpResponse<String> httpResponse;

    private OneflyApiConfig apiConfig;
    private ObjectMapper objectMapper;
    private SeatSelectionServiceImpl seatSelectionService;

    @BeforeEach
    void setUp() {
        apiConfig = new OneflyApiConfig();
        apiConfig.setBaseUrl("http://api.oneflya.com:4055/price");
        apiConfig.setCid("test");
        objectMapper = new ObjectMapper();
        seatSelectionService = new SeatSelectionServiceImpl(apiConfig, objectMapper, httpClient);
    }

    @SuppressWarnings("unchecked")
    @Test
    void getSeatSelection_withSuccessResponse_shouldReturnSeatMap() throws Exception {
        SeatSelectionRequest request = SeatSelectionRequest.builder()
                .data("testdata")
                .flightNo("VY1006")
                .depAirport("BCN")
                .arrAirport("MAD")
                .depTime("202401231000")
                .build();

        SeatSelectionResponse expectedResponse = SeatSelectionResponse.builder()
                .status(0)
                .msg("success")
                .seats(Map.of("VY1006", SeatMapEntity.builder()
                        .planeType("319")
                        .groupType(List.of(SeatGroupTypeEntity.builder()
                                .group("3").price("30").currency("EUR").build()))
                        .availableSeat(List.of(AvailableSeatEntity.builder()
                                .designator("1A").windowSeat(true).group("3").build()))
                        .unavailableSeat(List.of("2A", "2D"))
                        .build()))
                .build();

        when(httpResponse.body()).thenReturn(objectMapper.writeValueAsString(expectedResponse));
        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(httpResponse);

        SeatSelectionResponse result = seatSelectionService.getSeatSelection(request);

        assertNotNull(result);
        assertEquals(0, result.getStatus());
        assertTrue(result.getSeats().containsKey("VY1006"));
        assertEquals("319", result.getSeats().get("VY1006").getPlaneType());
    }

    @SuppressWarnings("unchecked")
    @Test
    void getSeatSelection_withNetworkError_shouldThrowBusinessException() throws Exception {
        SeatSelectionRequest request = SeatSelectionRequest.builder()
                .data("testdata")
                .flightNo("VY1006")
                .depAirport("BCN")
                .arrAirport("MAD")
                .depTime("202401231000")
                .build();

        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenThrow(new IOException("timeout"));

        assertThrows(BusinessException.class, () ->
                seatSelectionService.getSeatSelection(request));
    }
}
