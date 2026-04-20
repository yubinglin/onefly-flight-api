package com.onefly.flight.controller;

import com.onefly.flight.dto.request.SearchRequest;
import com.onefly.flight.dto.response.ApiResponse;
import com.onefly.flight.dto.response.SearchResponse;
import com.onefly.flight.service.FlightSearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/flights")
@RequiredArgsConstructor
@Tag(name = "Flight Search", description = "Flight search API")
public class FlightSearchController {

    private final FlightSearchService flightSearchService;

    @PostMapping("/search")
    @Operation(summary = "Search flights", description = "Search for available flights based on trip type, dates, and passengers")
    public ApiResponse<SearchResponse> searchFlights(@Valid @RequestBody SearchRequest request) {
        SearchResponse response = flightSearchService.searchFlights(request);
        return ApiResponse.success(response);
    }
}
