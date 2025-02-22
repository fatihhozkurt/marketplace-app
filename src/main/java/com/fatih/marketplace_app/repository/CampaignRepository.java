package com.fatih.marketplace_app.repository;

import com.fatih.marketplace_app.entity.CampaignEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CampaignRepository extends JpaRepository<CampaignEntity, UUID> {
    Optional<CampaignEntity> findByCampaignCode(String campaignCode);
}
