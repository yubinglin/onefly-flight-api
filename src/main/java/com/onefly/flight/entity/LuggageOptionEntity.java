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
@Schema(description = "Luggage option details")
public class LuggageOptionEntity {

    @Schema(description = "Outbound ID for SSR value")
    private String outId;

    @Schema(description = "Price")
    private String amount;

    @Schema(description = "Currency")
    private String currency;

    @Schema(description = "Number of pieces")
    private Integer pc;

    @Schema(description = "Weight (e.g. 15kg)")
    private String weight;

    @Schema(description = "Dimension (e.g. 55x40x20cm)")
    private String dimension;
}
