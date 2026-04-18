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
@Schema(description = "Refund/rescheduling/baggage rules")
public class RuleEntity {

    @Schema(description = "Allow refunds: 0=not allowed, 1=allowed")
    private Integer hasRefund;

    @Schema(description = "Refund fee tiers (e.g. 200-72-300-48-1000-0-*)")
    private String refund;

    @Schema(description = "Partial refund: 0=not allowed, 1=allowed")
    private Integer partRefund;

    @Schema(description = "Partial refund fee")
    private Integer partRefundPrice;

    @Schema(description = "Allow rescheduling: 0=not allowed, 1=allowed")
    private Integer hasEndorse;

    @Schema(description = "Rescheduling fee tiers")
    private String endorse;

    @Schema(description = "Partial rescheduling: 0=not allowed, 1=allowed")
    private Integer partEndorse;

    @Schema(description = "Partial rescheduling fee")
    private Integer partEndorsePrice;

    @Schema(description = "Endorsement: 0=not allowed, 1=allowed")
    private Integer endorsement;

    @Schema(description = "Free check-in baggage: 0=no, 1=yes")
    private Integer hasBaggage;

    @Schema(description = "Baggage allowance (e.g. 1-23 means 1PC, 23kg)")
    private String baggage;

    @Schema(description = "Hand bag allowance (e.g. 1-5 kg)")
    private String handBag;

    @Schema(description = "No-show rule: 0=no restrictions, 1=has restrictions")
    private Integer hasNoShow;

    @Schema(description = "No-show time limit (positive integer)")
    private Integer noShowLimitTime;

    @Schema(description = "No-show penalty")
    private Integer penalty;

    @Schema(description = "Special no-show rules: 0=none, 1=no refunds, 2=no rescheduling, 3=no refunds or rescheduling")
    private Integer specialNoShow;

    @Schema(description = "Other description, up to 300 characters")
    private String note;
}
