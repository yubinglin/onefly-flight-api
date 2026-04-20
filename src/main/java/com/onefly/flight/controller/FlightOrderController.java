package com.onefly.flight.controller;

import com.onefly.flight.dto.request.OrderRequest;
import com.onefly.flight.dto.response.ApiResponse;
import com.onefly.flight.dto.response.OrderResponse;
import com.onefly.flight.service.FlightOrderService;
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
@Tag(name = "Flight Order", description = "Order generation API (encrypted)")
public class FlightOrderController {

    private final FlightOrderService flightOrderService;

    @PostMapping("/order")
    @Operation(summary = "Create order", description = "Generate a flight order with passenger and contact information. Request/response are AES encrypted.")
    public ApiResponse<OrderResponse> createOrder(@Valid @RequestBody OrderRequest request) {
        OrderResponse response = flightOrderService.createOrder(request);
        return ApiResponse.success(response);
    }
}
