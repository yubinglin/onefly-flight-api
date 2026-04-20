package com.onefly.flight.dto.request;

import com.onefly.flight.entity.ContactEntity;
import com.onefly.flight.entity.PassengerEntity;
import com.onefly.flight.entity.RoutingEntity;
import com.onefly.flight.entity.AuxItemEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Order generation request (will be encrypted)")
public class OrderRequest {

    @NotBlank(message = "cid is required")
    @Schema(description = "Interface ID username (channel unique ID)")
    private String cid;

    @NotBlank(message = "tripType is required")
    @Schema(description = "Trip type: 1 for one-way, 2 for return")
    private String tripType;

    @NotBlank(message = "sessionId is required")
    @Schema(description = "Session ID from verify response")
    private String sessionId;

    @NotNull(message = "routing is required")
    @Valid
    @Schema(description = "Routing information from verify")
    private RoutingEntity routing;

    @NotEmpty(message = "passengers is required")
    @Valid
    @Schema(description = "Passenger information")
    private List<PassengerEntity> passengers;

    @Schema(description = "Auxiliary items (baggage, seat, cabin bag)")
    private List<AuxItemEntity> auxList;

    @NotNull(message = "contact is required")
    @Valid
    @Schema(description = "Contact information")
    private ContactEntity contact;
}
