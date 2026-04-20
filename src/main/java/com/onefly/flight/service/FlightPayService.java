package com.onefly.flight.service;

import com.onefly.flight.dto.request.PayRequest;
import com.onefly.flight.dto.response.PayResponse;

public interface FlightPayService {

    PayResponse payAndTicket(PayRequest request);
}
