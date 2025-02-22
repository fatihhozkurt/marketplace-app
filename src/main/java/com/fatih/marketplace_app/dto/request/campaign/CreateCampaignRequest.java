package com.fatih.marketplace_app.dto.request.campaign;

import com.fatih.marketplace_app.enums.CampaignType;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CreateCampaignRequest(

        @NotNull
        @NotBlank
        @Size(min = 5, max = 50)
        String campaignName,

        @NotNull
        @NotBlank
        @Size(min = 50, max = 500)
        String campaignDescription,

        @NotNull
        @NotBlank
        @Size(min = 5, max = 20)
        String campaignCode,

        @NotNull
        @NotBlank
        @Size(min = 5, max = 20)
        CampaignType campaignType,

        @NotNull
        @NotBlank
        @Positive
        BigDecimal discountValue,

        @NotNull
        @NotBlank
        LocalDateTime startDate,

        @NotNull
        @NotBlank
        LocalDateTime endDate
) {
}
