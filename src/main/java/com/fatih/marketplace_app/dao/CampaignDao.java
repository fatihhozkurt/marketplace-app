package com.fatih.marketplace_app.dao;

import com.fatih.marketplace_app.entity.CampaignEntity;
import com.fatih.marketplace_app.repository.CampaignRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

/**
 * Data Access Object (DAO) for managing {@link CampaignEntity} operations.
 */
@Component
@RequiredArgsConstructor
public class CampaignDao {

    private final CampaignRepository campaignRepository;

    /**
     * Saves the given campaign entity to the database.
     *
     * @param requestedCampaign the campaign entity to save
     * @return the saved {@link CampaignEntity}
     */

    public CampaignEntity save(CampaignEntity requestedCampaign) {
        return campaignRepository.save(requestedCampaign);
    }

    /**
     * Finds a campaign by its unique identifier.
     *
     * @param campaignId the UUID of the campaign
     * @return an {@link Optional} containing the found campaign, or empty if not found
     */
    public Optional<CampaignEntity> findById(UUID campaignId) {
        return campaignRepository.findById(campaignId);
    }

    /**
     * Retrieves a paginated list of all campaigns.
     *
     * @param pageable pagination information
     * @return a {@link Page} of {@link CampaignEntity} objects
     */
    public Page<CampaignEntity> findAll(Pageable pageable) {
        return campaignRepository.findAll(pageable);
    }

    /**
     * Deletes the given campaign entity from the database.
     *
     * @param foundCampaign the campaign entity to delete
     */
    public void delete(CampaignEntity foundCampaign) {
        campaignRepository.delete(foundCampaign);
    }

    /**
     * Finds a campaign by its unique campaign code.
     *
     * @param campaignCode the unique campaign code
     * @return an {@link Optional} containing the found campaign, or empty if not found
     */

    public Optional<CampaignEntity> findByCampaignCode(String campaignCode) {
        return campaignRepository.findByCampaignCode(campaignCode);
    }
}