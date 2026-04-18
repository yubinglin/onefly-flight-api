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
@Schema(description = "Seat layout information")
public class SeatLayoutEntity {

    @Schema(description = "First row number in whole floor")
    private Integer firstRowNumber;

    @Schema(description = "Last row number in whole floor")
    private Integer lastRowNumber;

    @Schema(description = "Row sections")
    private List<SeatRowEntity> row;
}
