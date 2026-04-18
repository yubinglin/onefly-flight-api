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
@Schema(description = "Seat map for a flight")
public class SeatMapEntity {

    @Schema(description = "Aircraft type")
    private String planeType;

    @Schema(description = "Seat price groups")
    private List<SeatGroupTypeEntity> groupType;

    @Schema(description = "Available seats")
    private List<AvailableSeatEntity> availableSeat;

    @Schema(description = "Unavailable seat numbers")
    private List<String> unavailableSeat;

    @Schema(description = "Seat layout information")
    private SeatLayoutEntity layout;
}
