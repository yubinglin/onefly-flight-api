package com.onefly.flight.dto.response;

import com.onefly.flight.entity.*;
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
@Schema(description = "Order details response")
public class OrderDetailResponse {

    @Schema(description = "Order number")
    private String orderno;

    @Schema(description = "Ticket price (does not include bags)")
    private Double ticketPrice;

    @Schema(description = "Bag price")
    private Double bagPrice;

    @Schema(description = "Seat selection price")
    private Double seatPrice;

    @Schema(description = "Card fee")
    private Double cardFee;

    @Schema(description = "Cabin bag price")
    private Double cabinBagPrice;

    @Schema(description = "PNR")
    private String pnr;

    @Schema(description = "Status: 0=ticket not issued, 3=payment successful, 4=ticket issued, " +
            "5=refund applied, 6=refund completed, -501=canceled, -502=payment failed, -503=issuance failed")
    private Integer status;

    @Schema(description = "Currency")
    private String currency;

    @Schema(description = "Routing information")
    private RoutingEntity routing;

    @Schema(description = "Passenger information")
    private List<PassengerEntity> passengers;

    @Schema(description = "Auxiliary list (baggage, seat, cabin bag)")
    private List<OrderAuxListEntity> auxList;

    @Schema(description = "Contact information")
    private ContactEntity contact;

    @Schema(description = "Extra information for MYB")
    private ExtInfoEntity extInfo;
}
