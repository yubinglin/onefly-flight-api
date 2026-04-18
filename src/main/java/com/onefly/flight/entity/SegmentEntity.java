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
@Schema(description = "Flight segment element")
public class SegmentEntity {

    @Schema(description = "Airline IATA two-digit code")
    private String carrier;

    @Schema(description = "Flight number (e.g. CA123)")
    private String flightNumber;

    @Schema(description = "Departure airport IATA three-digit code")
    private String depAirport;

    @Schema(description = "Departure date/time (YYYYMMDDHHMM)")
    private String depTime;

    @Schema(description = "Arrival airport IATA three-digit code")
    private String arrAirport;

    @Schema(description = "Arrival date/time (YYYYMMDDHHMM)")
    private String arrTime;

    @Schema(description = "Stopover city codes separated by /")
    private String stopCities;

    @Schema(description = "Code sharing flag")
    private Boolean codeShare;

    @Schema(description = "Cabin")
    private String cabin;

    @Schema(description = "Aircraft code")
    private String aircraftCode;

    @Schema(description = "Cabin class: 1=economy, 2=business, 3=first")
    private Integer cabinClass;

    @Schema(description = "Cabin seat count")
    private Integer cabinCount;

    @Schema(description = "Flight duration in minutes")
    private Integer duration;

    @Schema(description = "Code sharing flight number")
    private String sharingFlightNumber;
}
