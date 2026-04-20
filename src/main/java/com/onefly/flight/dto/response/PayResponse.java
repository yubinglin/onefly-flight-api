package com.onefly.flight.dto.response;

import com.onefly.flight.entity.RoutingEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Pay & Ticket response")
public class PayResponse {

    @Schema(description = "Status: 0=success, other=failure")
    private int status;

    @Schema(description = "Response message")
    private String msg;

    @Schema(description = "Session ID")
    private String sessionId;

    @Schema(description = "Order number")
    private String orderNo;

    @Schema(description = "PNR code")
    private String pnrCode;

    @Schema(description = "Routing information")
    private RoutingEntity routing;
}
