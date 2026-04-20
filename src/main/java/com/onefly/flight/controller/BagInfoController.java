package com.onefly.flight.controller;

import com.onefly.flight.dto.request.BagInfoRequest;
import com.onefly.flight.dto.response.ApiResponse;
import com.onefly.flight.dto.response.BagInfoResponse;
import com.onefly.flight.service.BagInfoService;
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
@Tag(name = "Bag Info", description = "Baggage information API")
public class BagInfoController {

    private final BagInfoService bagInfoService;

    @PostMapping("/bag-info")
    @Operation(summary = "Get bag info", description = "Get check-in baggage and cabin bag information")
    public ApiResponse<BagInfoResponse> getBagInfo(@Valid @RequestBody BagInfoRequest request) {
        BagInfoResponse response = bagInfoService.getBagInfo(request);
        return ApiResponse.success(response);
    }
}
