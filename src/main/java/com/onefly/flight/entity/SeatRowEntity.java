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
@Schema(description = "Seat row section")
public class SeatRowEntity {

    @Schema(description = "Start row number in current section")
    private Integer startRowNumber;

    @Schema(description = "End row number in current section")
    private Integer endRowNumber;

    @Schema(description = "Seat column names in each row")
    private List<String> column;
}
