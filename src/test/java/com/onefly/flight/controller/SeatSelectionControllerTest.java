package com.onefly.flight.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onefly.flight.dto.request.SeatSelectionRequest;
import com.onefly.flight.dto.response.SeatSelectionResponse;
import com.onefly.flight.entity.*;
import com.onefly.flight.service.SeatSelectionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SeatSelectionController.class)
class SeatSelectionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SeatSelectionService seatSelectionService;

    @Test
    void getSeatSelection_withValidRequest_shouldReturnSuccess() throws Exception {
        SeatSelectionRequest request = SeatSelectionRequest.builder()
                .data("testdata")
                .flightNo("VY1006")
                .depAirport("BCN")
                .arrAirport("MAD")
                .depTime("202401231000")
                .build();

        AvailableSeatEntity seat = AvailableSeatEntity.builder()
                .designator("1A")
                .emergencyExit(false)
                .windowSeat(true)
                .childAllowed(true)
                .extraLegRoom(false)
                .aisle(false)
                .group("3")
                .build();

        SeatGroupTypeEntity groupType = SeatGroupTypeEntity.builder()
                .group("3")
                .price("30")
                .currency("EUR")
                .build();

        SeatMapEntity seatMap = SeatMapEntity.builder()
                .planeType("319")
                .groupType(List.of(groupType))
                .availableSeat(List.of(seat))
                .unavailableSeat(List.of("2A", "2D"))
                .layout(SeatLayoutEntity.builder()
                        .firstRowNumber(1)
                        .lastRowNumber(33)
                        .build())
                .build();

        SeatSelectionResponse seatResponse = SeatSelectionResponse.builder()
                .status(0)
                .msg("success")
                .seats(Map.of("VY1006", seatMap))
                .build();

        when(seatSelectionService.getSeatSelection(any(SeatSelectionRequest.class))).thenReturn(seatResponse);

        mockMvc.perform(post("/api/v1/flights/seat-selection")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(0))
                .andExpect(jsonPath("$.data.seats.VY1006.planeType").value("319"));
    }

    @Test
    void getSeatSelection_withMissingData_shouldReturnValidationError() throws Exception {
        SeatSelectionRequest request = SeatSelectionRequest.builder()
                .flightNo("VY1006")
                .depAirport("BCN")
                .arrAirport("MAD")
                .depTime("202401231000")
                .build();

        mockMvc.perform(post("/api/v1/flights/seat-selection")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(-2));
    }
}
