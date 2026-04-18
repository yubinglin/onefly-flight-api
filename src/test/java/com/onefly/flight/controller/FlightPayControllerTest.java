package com.onefly.flight.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onefly.flight.dto.request.PayRequest;
import com.onefly.flight.dto.response.PayResponse;
import com.onefly.flight.entity.RoutingEntity;
import com.onefly.flight.service.FlightPayService;
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

@WebMvcTest(FlightPayController.class)
class FlightPayControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private FlightPayService flightPayService;

    @Test
    void payAndTicket_withValidRequest_shouldReturnSuccess() throws Exception {
        RoutingEntity routing = RoutingEntity.builder()
                .data("testdata")
                .adultPrice(800.0)
                .adultTax(66.0)
                .currency("CNY")
                .fromSegments(Collections.emptyList())
                .retSegments(Collections.emptyList())
                .build();

        PayRequest request = PayRequest.builder()
                .cid("test")
                .tripType("1")
                .sessionId("S12345")
                .orderNo("Ticket-AAA-2013031209-0001")
                .pnrCode("XXXXXX")
                .paymentType(1)
                .routing(routing)
                .build();

        PayResponse payResponse = PayResponse.builder()
                .status(0)
                .msg("success")
                .sessionId("S12345")
                .orderNo("Ticket-AAA-2013031209-0001")
                .pnrCode("XXXXXX")
                .routing(routing)
                .build();

        when(flightPayService.payAndTicket(any(PayRequest.class))).thenReturn(payResponse);

        mockMvc.perform(post("/api/v1/flights/pay")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(0))
                .andExpect(jsonPath("$.data.orderNo").value("Ticket-AAA-2013031209-0001"));
    }

    @Test
    void payAndTicket_withMissingOrderNo_shouldReturnValidationError() throws Exception {
        PayRequest request = PayRequest.builder()
                .cid("test")
                .tripType("1")
                .sessionId("S12345")
                .pnrCode("XXXXXX")
                .paymentType(1)
                .routing(RoutingEntity.builder().build())
                .build();

        mockMvc.perform(post("/api/v1/flights/pay")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(-2));
    }
}
