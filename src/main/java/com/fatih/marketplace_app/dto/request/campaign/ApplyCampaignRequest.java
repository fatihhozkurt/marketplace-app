package com.fatih.marketplace_app.dto.request.campaign;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

public record ApplyCampaignRequest(

        @NotNull
        String campaignCode,

        @NotNull
        @Positive
        UUID cartId
) {
}
