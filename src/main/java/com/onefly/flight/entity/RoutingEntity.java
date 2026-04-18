package com.onefly.flight.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Routing/pricing information element")
public class RoutingEntity {

    @Schema(description = "Data token for order generation")
    private String data;

    @Schema(description = "Currency (default CNY if not returned)")
    private String currency;

    @Schema(description = "Adult price")
    private Double adultPrice;

    @Schema(description = "Adult tax")
    private Double adultTax;

    @Schema(description = "Child price")
    private Double childPrice;

    @Schema(description = "Child tax")
    private Double childTax;

    @Schema(description = "Infant price")
    private Double infantPrice;

    @Schema(description = "Infant tax")
    private Double infantTax;

    @Schema(description = "Nationality type: 0=All, 1=Applicable, 2=Not Applicable")
    private Integer nationalityType;

    @Schema(description = "Nationality codes (two-character, comma separated)")
    private String nationality;

    @Schema(description = "Suitable age range (e.g. 12~59)")
    private String suitAge;

    @Schema(description = "Adult tax type: 0=without tax, 1=with tax")
    private Integer adultTaxType;

    @Schema(description = "Child tax type: 0=without tax, 1=with tax")
    private Integer childTaxType;

    @Schema(description = "Cache time")
    private Integer cacheTime;

    @Schema(description = "Cancellation and change rules")
    private RuleEntity rule;

    @Schema(description = "Outbound flight segments")
    private List<SegmentEntity> fromSegments;

    @Schema(description = "Return flight segments (empty for one-way)")
    private List<SegmentEntity> retSegments;

    @Schema(description = "Whether nationality is needed in order request")
    private Boolean isNeedNationality;

    @Schema(description = "Whether passport info is needed in order request")
    private Boolean isNeedPass;

    @Schema(description = "Resident town information")
    private List<ResidentTownEntity> residentTown;

    @Schema(description = "Extra information")
    private Map<String, Object> extraInfo;

    @Schema(description = "Apply type")
    private Integer applyType;

    @Schema(description = "Price type")
    private Integer priceType;
}
