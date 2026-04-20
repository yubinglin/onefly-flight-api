package com.onefly.flight.dto.response;

import com.onefly.flight.entity.SeatMapEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Seat selection response")
public class SeatSelectionResponse {

    @Schema(description = "Status: 0=success, other=failure")
    private int status;

    @Schema(description = "Response message")
    private String msg;

    @Schema(description = "Seat map, key is flight number, value is seat info")
    private Map<String, SeatMapEntity> seats;
}
