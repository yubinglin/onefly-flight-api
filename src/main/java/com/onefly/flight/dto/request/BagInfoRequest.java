package com.onefly.flight.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Bag info request")
public class BagInfoRequest {

    @NotBlank(message = "data is required")
    @Schema(description = "The data value returned by verify call")
    private String data;

    @NotBlank(message = "flightNo is required")
    @Schema(description = "Flight number (transfer/connect)")
    private String flightNo;

    @NotBlank(message = "depAirport is required")
    @Schema(description = "Departure airport")
    private String depAirport;

    @NotBlank(message = "arrAirport is required")
    @Schema(description = "Arrival airport")
    private String arrAirport;

    @NotBlank(message = "depTime is required")
    @Schema(description = "Departure time yyyyMMddHHmm")
    private String depTime;

    @Schema(description = "Return flight number (transfer/connect)")
    private String retFlightNo;

    @Schema(description = "Return time yyyyMMddHHmm")
    private String retTime;
}
