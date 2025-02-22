package com.fatih.marketplace_app.dto.request.campaign;

import com.fatih.marketplace_app.annotation.OptionalFieldValidation;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record UpdateCampaignRequest(

        @NotNull
        UUID id,

        @OptionalFieldValidation(notBlank = true, min = 5, max = 50)
        String campaignName,

        @OptionalFieldValidation(notBlank = true, min = 52, max = 500)
        String campaignDescription,

        @OptionalFieldValidation(notBlank = true, min = 5, max = 20)
        String campaignCode,

        @Positive
        BigDecimal discountValue,

        LocalDateTime startDate,

        LocalDateTime endDate
) {
}
