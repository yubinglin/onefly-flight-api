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
@Schema(description = "Available seat information")
public class AvailableSeatEntity {

    @Schema(description = "Seat number (e.g. 1A)")
    private String designator;

    @Schema(description = "Emergency exit seat")
    private Boolean emergencyExit;

    @Schema(description = "Window seat")
    private Boolean windowSeat;

    @Schema(description = "Child allowed")
    private Boolean childAllowed;

    @Schema(description = "Extra leg room")
    private Boolean extraLegRoom;

    @Schema(description = "Aisle seat")
    private Boolean aisle;

    @Schema(description = "Infant allowed")
    private Boolean infantAllowed;

    @Schema(description = "Price group identifier")
    private String group;
}
