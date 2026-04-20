package com.onefly.flight.controller;

import com.onefly.flight.dto.request.SeatSelectionRequest;
import com.onefly.flight.dto.response.ApiResponse;
import com.onefly.flight.dto.response.SeatSelectionResponse;
import com.onefly.flight.service.SeatSelectionService;
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
@Tag(name = "Seat Selection", description = "Seat selection API")
public class SeatSelectionController {

    private final SeatSelectionService seatSelectionService;

    @PostMapping("/seat-selection")
    @Operation(summary = "Get seat selection", description = "Get available seats and seat layout for flights")
    public ApiResponse<SeatSelectionResponse> getSeatSelection(@Valid @RequestBody SeatSelectionRequest request) {
        SeatSelectionResponse response = seatSelectionService.getSeatSelection(request);
        return ApiResponse.success(response);
    }
}
