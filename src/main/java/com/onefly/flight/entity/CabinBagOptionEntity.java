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
@Schema(description = "Cabin bag option details")
public class CabinBagOptionEntity {

    @Schema(description = "Price")
    private String amount;

    @Schema(description = "Currency")
    private String currency;

    @Schema(description = "Dimension (e.g. 55x40x20cm)")
    private String dimesion;

    @Schema(description = "Whether priority boarding is included")
    private Boolean isPriorityBoarding;

    @Schema(description = "Maximum weight (e.g. 10kg)")
    private String maxWeight;

    @Schema(description = "Number of pieces")
    private Integer pc;
}
