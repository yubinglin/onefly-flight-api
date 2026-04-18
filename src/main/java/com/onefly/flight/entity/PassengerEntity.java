package com.onefly.flight.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Passenger information")
public class PassengerEntity {

    @NotBlank(message = "Passenger name is required")
    @Schema(description = "LastName/FirstName")
    private String name;

    @Schema(description = "Middle name (second surname for residents)")
    private String middleName;

    @NotNull(message = "ageType is required")
    @Schema(description = "Passenger type: 0=Adult, 1=Child, 2=Infant, -1=International Student")
    private Integer ageType;

    @NotBlank(message = "birthday is required")
    @Schema(description = "Birthday format: YYYYMMDD")
    private String birthday;

    @NotBlank(message = "gender is required")
    @Schema(description = "Passenger gender: M/F")
    private String gender;

    @Schema(description = "ID/passport number, up to 15 characters")
    private String cardNum;

    @Schema(description = "Card type: PP=passport, GA=HK/Macau Pass, TW=Taiwan Pass, etc.")
    private String cardType;

    @Schema(description = "Passport issuing country or resident municipality code")
    private String cardIssuePlace;

    @Schema(description = "Card expiry date: YYYYMMDD")
    private String cardExpired;

    @Schema(description = "Passenger nationality, two-digit country code")
    private String nationality;

    @Schema(description = "Document code for Vueling/Volotea residents")
    private String doucmentCode;

    @Schema(description = "Ticket number (returned in order details)")
    private String ticketNo;
}
