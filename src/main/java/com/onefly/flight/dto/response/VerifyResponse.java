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
@Schema(description = "Verify API response")
public class VerifyResponse {

    @Schema(description = "Status: 0=success, other=failure")
    private int status;

    @Schema(description = "Response message")
    private String msg;

    @Schema(description = "Session ID")
    private String sessionId;

    @Schema(description = "Maximum number of seats that can be reserved (max 9)")
    private Integer maxSeats;

    @Schema(description = "Routing/pricing information")
    private RoutingEntity routing;
}
