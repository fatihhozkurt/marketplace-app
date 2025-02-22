package com.fatih.marketplace_app.strategy;

import com.fatih.marketplace_app.enums.CampaignType;
import com.fatih.marketplace_app.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class DiscountStrategyFactory {

    private final MessageSource messageSource;

    public DiscountStrategyService getStrategy(CampaignType campaignType) {
        switch (campaignType) {
            case FIXED:
                return new FixedDiscountStrategyManager();
            case PERCENTAGE:
                return new PercentageDiscountStrategyManager();
            default:
                throw new BusinessException(messageSource.getMessage(
                        "backend.exceptions.CMP002",
                        new Object[]{campaignType},
                        Locale.getDefault()));
        }
    }
}

