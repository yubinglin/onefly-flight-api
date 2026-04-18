package com.onefly.flight.dto.response;

import com.onefly.flight.entity.AuxListEntity;
import com.onefly.flight.entity.RoutingEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Order generation response")
public class OrderResponse {

    @Schema(description = "Status: 0=success, 1=price changed, other=failure")
    private int status;

    @Schema(description = "Response message")
    private String msg;

    @Schema(description = "Order number")
    private String orderNo;

    @Schema(description = "PNR code")
    private String pnrCode;

    @Schema(description = "Routing information")
    private RoutingEntity routing;

    @Schema(description = "Validate Spanish resident document")
    private String validateSpanish;

    @Schema(description = "Whether resident is supported")
    private String supportResident;

    @Schema(description = "Resident discount")
    private String residentDiscount;

    @Schema(description = "Total seat price")
    private Double seatPrice;

    @Schema(description = "Total baggage price")
    private Double bagPrice;

    @Schema(description = "Total ticket price")
    private Double ticketPrice;

    @Schema(description = "Total cabin bag price")
    private Double cabinBagPrice;

    @Schema(description = "Card fee")
    private Double cardFee;

    @Schema(description = "Session ID")
    private String sessionId;

    @Schema(description = "Auxiliary list (baggage, seat, cabin bag per passenger)")
    private List<AuxListEntity> auxList;
}
