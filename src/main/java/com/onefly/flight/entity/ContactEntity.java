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
@Schema(description = "Contact information")
public class ContactEntity {

    @NotBlank(message = "Contact name is required")
    @Schema(description = "Contact name")
    private String name;

    @Schema(description = "Address")
    private String address;

    @Schema(description = "Postcode")
    private String postcode;

    @Schema(description = "Mobile number")
    private String mobile;

    @NotBlank(message = "Email is required")
    @Schema(description = "Email address")
    private String email;
}
