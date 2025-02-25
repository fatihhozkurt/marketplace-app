package com.fatih.marketplace_app.repository;

import com.fatih.marketplace_app.entity.CampaignEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for managing {@link CampaignEntity} persistence operations.
 */
@Repository
public interface CampaignRepository extends JpaRepository<CampaignEntity, UUID> {

    /**
     * Retrieves a campaign entity by its unique campaign code.
     *
     * @param campaignCode the unique campaign code.
     * @return an {@link Optional} containing the found {@link CampaignEntity}, or empty if no campaign exists with the given code.
     */
    Optional<CampaignEntity> findByCampaignCode(String campaignCode);
}
