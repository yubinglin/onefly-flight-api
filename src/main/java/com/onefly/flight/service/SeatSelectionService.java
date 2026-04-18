package com.onefly.flight.service;

import com.onefly.flight.dto.request.SeatSelectionRequest;
import com.onefly.flight.dto.response.SeatSelectionResponse;

public interface SeatSelectionService {

    SeatSelectionResponse getSeatSelection(SeatSelectionRequest request);
}
