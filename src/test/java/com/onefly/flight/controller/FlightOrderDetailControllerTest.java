package com.onefly.flight.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onefly.flight.dto.request.OrderDetailRequest;
import com.onefly.flight.dto.response.OrderDetailResponse;
import com.onefly.flight.entity.ContactEntity;
import com.onefly.flight.entity.PassengerEntity;
import com.onefly.flight.entity.RoutingEntity;
import com.onefly.flight.service.FlightOrderDetailService;
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

@WebMvcTest(FlightOrderDetailController.class)
class FlightOrderDetailControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private FlightOrderDetailService flightOrderDetailService;

    @Test
    void getOrderDetail_withValidRequest_shouldReturnSuccess() throws Exception {
        OrderDetailRequest request = OrderDetailRequest.builder()
                .orderno("F202306191157088557459c1e")
                .build();

        OrderDetailResponse detailResponse = OrderDetailResponse.builder()
                .orderno("F202306191157088557459c1e")
                .ticketPrice(447.96)
                .bagPrice(130.0)
                .seatPrice(49.7)
                .cabinBagPrice(102.0)
                .status(3)
                .currency("EUR")
                .routing(RoutingEntity.builder().fromSegments(Collections.emptyList()).build())
                .passengers(List.of(PassengerEntity.builder().name("LU/MANCHENG").ageType(0).birthday("19900114").gender("M").build()))
                .contact(ContactEntity.builder().name("LU/MANCHENG").email("test@test.com").build())
                .build();

        when(flightOrderDetailService.getOrderDetail(any(OrderDetailRequest.class))).thenReturn(detailResponse);

        mockMvc.perform(post("/api/v1/flights/order-detail")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(0))
                .andExpect(jsonPath("$.data.orderno").value("F202306191157088557459c1e"))
                .andExpect(jsonPath("$.data.ticketPrice").value(447.96));
    }

    @Test
    void getOrderDetail_withMissingOrderNo_shouldReturnValidationError() throws Exception {
        OrderDetailRequest request = OrderDetailRequest.builder().build();

        mockMvc.perform(post("/api/v1/flights/order-detail")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(-2));
    }
}
