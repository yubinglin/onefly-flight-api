package com.onefly.flight.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
@Schema(description = "Flight search request")
public class SearchRequest {

    @NotBlank(message = "cid is required")
    @Schema(description = "API ID username (channel unique ID)")
    private String cid;

    @NotBlank(message = "tripType is required")
    @Schema(description = "Trip type: 1 for one-way, 2 for return")
    private String tripType;

    @NotNull(message = "adultNum is required")
    @Min(value = 1, message = "adultNum must be between 1 and 9")
    @Max(value = 9, message = "adultNum must be between 1 and 9")
    @Schema(description = "Number of adult passengers (1-9)")
    private Integer adultNum;

    @NotNull(message = "childNum is required")
    @Min(value = 0, message = "childNum must be between 0 and 9")
    @Max(value = 9, message = "childNum must be between 0 and 9")
    @Schema(description = "Number of child passengers (0-9)")
    private Integer childNum;

    @Schema(description = "Number of infant passengers, less than the number of adults")
    private Integer infantNum;

    @NotBlank(message = "fromCity is required")
    @Schema(description = "Departure city IATA 3-digit code")
    private String fromCity;

    @NotBlank(message = "toCity is required")
    @Schema(description = "Destination/arrival city IATA 3-digit code")
    private String toCity;

    @NotBlank(message = "fromDate is required")
    @Schema(description = "Outbound date in YYYYMMDD format")
    private String fromDate;

    @Schema(description = "Return date in YYYYMMDD format (leave blank for one-way)")
    private String retDate;
}
