package com.fatih.marketplace_app.controller.api;

import com.fatih.marketplace_app.dto.request.campaign.ApplyCampaignRequest;
import com.fatih.marketplace_app.dto.request.campaign.CreateCampaignRequest;
import com.fatih.marketplace_app.dto.request.campaign.UpdateCampaignRequest;
import com.fatih.marketplace_app.dto.response.campaign.ApplyCampaignResponse;
import com.fatih.marketplace_app.dto.response.campaign.CampaignResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.fatih.marketplace_app.constant.UrlConst.*;

/**
 * API interface for managing campaigns.
 */
@RequestMapping(CAMPAIGN)
public interface CampaignApi {

    /**
     * Creates a new campaign.
     *
     * @param createCampaignRequest Request body containing campaign details.
     * @return The created campaign response.
     */
    @PostMapping
    ResponseEntity<CampaignResponse> createCampaign(@RequestBody @Valid CreateCampaignRequest createCampaignRequest);

    /**
     * Retrieves a campaign by its ID.
     *
     * @param campaignId The unique identifier of the campaign.
     * @return The campaign response.
     */
    @GetMapping(ID)
    ResponseEntity<CampaignResponse> getCampaignById(@RequestParam("campaignId") @NotNull UUID campaignId);

    /**
     * Retrieves all campaigns with pagination.
     *
     * @param pageable Pagination details.
     * @return A paginated list of campaign responses grouped by user ID.
     */
    @GetMapping(ALL)
    ResponseEntity<PageImpl<Map<UUID, List<CampaignResponse>>>> getAllCampaigns(Pageable pageable);

    /**
     * Updates an existing campaign.
     *
     * @param updateCampaignRequest Request body containing updated campaign details.
     * @return The updated campaign response.
     */
    @PutMapping
    ResponseEntity<CampaignResponse> updateCampaign(@RequestBody @Valid UpdateCampaignRequest updateCampaignRequest);

    /**
     * Deletes a campaign by its ID.
     *
     * @param campaignId The unique identifier of the campaign.
     * @return HTTP status indicating the result of the operation.
     */
    @DeleteMapping
    ResponseEntity<HttpStatus> deleteCampaign(@RequestParam("campaignId") @NotNull UUID campaignId);

    /**
     * Retrieves a campaign by its unique campaign code.
     *
     * @param campaignCode The unique campaign code (must be between 5 and 20 characters).
     * @return The campaign response.
     */
    @GetMapping(CODE)
    ResponseEntity<CampaignResponse> getCampaignByCampaignCode(@RequestParam("campaignCode") @NotNull @Size(min = 5, max = 20) String campaignCode);

    /**
     * Applies a campaign to a user's cart or order.
     *
     * @param applyCampaignRequest Request body containing campaign application details.
     * @return The response after applying the campaign.
     */
    @PutMapping(APPLY)
    ResponseEntity<ApplyCampaignResponse> applyCampaign(@RequestBody @Valid ApplyCampaignRequest applyCampaignRequest);
}
