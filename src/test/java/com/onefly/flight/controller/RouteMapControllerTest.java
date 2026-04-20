package com.onefly.flight.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onefly.flight.dto.response.RouteMapResponse;
import com.onefly.flight.service.RouteMapService;
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

@WebMvcTest(RouteMapController.class)
class RouteMapControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RouteMapService routeMapService;

    @SuppressWarnings("unchecked")
    @Test
    void getRouteMap_withValidRequest_shouldReturnSuccess() throws Exception {
        Map<String, List<String>> request = Map.of(
                "F8", List.of("YVR-SFO", "AZA-YVR", "YHZ-YTO")
        );

        RouteMapResponse routeMapResponse = RouteMapResponse.builder()
                .status(0)
                .msg("success")
                .routes(request)
                .build();

        when(routeMapService.getRouteMap(any(Map.class))).thenReturn(routeMapResponse);

        mockMvc.perform(post("/api/v1/flights/route-map")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(0))
                .andExpect(jsonPath("$.data.routes.F8[0]").value("YVR-SFO"));
    }
}
