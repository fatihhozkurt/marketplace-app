package com.fatih.marketplace_app.dto.request.campaign;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record ApplyCampaignRequest(

        @NotNull
        @NotBlank
        @Size(min = 5, max = 20)
        String campaignCode,

        @NotNull
        UUID cartId
) {
}
