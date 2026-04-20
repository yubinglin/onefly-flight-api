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
@Schema(description = "Extra information for MYB")
public class ExtInfoEntity {

    @Schema(description = "Login account to manage booking")
    private String account;

    @Schema(description = "Login password to manage booking")
    private String password;

    @Schema(description = "Email used for booking")
    private String bookEmail;
}
