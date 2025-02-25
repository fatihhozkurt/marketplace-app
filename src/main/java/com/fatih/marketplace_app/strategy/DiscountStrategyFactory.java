package com.fatih.marketplace_app.strategy;

import com.fatih.marketplace_app.enums.CampaignType;
import com.fatih.marketplace_app.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

/**
 * Factory class for providing the appropriate discount strategy based on the campaign type.
 */
@Service
@RequiredArgsConstructor
public class DiscountStrategyFactory {

    private final MessageSource messageSource;

    /**
     * Retrieves the discount strategy corresponding to the given campaign type.
     *
     * @param campaignType the type of the discount campaign
     * @return an instance of {@link DiscountStrategyService} based on the campaign type
     * @throws BusinessException if the campaign type is not recognized
     */
    public DiscountStrategyService getStrategy(CampaignType campaignType) {
        switch (campaignType) {
            case FIXED:
                return new FixedDiscountStrategyManager();
            case PERCENTAGE:
                return new PercentageDiscountStrategyManager();
            default:
                throw new BusinessException(messageSource.getMessage(
                        "backend.exceptions.CMP003",
                        new Object[]{campaignType},
                        Locale.getDefault()));
        }
    }
}

