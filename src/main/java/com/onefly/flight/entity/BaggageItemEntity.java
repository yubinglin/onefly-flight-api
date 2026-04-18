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
@Schema(description = "Baggage item in order details")
public class BaggageItemEntity {

    @Schema(description = "Number of pieces")
    private String pc;

    @Schema(description = "Flight number")
    private String flightNo;

    @Schema(description = "Weight")
    private String weight;

    @Schema(description = "Bag price")
    private String bagPrice;
}
