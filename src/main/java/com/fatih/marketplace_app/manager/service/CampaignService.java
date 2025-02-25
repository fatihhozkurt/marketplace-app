package com.fatih.marketplace_app.manager.service;

import com.fatih.marketplace_app.entity.CampaignEntity;
import com.fatih.marketplace_app.entity.CartEntity;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

/**
 * Service interface for managing campaign-related operations.
 */
public interface CampaignService {

    /**
     * Creates a new campaign.
     *
     * @param requestedCampaign The campaign entity to be created.
     * @return The created campaign entity.
     */
    CampaignEntity createCampaign(CampaignEntity requestedCampaign);

    /**
     * Retrieves a campaign by its unique ID.
     *
     * @param campaignId The unique identifier of the campaign.
     * @return The campaign entity if found.
     */
    CampaignEntity getCampaignById(UUID campaignId);

    /**
     * Retrieves all campaigns with pagination support.
     *
     * @param pageable The pagination information.
     * @return A paginated list of campaign entities.
     */
    Page<CampaignEntity> getAllCampaigns(Pageable pageable);

    /**
     * Updates an existing campaign.
     *
     * @param requestedCampaign The updated campaign entity.
     * @return The updated campaign entity.
     */
    CampaignEntity updateCampaign(CampaignEntity requestedCampaign);

    /**
     * Deletes a campaign by its unique ID.
     *
     * @param campaignId The unique identifier of the campaign to be deleted.
     */
    void deleteCampaign(UUID campaignId);

    /**
     * Retrieves a campaign by its campaign code.
     *
     * @param campaignCode The unique campaign code.
     * @return The campaign entity if found.
     */
    CampaignEntity getCampaignByCampaignCode(String campaignCode);

    /**
     * Applies a campaign discount to a cart.
     *
     * @param campaignCode The campaign code to be applied.
     * @param cartId       The unique identifier of the cart.
     * @return The updated cart entity after applying the campaign.
     */
    CartEntity applyCampaign(@NotNull String campaignCode, @NotNull UUID cartId);
}
