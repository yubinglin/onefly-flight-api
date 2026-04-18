package com.onefly.flight.dto.response;

import com.onefly.flight.entity.RoutingEntity;
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
@Schema(description = "Search API response")
public class SearchResponse {

    @Schema(description = "Status: 0=success, other=failure")
    private int status;

    @Schema(description = "Response message")
    private String msg;

    @Schema(description = "List of routing/pricing information")
    private List<RoutingEntity> routings;
}
