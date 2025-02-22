package com.fatih.marketplace_app.controller;

import com.fatih.marketplace_app.controller.api.CampaignApi;
import com.fatih.marketplace_app.dao.CampaignDao;
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

@RestController
@RequiredArgsConstructor
public class CampaignController implements CampaignApi {

    private final CampaignService campaignService;
    private final CampaignDao campaignDao;

    @Override
    public ResponseEntity<CampaignResponse> createCampaign(CreateCampaignRequest createCampaignRequest) {

        CampaignEntity requestedCampaign = CampaignMapper.INSTANCE.createCampaignToEntity(createCampaignRequest);
        CampaignEntity savedCampaign = campaignService.createCampaign(requestedCampaign);
        CampaignResponse campaignResponse = CampaignMapper.INSTANCE.toCampaignResponse(savedCampaign);

        return new ResponseEntity<>(campaignResponse, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<CampaignResponse> getCampaignById(UUID campaignId) {

        CampaignEntity foundCampaign = campaignService.getCampaignById(campaignId);
        CampaignResponse campaignResponse = CampaignMapper.INSTANCE.toCampaignResponse(foundCampaign);

        return new ResponseEntity<>(campaignResponse, HttpStatus.FOUND);
    }

    @Override
    public ResponseEntity<PageImpl<Map<UUID, List<CampaignResponse>>>> getAllCampaigns(Pageable pageable) {

        Page<CampaignEntity> campaignEntities = campaignService.getAllCampaigns(pageable);
        List<CampaignResponse> campaignResponses = CampaignMapper.INSTANCE.toCampaignResponseList(campaignEntities.getContent());
        Map<UUID, List<CampaignResponse>> campaignMap = campaignResponses.stream().collect(Collectors.groupingBy(CampaignResponse::id));

        return new ResponseEntity<>(new PageImpl<>(List.of(campaignMap), pageable, campaignEntities.getTotalElements()), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CampaignResponse> updateCampaign(UpdateCampaignRequest updateCampaignRequest) {

        CampaignEntity requestedCampaign = CampaignMapper.INSTANCE.updateCampaignRequestToEntity(updateCampaignRequest);
        CampaignEntity updatedCampaign = campaignService.updateCampaign(requestedCampaign);
        CampaignResponse campaignResponse = CampaignMapper.INSTANCE.toCampaignResponse(updatedCampaign);

        return new ResponseEntity<>(campaignResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<HttpStatus> deleteCampaign(UUID campaignId) {

        campaignService.deleteCampaign(campaignId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<CampaignResponse> getCampaignByCampaignCode(String campaignCode) {

        CampaignEntity foundCampaign = campaignService.getCampaignByCampaignCode(campaignCode);
        CampaignResponse campaignResponse = CampaignMapper.INSTANCE.toCampaignResponse(foundCampaign);

        return new ResponseEntity<>(campaignResponse, HttpStatus.FOUND);
    }

    @Override
    public ResponseEntity<ApplyCampaignResponse> applyCampaign(ApplyCampaignRequest applyCampaignRequest) {

        CartEntity updatedCart = campaignService.applyCampaign(applyCampaignRequest.campaignCode(), applyCampaignRequest.cartId());

        ApplyCampaignResponse campaignResponse = new ApplyCampaignResponse(
                updatedCart.getId(),
                updatedCart.getCampaign().getDiscountValue(),
                updatedCart.getCartPrice()
        );

        return new ResponseEntity<>(campaignResponse, HttpStatus.OK);
    }
}
