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
@Schema(description = "Cabin bag information")
public class CabinBagEntity {

    @Schema(description = "Outbound cabin bag option")
    private CabinBagOptionEntity outbound;

    @Schema(description = "Inbound cabin bag option")
    private CabinBagOptionEntity inbound;

    @Schema(description = "Can be purchased per journey")
    private Boolean perJourney;

    @Schema(description = "Can be purchased per passenger")
    private Boolean perPassenger;

    @Schema(description = "Service type (3=cabin bag)")
    private Integer serviceType;
}
