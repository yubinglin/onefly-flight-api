package com.onefly.flight.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Order auxiliary list item (baggage/seat/cabin bag per passenger)")
public class OrderAuxListEntity {

    @Schema(description = "Passenger name")
    private String passengerName;

    @Schema(description = "Baggage info")
    private BaggageItemEntity baggageItem;

    @Schema(description = "Seat info")
    private SeatItemEntity seatItem;

    @Schema(description = "Cabin bag info")
    private CabinBagItemEntity cabinBagItem;
}
