package com.onefly.flight.service;

import com.onefly.flight.dto.request.OrderDetailRequest;
import com.onefly.flight.dto.response.OrderDetailResponse;

public interface FlightOrderDetailService {

    OrderDetailResponse getOrderDetail(OrderDetailRequest request);
}
