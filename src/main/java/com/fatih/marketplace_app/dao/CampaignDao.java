package com.fatih.marketplace_app.dao;

import com.fatih.marketplace_app.entity.CampaignEntity;
import com.fatih.marketplace_app.repository.CampaignRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CampaignDao {

    private final CampaignRepository campaignRepository;

    public CampaignEntity save(CampaignEntity requestedCampaign) {
        return campaignRepository.save(requestedCampaign);
    }

    public Optional<CampaignEntity> findById(UUID campaignId) {
        return campaignRepository.findById(campaignId);
    }

    public Page<CampaignEntity> findAll(Pageable pageable) {
        return campaignRepository.findAll(pageable);
    }

    public void delete(CampaignEntity foundCampaign) {
        campaignRepository.delete(foundCampaign);
    }

    public Optional<CampaignEntity> findByCampaignCode(String campaignCode) {
        return campaignRepository.findByCampaignCode(campaignCode);
    }
}