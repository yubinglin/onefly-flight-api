package com.onefly.flight.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onefly.flight.dto.request.OrderRequest;
import com.onefly.flight.dto.response.OrderResponse;
import com.onefly.flight.entity.ContactEntity;
import com.onefly.flight.entity.PassengerEntity;
import com.onefly.flight.entity.RoutingEntity;
import com.onefly.flight.service.FlightOrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FlightOrderController.class)
class FlightOrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private FlightOrderService flightOrderService;

    @Test
    void createOrder_withValidRequest_shouldReturnSuccess() throws Exception {
        RoutingEntity routing = RoutingEntity.builder()
                .data("testdata")
                .adultPrice(426.96)
                .adultTax(1.0)
                .currency("EUR")
                .fromSegments(Collections.emptyList())
                .retSegments(Collections.emptyList())
                .build();

        PassengerEntity passenger = PassengerEntity.builder()
                .name("LU/MANCHENG")
                .ageType(0)
                .birthday("19900114")
                .gender("M")
                .build();

        ContactEntity contact = ContactEntity.builder()
                .name("LU/MANCHENG")
                .email("test@test.com")
                .mobile("13800000000")
                .build();

        OrderRequest request = OrderRequest.builder()
                .cid("test")
                .tripType("2")
                .sessionId("5163645d8e")
                .routing(routing)
                .passengers(List.of(passenger))
                .contact(contact)
                .build();

        OrderResponse orderResponse = OrderResponse.builder()
                .status(0)
                .msg("success")
                .orderNo("F2023062718391849681fa52a")
                .pnrCode("2h3r12")
                .routing(routing)
                .ticketPrice(427.96)
                .bagPrice(129.0)
                .seatPrice(40.85)
                .sessionId("5163645d8e")
                .build();

        when(flightOrderService.createOrder(any(OrderRequest.class))).thenReturn(orderResponse);

        mockMvc.perform(post("/api/v1/flights/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(0))
                .andExpect(jsonPath("$.data.orderNo").value("F2023062718391849681fa52a"))
                .andExpect(jsonPath("$.data.pnrCode").value("2h3r12"));
    }

    @Test
    void createOrder_withMissingPassengers_shouldReturnValidationError() throws Exception {
        OrderRequest request = OrderRequest.builder()
                .cid("test")
                .tripType("1")
                .sessionId("session1")
                .routing(RoutingEntity.builder().build())
                .contact(ContactEntity.builder().name("Test").email("test@test.com").build())
                .build();

        mockMvc.perform(post("/api/v1/flights/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(-2));
    }
}
