package com.fatih.marketplace_app.controller;

import com.fatih.marketplace_app.controller.api.CampaignApi;
import com.fatih.marketplace_app.dto.request.campaign.ApplyCampaignRequest;
import com.fatih.marketplace_app.dto.request.campaign.CreateCampaignRequest;
import com.fatih.marketplace_app.dto.request.campaign.UpdateCampaignRequest;
import com.fatih.marketplace_app.dto.response.campaign.ApplyCampaignResponse;
import com.fatih.marketplace_app.dto.response.campaign.CampaignResponse;
import com.fatih.marketplace_app.entity.CampaignEntity;
import com.fatih.marketplace_app.entity.CartEntity;
import com.fatih.marketplace_app.manager.service.CampaignService;
import com.fatih.marketplace_app.mapper.CampaignMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Controller implementation for managing campaigns.
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class CampaignController implements CampaignApi {

    private final CampaignService campaignService;

    /**
     * Creates a new campaign.
     *
     * @param createCampaignRequest The request body containing campaign details.
     * @return The created campaign response.
     */
    @Override
    public ResponseEntity<CampaignResponse> createCampaign(CreateCampaignRequest createCampaignRequest) {

        log.info("Creating a new campaign with request: {}", createCampaignRequest);
        CampaignEntity requestedCampaign = CampaignMapper.INSTANCE.createCampaignToEntity(createCampaignRequest);
        CampaignEntity savedCampaign = campaignService.createCampaign(requestedCampaign);
        CampaignResponse campaignResponse = CampaignMapper.INSTANCE.toCampaignResponse(savedCampaign);
        log.info("Campaign created successfully with ID: {}", savedCampaign.getId());

        return new ResponseEntity<>(campaignResponse, HttpStatus.CREATED);
    }

    /**
     * Retrieves a campaign by its unique ID.
     *
     * @param campaignId The unique identifier of the campaign.
     * @return The campaign response.
     */
    @Override
    public ResponseEntity<CampaignResponse> getCampaignById(UUID campaignId) {

        log.info("Fetching campaign by ID: {}", campaignId);
        CampaignEntity foundCampaign = campaignService.getCampaignById(campaignId);
        CampaignResponse campaignResponse = CampaignMapper.INSTANCE.toCampaignResponse(foundCampaign);
        log.info("Campaign retrieved successfully: {}", campaignResponse);

        return new ResponseEntity<>(campaignResponse, HttpStatus.FOUND);
    }

    /**
     * Retrieves all campaigns with pagination.
     *
     * @param pageable Pagination details.
     * @return A paginated list of campaign responses grouped by campaign ID.
     */
    @Override
    public ResponseEntity<PageImpl<Map<UUID, List<CampaignResponse>>>> getAllCampaigns(Pageable pageable) {

        log.info("Fetching all campaigns with pagination: {}", pageable);
        Page<CampaignEntity> campaignEntities = campaignService.getAllCampaigns(pageable);
        List<CampaignResponse> campaignResponses = CampaignMapper.INSTANCE.toCampaignResponseList(campaignEntities.getContent());
        Map<UUID, List<CampaignResponse>> campaignMap = campaignResponses.stream().collect(Collectors.groupingBy(CampaignResponse::id));
        log.info("Retrieved {} campaigns", campaignEntities.getTotalElements());

        return new ResponseEntity<>(new PageImpl<>(List.of(campaignMap), pageable, campaignEntities.getTotalElements()), HttpStatus.OK);
    }

    /**
     * Updates an existing campaign.
     *
     * @param updateCampaignRequest The request body containing updated campaign details.
     * @return The updated campaign response.
     */
    @Override
    public ResponseEntity<CampaignResponse> updateCampaign(UpdateCampaignRequest updateCampaignRequest) {

        log.info("Updating campaign with request: {}", updateCampaignRequest);
        CampaignEntity requestedCampaign = CampaignMapper.INSTANCE.updateCampaignRequestToEntity(updateCampaignRequest);
        CampaignEntity updatedCampaign = campaignService.updateCampaign(requestedCampaign);
        CampaignResponse campaignResponse = CampaignMapper.INSTANCE.toCampaignResponse(updatedCampaign);
        log.info("Campaign updated successfully with ID: {}", updatedCampaign.getId());

        return new ResponseEntity<>(campaignResponse, HttpStatus.OK);
    }

    /**
     * Deletes a campaign by its unique ID.
     *
     * @param campaignId The unique identifier of the campaign to be deleted.
     * @return HTTP status indicating the result of the deletion.
     */
    @Override
    public ResponseEntity<HttpStatus> deleteCampaign(UUID campaignId) {

        log.warn("Deleting campaign with ID: {}", campaignId);
        campaignService.deleteCampaign(campaignId);
        log.info("Campaign deleted successfully with ID: {}", campaignId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Retrieves a campaign by its unique campaign code.
     *
     * @param campaignCode The unique code of the campaign.
     * @return The campaign response.
     */
    @Override
    public ResponseEntity<CampaignResponse> getCampaignByCampaignCode(String campaignCode) {

        log.info("Fetching campaign by campaign code: {}", campaignCode);
        CampaignEntity foundCampaign = campaignService.getCampaignByCampaignCode(campaignCode);
        CampaignResponse campaignResponse = CampaignMapper.INSTANCE.toCampaignResponse(foundCampaign);
        log.info("Campaign retrieved successfully: {}", campaignResponse);

        return new ResponseEntity<>(campaignResponse, HttpStatus.FOUND);
    }

    /**
     * Applies a campaign to a cart.
     *
     * @param applyCampaignRequest The request body containing campaign code and cart ID.
     * @return The response indicating the applied campaign details.
     */
    @Override
    public ResponseEntity<ApplyCampaignResponse> applyCampaign(ApplyCampaignRequest applyCampaignRequest) {

        log.info("Applying campaign {} to cart {}", applyCampaignRequest.campaignCode(), applyCampaignRequest.cartId());
        CartEntity updatedCart = campaignService.applyCampaign(applyCampaignRequest.campaignCode(), applyCampaignRequest.cartId());

        ApplyCampaignResponse campaignResponse = new ApplyCampaignResponse(
                updatedCart.getId(),
                updatedCart.getCampaign().getDiscountValue(),
                updatedCart.getCartPrice()
        );
        log.info("Campaign applied successfully. Updated cart: {}", updatedCart.getId());

        return new ResponseEntity<>(campaignResponse, HttpStatus.OK);
    }
}
