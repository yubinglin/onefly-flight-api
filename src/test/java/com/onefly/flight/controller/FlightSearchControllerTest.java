package com.onefly.flight.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onefly.flight.dto.request.SearchRequest;
import com.onefly.flight.dto.response.SearchResponse;
import com.onefly.flight.entity.RoutingEntity;
import com.onefly.flight.entity.RuleEntity;
import com.onefly.flight.entity.SegmentEntity;
import com.onefly.flight.service.FlightSearchService;
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

@WebMvcTest(FlightSearchController.class)
class FlightSearchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private FlightSearchService flightSearchService;

    @Test
    void searchFlights_withValidRequest_shouldReturnSuccess() throws Exception {
        SearchRequest request = SearchRequest.builder()
                .cid("test")
                .tripType("1")
                .adultNum(2)
                .childNum(1)
                .infantNum(0)
                .fromCity("BJS")
                .toCity("HKG")
                .fromDate("20230729")
                .retDate("")
                .build();

        SegmentEntity segment = SegmentEntity.builder()
                .carrier("AA")
                .flightNumber("AA89")
                .depAirport("LAX")
                .depTime("201203140140")
                .arrAirport("PEK")
                .arrTime("201203150530")
                .codeShare(false)
                .cabin("E")
                .cabinClass(2)
                .build();

        RoutingEntity routing = RoutingEntity.builder()
                .data("3da0a93eba26c6e8f28955fe65f426fadbec03d9")
                .currency("CNY")
                .adultPrice(800.0)
                .adultTax(66.0)
                .childPrice(150.0)
                .childTax(35.0)
                .nationalityType(1)
                .nationality("CN,HK,TW")
                .adultTaxType(0)
                .childTaxType(0)
                .rule(RuleEntity.builder().hasRefund(1).hasBaggage(1).build())
                .fromSegments(List.of(segment))
                .retSegments(Collections.emptyList())
                .build();

        SearchResponse searchResponse = SearchResponse.builder()
                .status(0)
                .msg("success")
                .routings(List.of(routing))
                .build();

        when(flightSearchService.searchFlights(any(SearchRequest.class))).thenReturn(searchResponse);

        mockMvc.perform(post("/api/v1/flights/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(0))
                .andExpect(jsonPath("$.msg").value("success"))
                .andExpect(jsonPath("$.data.routings[0].adultPrice").value(800.0));
    }

    @Test
    void searchFlights_withMissingCid_shouldReturnValidationError() throws Exception {
        SearchRequest request = SearchRequest.builder()
                .tripType("1")
                .adultNum(2)
                .childNum(1)
                .fromCity("BJS")
                .toCity("HKG")
                .fromDate("20230729")
                .build();

        mockMvc.perform(post("/api/v1/flights/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(-2));
    }

    @Test
    void searchFlights_withInvalidAdultNum_shouldReturnValidationError() throws Exception {
        SearchRequest request = SearchRequest.builder()
                .cid("test")
                .tripType("1")
                .adultNum(0)
                .childNum(1)
                .fromCity("BJS")
                .toCity("HKG")
                .fromDate("20230729")
                .build();

        mockMvc.perform(post("/api/v1/flights/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(-2));
    }
}
