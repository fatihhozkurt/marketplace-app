package com.fatih.marketplace_app.dto.response.campaign;

import com.fatih.marketplace_app.enums.CampaignType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record CampaignResponse(

        UUID id,

        String campaignName,

        String campaignDescription,

        String campaignCode,

        CampaignType campaignType,

        BigDecimal discountValue,

        LocalDateTime startDate,

        LocalDateTime endDate
) {
}
