package com.onefly.flight.controller;

import com.onefly.flight.dto.request.OrderDetailRequest;
import com.onefly.flight.dto.response.ApiResponse;
import com.onefly.flight.dto.response.OrderDetailResponse;
import com.onefly.flight.service.FlightOrderDetailService;
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
@Tag(name = "Order Details", description = "Order details API")
public class FlightOrderDetailController {

    private final FlightOrderDetailService flightOrderDetailService;

    @PostMapping("/order-detail")
    @Operation(summary = "Get order details", description = "Retrieve order details including ticket number when issued")
    public ApiResponse<OrderDetailResponse> getOrderDetail(@Valid @RequestBody OrderDetailRequest request) {
        OrderDetailResponse response = flightOrderDetailService.getOrderDetail(request);
        return ApiResponse.success(response);
    }
}
