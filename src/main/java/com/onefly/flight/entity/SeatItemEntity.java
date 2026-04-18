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
@Schema(description = "Seat item in order details")
public class SeatItemEntity {

    @Schema(description = "Seat number")
    private String seat;

    @Schema(description = "Flight number")
    private String flightNo;

    @Schema(description = "Seat price")
    private String seatPrice;
}
