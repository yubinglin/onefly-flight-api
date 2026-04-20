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
@Schema(description = "Auxiliary item (baggage/seat/cabin bag)")
public class AuxItemEntity {

    @Schema(description = "Type: 1=luggage, 2=seat selection, 3=cabin bag")
    private Integer type;

    @Schema(description = "Amount")
    private Double amount;

    @Schema(description = "Flight number")
    private String flightNo;

    @Schema(description = "SSR value (outid for luggage, seat number for seat, null for cabin bag)")
    private String ssrValue;

    @Schema(description = "Currency")
    private String currency;
}
