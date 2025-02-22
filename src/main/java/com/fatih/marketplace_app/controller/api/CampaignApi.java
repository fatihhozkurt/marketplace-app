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

@RequestMapping(CAMPAIGN)
public interface CampaignApi {

    @PostMapping
    ResponseEntity<CampaignResponse> createCampaign(@RequestBody @Valid CreateCampaignRequest createCampaignRequest);

    @GetMapping(ID)
    ResponseEntity<CampaignResponse> getCampaignById(@RequestParam("campaignId") @NotNull UUID campaignId);

    @GetMapping(ALL)
    ResponseEntity<PageImpl<Map<UUID, List<CampaignResponse>>>> getAllCampaigns(Pageable pageable);

    @PutMapping
    ResponseEntity<CampaignResponse> updateCampaign(@RequestBody @Valid UpdateCampaignRequest updateCampaignRequest);

    @DeleteMapping
    ResponseEntity<HttpStatus> deleteCampaign(@RequestParam("campaignId") @NotNull UUID campaignId);

    @GetMapping(CODE)
    ResponseEntity<CampaignResponse> getCampaignByCampaignCode(@RequestParam("campaignCode") @NotNull @Size(min = 5, max = 20) String campaignCode);

    @PostMapping(APPLY)
    ResponseEntity<ApplyCampaignResponse> applyCampaign(@RequestBody @Valid ApplyCampaignRequest applyCampaignRequest);
}
