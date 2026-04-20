package com.onefly.flight.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onefly.flight.dto.request.BagInfoRequest;
import com.onefly.flight.dto.response.BagInfoResponse;
import com.onefly.flight.entity.LuggageEntity;
import com.onefly.flight.entity.LuggageOptionEntity;
import com.onefly.flight.service.BagInfoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BagInfoController.class)
class BagInfoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BagInfoService bagInfoService;

    @Test
    void getBagInfo_withValidRequest_shouldReturnSuccess() throws Exception {
        BagInfoRequest request = BagInfoRequest.builder()
                .data("testdata")
                .flightNo("VY8016/VY6942")
                .depAirport("BCN")
                .arrAirport("LGW")
                .depTime("202401232020")
                .retFlightNo("")
                .retTime("")
                .build();

        LuggageOptionEntity option = LuggageOptionEntity.builder()
                .outId("testOutId")
                .amount("25.0")
                .currency("EUR")
                .pc(1)
                .weight("15kg")
                .build();

        LuggageEntity luggage = LuggageEntity.builder()
                .outbound(List.of(option))
                .perJourney(true)
                .perPassenger(true)
                .serviceType(1)
                .build();

        BagInfoResponse bagInfoResponse = BagInfoResponse.builder()
                .status(0)
                .msg("success")
                .luggage(luggage)
                .build();

        when(bagInfoService.getBagInfo(any(BagInfoRequest.class))).thenReturn(bagInfoResponse);

        mockMvc.perform(post("/api/v1/flights/bag-info")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(0))
                .andExpect(jsonPath("$.data.luggage.outbound[0].amount").value("25.0"));
    }

    @Test
    void getBagInfo_withMissingFlightNo_shouldReturnValidationError() throws Exception {
        BagInfoRequest request = BagInfoRequest.builder()
                .data("testdata")
                .depAirport("BCN")
                .arrAirport("LGW")
                .depTime("202401232020")
                .build();

        mockMvc.perform(post("/api/v1/flights/bag-info")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(-2));
    }
}
