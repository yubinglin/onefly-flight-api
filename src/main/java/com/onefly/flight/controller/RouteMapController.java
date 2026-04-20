package com.onefly.flight.controller;

import com.onefly.flight.dto.response.ApiResponse;
import com.onefly.flight.dto.response.RouteMapResponse;
import com.onefly.flight.service.RouteMapService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/flights")
@RequiredArgsConstructor
@Tag(name = "Route Map", description = "Route map API")
public class RouteMapController {

    private final RouteMapService routeMapService;

    @PostMapping("/route-map")
    @Operation(summary = "Get route map", description = "Get airline route map with supported routes")
    public ApiResponse<RouteMapResponse> getRouteMap(@RequestBody Map<String, List<String>> request) {
        RouteMapResponse response = routeMapService.getRouteMap(request);
        return ApiResponse.success(response);
    }
}
