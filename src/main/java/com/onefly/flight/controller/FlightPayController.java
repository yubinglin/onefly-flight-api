package com.onefly.flight.controller;

import com.onefly.flight.dto.request.PayRequest;
import com.onefly.flight.dto.response.ApiResponse;
import com.onefly.flight.dto.response.PayResponse;
import com.onefly.flight.service.FlightPayService;
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
@Tag(name = "Flight Pay", description = "Pay & Ticket API (encrypted)")
public class FlightPayController {

    private final FlightPayService flightPayService;

    @PostMapping("/pay")
    @Operation(summary = "Pay and ticket", description = "Pay for an order and issue tickets. Request/response are AES encrypted.")
    public ApiResponse<PayResponse> payAndTicket(@Valid @RequestBody PayRequest request) {
        PayResponse response = flightPayService.payAndTicket(request);
        return ApiResponse.success(response);
    }
}
