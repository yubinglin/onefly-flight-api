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
@Schema(description = "Cabin bag item in order details")
public class CabinBagItemEntity {

    @Schema(description = "Cabin bag price")
    private String cabinBagPrice;

    @Schema(description = "Flight number")
    private String flightNo;
}
