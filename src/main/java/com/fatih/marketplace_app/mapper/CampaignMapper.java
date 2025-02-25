package com.fatih.marketplace_app.mapper;

import com.fatih.marketplace_app.dto.request.campaign.CreateCampaignRequest;
import com.fatih.marketplace_app.dto.request.campaign.UpdateCampaignRequest;
import com.fatih.marketplace_app.dto.response.campaign.CampaignResponse;
import com.fatih.marketplace_app.entity.CampaignEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * Mapper interface for converting campaign-related DTOs to entities and vice versa.
 */
@Mapper
public interface CampaignMapper {

    /**
     * Singleton instance of the CampaignMapper.
     */
    CampaignMapper INSTANCE = Mappers.getMapper(CampaignMapper.class);

    /**
     * Converts a {@link CreateCampaignRequest} to a {@link CampaignEntity}.
     *
     * @param createCampaignRequest The request DTO containing campaign creation details.
     * @return The mapped {@link CampaignEntity}.
     */
    CampaignEntity createCampaignToEntity(CreateCampaignRequest createCampaignRequest);

    /**
     * Converts an {@link UpdateCampaignRequest} to a {@link CampaignEntity}.
     *
     * @param updateCampaignRequest The request DTO containing campaign update details.
     * @return The mapped {@link CampaignEntity}.
     */
    CampaignEntity updateCampaignRequestToEntity(UpdateCampaignRequest updateCampaignRequest);

    /**
     * Converts a {@link CampaignEntity} to a {@link CampaignResponse}.
     *
     * @param campaignEntity The campaign entity to be converted.
     * @return The mapped {@link CampaignResponse}.
     */
    CampaignResponse toCampaignResponse(CampaignEntity campaignEntity);

    /**
     * Converts a list of {@link CampaignEntity} objects to a list of {@link CampaignResponse} objects.
     *
     * @param campaignEntities The list of campaign entities to be converted.
     * @return The mapped list of {@link CampaignResponse} objects.
     */
    List<CampaignResponse> toCampaignResponseList(List<CampaignEntity> campaignEntities);
}
