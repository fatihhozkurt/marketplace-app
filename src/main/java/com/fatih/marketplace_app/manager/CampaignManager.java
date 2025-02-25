package com.fatih.marketplace_app.manager;

import com.fatih.marketplace_app.dao.CampaignDao;
import com.fatih.marketplace_app.entity.CampaignEntity;
import com.fatih.marketplace_app.entity.CartEntity;
import com.fatih.marketplace_app.exception.ResourceNotFoundException;
import com.fatih.marketplace_app.manager.service.CampaignService;
import com.fatih.marketplace_app.manager.service.CartService;
import com.fatih.marketplace_app.strategy.DiscountStrategyFactory;
import com.fatih.marketplace_app.strategy.DiscountStrategyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.UUID;

/**
 * Manages operations related to campaigns, including creation, retrieval, updating,
 * and applying campaigns to carts.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CampaignManager implements CampaignService {

    private final CampaignDao campaignDao;
    private final MessageSource messageSource;
    private final CartService cartService;
    private final DiscountStrategyFactory discountStrategyFactory;

    /**
     * Creates a new campaign.
     *
     * @param requestedCampaign The campaign entity to be created.
     * @return The saved campaign entity.
     */
    @Transactional
    @Override
    public CampaignEntity createCampaign(CampaignEntity requestedCampaign) {

        log.info("Creating a new campaign: {}", requestedCampaign);
        if (requestedCampaign.getDiscountValue().compareTo(BigDecimal.ZERO) <= 0) {
            log.warn("Invalid discount value for campaign: {}", requestedCampaign);
            throw new ResourceNotFoundException(messageSource
                    .getMessage("backend.exceptions.CMP004",
                            new Object[]{},
                            Locale.getDefault()));
        }

        return campaignDao.save(requestedCampaign);
    }

    /**
     * Retrieves a campaign by its ID.
     *
     * @param campaignId The UUID of the campaign.
     * @return The found campaign entity.
     */
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public CampaignEntity getCampaignById(UUID campaignId) {
        log.info("Fetching campaign by ID: {}", campaignId);
        return campaignDao.findById(campaignId).orElseThrow(() ->
                new ResourceNotFoundException(messageSource
                        .getMessage("backend.exceptions.CMP001",
                                new Object[]{campaignId},
                                Locale.getDefault())));
    }

    /**
     * Retrieves all campaigns with pagination.
     *
     * @param pageable Pageable object for pagination.
     * @return A page of campaign entities.
     */
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public Page<CampaignEntity> getAllCampaigns(Pageable pageable) {
        log.info("Fetching all campaigns with pagination.");
        return campaignDao.findAll(pageable);
    }

    /**
     * Updates an existing campaign.
     *
     * @param requestedCampaign The campaign entity with updated fields.
     * @return The updated campaign entity.
     */
    @Transactional
    @Override
    public CampaignEntity updateCampaign(CampaignEntity requestedCampaign) {

        log.info("Updating campaign: {}", requestedCampaign);
        CampaignEntity foundCampaign = getCampaignById(requestedCampaign.getId());
        CampaignEntity updatedCampaign = checkUpdateConditions(requestedCampaign, foundCampaign);

        return campaignDao.save(updatedCampaign);
    }

    /**
     * Deletes a campaign by its ID.
     *
     * @param campaignId The UUID of the campaign to be deleted.
     */
    @Transactional
    @Override
    public void deleteCampaign(UUID campaignId) {

        log.warn("Deleting campaign with ID: {}", campaignId);
        CampaignEntity foundCampaign = getCampaignById(campaignId);

        campaignDao.delete(foundCampaign);
    }

    /**
     * Retrieves a campaign by its campaign code.
     *
     * @param campaignCode The unique campaign code.
     * @return The found campaign entity.
     */
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public CampaignEntity getCampaignByCampaignCode(String campaignCode) {
        log.info("Fetching campaign by code: {}", campaignCode);
        return campaignDao.findByCampaignCode(campaignCode).orElseThrow(() ->
                new ResourceNotFoundException(messageSource
                        .getMessage("backend.exceptions.CMP002",
                                new Object[]{campaignCode},
                                Locale.getDefault())));
    }

    /**
     * Applies a campaign to a cart, adjusting the cart price accordingly.
     *
     * @param campaignCode The campaign code.
     * @param cartId       The cart ID.
     * @return The updated cart entity.
     */
    @Transactional
    @Override
    public CartEntity applyCampaign(String campaignCode, UUID cartId) {
        log.info("Applying campaign '{}' to cart '{}'.", campaignCode, cartId);
        CartEntity foundCart = cartService.getCartById(cartId);
        CampaignEntity foundCampaign = getCampaignByCampaignCode(campaignCode);

        if (foundCart.getCampaign() != null) {
            log.warn("Cart '{}' already has a campaign applied.", cartId);
            throw new ResourceNotFoundException(messageSource
                    .getMessage("backend.exceptions.CMP005",
                            new Object[]{},
                            Locale.getDefault()));
        }

        DiscountStrategyService discountStrategy = discountStrategyFactory.getStrategy(foundCampaign.getCampaignType());

        BigDecimal discountedPrice = discountStrategy.applyDiscount(foundCart.getCartPrice(), foundCampaign.getDiscountValue());

        foundCart.setCartPrice(discountedPrice);
        foundCart.setCampaign(foundCampaign);
        cartService.updateCart(foundCart);

        return foundCart;
    }

    /**
     * Checks and updates the campaign fields if they are not null.
     *
     * @param requestedCampaign The new campaign details.
     * @param foundCampaign     The existing campaign entity.
     * @return The updated campaign entity.
     */
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public CampaignEntity checkUpdateConditions(CampaignEntity requestedCampaign, CampaignEntity foundCampaign) {
        log.info("Checking update conditions for campaign '{}'.", foundCampaign.getId());

        if (requestedCampaign.getCampaignName() != null) {
            foundCampaign.setCampaignName(requestedCampaign.getCampaignName());
        }
        if (requestedCampaign.getCampaignDescription() != null) {
            foundCampaign.setCampaignDescription(requestedCampaign.getCampaignDescription());
        }
        if (requestedCampaign.getCampaignCode() != null) {
            foundCampaign.setCampaignCode(requestedCampaign.getCampaignCode());
        }
        if (requestedCampaign.getDiscountValue() != null) {
            foundCampaign.setDiscountValue(requestedCampaign.getDiscountValue());
        }
        if (requestedCampaign.getStartDate() != null) {
            foundCampaign.setStartDate(requestedCampaign.getStartDate());
        }
        if (requestedCampaign.getEndDate() != null) {
            foundCampaign.setEndDate(requestedCampaign.getEndDate());
        }

        return foundCampaign;
    }
}
