package com.fatih.marketplace_app.mapper;

import com.fatih.marketplace_app.dto.request.campaign.CreateCampaignRequest;
import com.fatih.marketplace_app.dto.request.campaign.UpdateCampaignRequest;
import com.fatih.marketplace_app.dto.response.campaign.CampaignResponse;
import com.fatih.marketplace_app.entity.CampaignEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CampaignMapper {

    CampaignMapper INSTANCE = Mappers.getMapper(CampaignMapper.class);

    CampaignEntity createCampaignToEntity(CreateCampaignRequest createCampaignRequest);

    CampaignEntity updateCampaignRequestToEntity(UpdateCampaignRequest updateCampaignRequest);

    CampaignResponse toCampaignResponse(CampaignEntity campaignEntity);

    List<CampaignResponse> toCampaignResponseList(List<CampaignEntity> campaignEntities);
}
