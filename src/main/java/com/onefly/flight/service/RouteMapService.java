package com.onefly.flight.service;

import com.onefly.flight.dto.response.RouteMapResponse;

import java.util.List;
import java.util.Map;

public interface RouteMapService {

    RouteMapResponse getRouteMap(Map<String, List<String>> routeMapRequest);
}
