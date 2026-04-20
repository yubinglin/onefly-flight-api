package com.onefly.flight.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Luggage (check-in bag) information")
public class LuggageEntity {

    @Schema(description = "Outbound luggage list")
    private List<LuggageOptionEntity> outbound;

    @Schema(description = "Inbound/return luggage list")
    private List<LuggageOptionEntity> inbound;

    @Schema(description = "Can be purchased per journey")
    private Boolean perJourney;

    @Schema(description = "Can be purchased per passenger")
    private Boolean perPassenger;

    @Schema(description = "Service type (1=luggage)")
    private Integer serviceType;
}
