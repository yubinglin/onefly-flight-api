package com.onefly.flight.controller;

import com.onefly.flight.dto.request.VerifyRequest;
import com.onefly.flight.dto.response.ApiResponse;
import com.onefly.flight.dto.response.VerifyResponse;
import com.onefly.flight.service.FlightVerifyService;
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
@Tag(name = "Flight Verify", description = "Price verification API")
public class FlightVerifyController {

    private final FlightVerifyService flightVerifyService;

    @PostMapping("/verify")
    @Operation(summary = "Verify flight price", description = "Verify the price of a selected flight routing")
    public ApiResponse<VerifyResponse> verifyPrice(@Valid @RequestBody VerifyRequest request) {
        VerifyResponse response = flightVerifyService.verifyPrice(request);
        return ApiResponse.success(response);
    }
}
