package com.onefly.flight.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onefly.flight.dto.request.VerifyRequest;
import com.onefly.flight.dto.response.VerifyResponse;
import com.onefly.flight.entity.RoutingEntity;
import com.onefly.flight.service.FlightVerifyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FlightVerifyController.class)
class FlightVerifyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private FlightVerifyService flightVerifyService;

    @Test
    void verifyPrice_withValidRequest_shouldReturnSuccess() throws Exception {
        RoutingEntity routing = RoutingEntity.builder()
                .data("eyJ3cmFwcGVyIjoidnkifQ==")
                .adultPrice(138.98)
                .adultTax(1.0)
                .currency("EUR")
                .fromSegments(Collections.emptyList())
                .retSegments(Collections.emptyList())
                .build();

        VerifyRequest request = VerifyRequest.builder()
                .cid("test")
                .tripType("1")
                .adultNum(1)
                .childNum(0)
                .infantNum(0)
                .routing(routing)
                .build();

        VerifyResponse verifyResponse = VerifyResponse.builder()
                .status(0)
                .msg("verify success")
                .sessionId("5163645d8e")
                .maxSeats(1)
                .routing(routing)
                .build();

        when(flightVerifyService.verifyPrice(any(VerifyRequest.class))).thenReturn(verifyResponse);

        mockMvc.perform(post("/api/v1/flights/verify")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(0))
                .andExpect(jsonPath("$.data.sessionId").value("5163645d8e"))
                .andExpect(jsonPath("$.data.maxSeats").value(1));
    }

    @Test
    void verifyPrice_withMissingRouting_shouldReturnValidationError() throws Exception {
        VerifyRequest request = VerifyRequest.builder()
                .cid("test")
                .tripType("1")
                .adultNum(1)
                .build();

        mockMvc.perform(post("/api/v1/flights/verify")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(-2));
    }
}
