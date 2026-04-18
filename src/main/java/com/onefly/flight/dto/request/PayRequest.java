package com.onefly.flight.dto.request;

import com.onefly.flight.entity.PayCardEntity;
import com.onefly.flight.entity.RoutingEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Pay & Ticket request (will be encrypted)")
public class PayRequest {

    @NotBlank(message = "cid is required")
    @Schema(description = "Interface ID username (channel unique ID)")
    private String cid;

    @NotBlank(message = "tripType is required")
    @Schema(description = "Trip type: 1 for one-way, 2 for return")
    private String tripType;

    @NotBlank(message = "sessionId is required")
    @Schema(description = "Session ID")
    private String sessionId;

    @NotBlank(message = "orderNo is required")
    @Schema(description = "Order number from order generation")
    private String orderNo;

    @NotBlank(message = "pnrCode is required")
    @Schema(description = "PNR code from order generation")
    private String pnrCode;

    @NotNull(message = "paymentType is required")
    @Schema(description = "Payment type: 1=Partner pays Onefly, 2=Partner pays airlines, 3=Credit card")
    private Integer paymentType;

    @Valid
    @Schema(description = "Credit card info (required if paymentType=3)")
    private PayCardEntity payCard;

    @NotNull(message = "routing is required")
    @Schema(description = "Routing information")
    private RoutingEntity routing;
}
