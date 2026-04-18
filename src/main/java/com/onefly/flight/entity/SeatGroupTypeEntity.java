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
@Schema(description = "Seat price group type")
public class SeatGroupTypeEntity {

    @Schema(description = "Price")
    private String price;

    @Schema(description = "Currency")
    private String currency;

    @Schema(description = "Group identifier")
    private String group;
}
