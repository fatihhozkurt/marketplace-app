package com.fatih.marketplace_app.manager.service;

import com.fatih.marketplace_app.entity.CampaignEntity;
import com.fatih.marketplace_app.entity.CartEntity;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface CampaignService {


    CampaignEntity createCampaign(CampaignEntity requestedCampaign);

    CampaignEntity getCampaignById(UUID campaignId);

    Page<CampaignEntity> getAllCampaigns(Pageable pageable);

    CampaignEntity updateCampaign(CampaignEntity requestedCampaign);

    void deleteCampaign(UUID campaignId);

    CampaignEntity getCampaignByCampaignCode(String campaignCode);

    CartEntity applyCampaign(@NotNull String campaignCode, @NotNull UUID cartId);
}
