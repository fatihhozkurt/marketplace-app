package com.fatih.marketplace_app.manager;

import com.fatih.marketplace_app.dao.CampaignDao;
import com.fatih.marketplace_app.entity.CampaignEntity;
import com.fatih.marketplace_app.entity.CartEntity;
import com.fatih.marketplace_app.exception.ResourceNotFoundException;
import com.fatih.marketplace_app.manager.service.CampaignService;
import com.fatih.marketplace_app.strategy.DiscountStrategyFactory;
import com.fatih.marketplace_app.strategy.DiscountStrategyService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CampaignManager implements CampaignService {

    private final CampaignDao campaignDao;
    private final MessageSource messageSource;
    private final CartService cartService;
    private final DiscountStrategyFactory discountStrategyFactory;

    @Transactional
    @Override
    public CampaignEntity createCampaign(CampaignEntity requestedCampaign) {
        return campaignDao.save(requestedCampaign);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public CampaignEntity getCampaignById(UUID campaignId) {
        return campaignDao.findById(campaignId).orElseThrow(() ->
                new ResourceNotFoundException(messageSource
                        .getMessage("backend.exceptions.CMP001",
                                new Object[]{campaignId},
                                Locale.getDefault())));
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public Page<CampaignEntity> getAllCampaigns(Pageable pageable) {
        return campaignDao.findAll(pageable);
    }

    @Transactional
    @Override
    public CampaignEntity updateCampaign(CampaignEntity requestedCampaign) {

        CampaignEntity foundCampaign = getCampaignById(requestedCampaign.getId());
        CampaignEntity updatedCampaign = checkUpdateCampaign(requestedCampaign, foundCampaign);

        return campaignDao.save(updatedCampaign);
    }

    @Transactional
    @Override
    public void deleteCampaign(UUID campaignId) {

        CampaignEntity foundCampaign = getCampaignById(campaignId);

        campaignDao.delete(foundCampaign);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public CampaignEntity getCampaignByCampaignCode(String campaignCode) {
        return campaignDao.findByCampaignCode(campaignCode).orElseThrow(() ->
                new ResourceNotFoundException(messageSource
                        .getMessage("backend.exceptions.CMP002",
                                new Object[]{campaignCode},
                                Locale.getDefault())));
    }


    //TODO: Finish it
    @Transactional
    @Override
    public CartEntity applyCampaign(String campaignCode, UUID cartId) {

        CartEntity foundCart = cartService.getCartById(cartId);
        CampaignEntity foundCampaign = getCampaignByCampaignCode(campaignCode);

        DiscountStrategyService discountStrategy = discountStrategyFactory.getStrategy(foundCampaign.getCampaignType());

        BigDecimal discountedPrice = discountStrategy.applyDiscount(foundCart.getCartPrice(), foundCampaign.getDiscountValue());

        foundCart.setCartPrice(discountedPrice);
        cartService.updateCart(foundCart);

        return foundCart;
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public CampaignEntity checkUpdateCampaign(CampaignEntity requestedCampaign, CampaignEntity foundCampaign) {

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
