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
@Schema(description = "Auxiliary list per passenger")
public class AuxListEntity {

    @Schema(description = "Passenger name")
    private String passagerName;

    @Schema(description = "Auxiliary items (baggage, seat, cabin bag)")
    private List<AuxItemEntity> auxItems;
}
