package com.onefly.flight.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Credit card payment information")
public class PayCardEntity {

    @NotBlank(message = "cardType is required")
    @Schema(description = "Card type: AX=American Express, DN=Diners Club, DS=Discover, MC=Master, VI=Visa, VE=Visa Electron, JC=JCB")
    private String cardType;

    @NotBlank(message = "accountName is required")
    @Schema(description = "Credit card holder name")
    private String accountName;

    @NotBlank(message = "cardNumber is required")
    @Schema(description = "Card number")
    private String cardNumber;

    @NotBlank(message = "validTime is required")
    @Schema(description = "Card expire date: YYMM")
    private String validTime;

    @Schema(description = "CVV code")
    private String cvv;
}
