package com.onefly.flight.service;

import com.onefly.flight.dto.request.OrderRequest;
import com.onefly.flight.dto.response.OrderResponse;

public interface FlightOrderService {

    OrderResponse createOrder(OrderRequest request);
}
