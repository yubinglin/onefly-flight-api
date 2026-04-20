package com.onefly.flight.service;

import com.onefly.flight.dto.request.VerifyRequest;
import com.onefly.flight.dto.response.VerifyResponse;

public interface FlightVerifyService {

    VerifyResponse verifyPrice(VerifyRequest request);
}
