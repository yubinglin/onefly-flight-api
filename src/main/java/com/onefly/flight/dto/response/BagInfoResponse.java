package com.onefly.flight.dto.response;

import com.onefly.flight.entity.CabinBagEntity;
import com.onefly.flight.entity.LuggageEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Bag info response")
public class BagInfoResponse {

    @Schema(description = "Status: 0=success, other=failure")
    private int status;

    @Schema(description = "Response message")
    private String msg;

    @Schema(description = "Check-in bag info")
    private LuggageEntity luggage;

    @Schema(description = "Cabin bag info")
    private CabinBagEntity cabinBag;
}
