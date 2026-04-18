package com.onefly.flight.dto.request;

import com.onefly.flight.entity.RoutingEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Price verification request")
public class VerifyRequest {

    @NotBlank(message = "cid is required")
    @Schema(description = "Interface ID username (channel unique ID)")
    private String cid;

    @NotBlank(message = "tripType is required")
    @Schema(description = "Trip type: 1 for one-way, 2 for return")
    private String tripType;

    @NotNull(message = "adultNum is required")
    @Schema(description = "Number of adult passengers (1-9)")
    private Integer adultNum;

    @Schema(description = "Number of child passengers (0-8)")
    private Integer childNum;

    @Schema(description = "Number of infant passengers")
    private Integer infantNum;

    @NotNull(message = "routing is required")
    @Schema(description = "Routing information from search results")
    private RoutingEntity routing;

    @Schema(description = "If true, return resident discount price")
    private Boolean isResidentDiscountEligible;
}
