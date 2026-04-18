package com.onefly.flight.service;

import com.onefly.flight.dto.request.SearchRequest;
import com.onefly.flight.dto.response.SearchResponse;

public interface FlightSearchService {

    SearchResponse searchFlights(SearchRequest request);
}
