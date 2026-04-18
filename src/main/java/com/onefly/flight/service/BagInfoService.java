package com.onefly.flight.service;

import com.onefly.flight.dto.request.BagInfoRequest;
import com.onefly.flight.dto.response.BagInfoResponse;

public interface BagInfoService {

    BagInfoResponse getBagInfo(BagInfoRequest request);
}
