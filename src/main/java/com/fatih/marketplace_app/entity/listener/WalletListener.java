package com.fatih.marketplace_app.entity.listener;

import com.fatih.marketplace_app.entity.WalletEntity;
import jakarta.persistence.PrePersist;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Entity listener for {@link WalletEntity} to handle pre-persist operations.
 */
@Component
public class WalletListener {

    /**
     * Initializes the wallet balance to {@link BigDecimal#ZERO} before persisting a new {@link WalletEntity}.
     *
     * @param walletEntity the wallet entity being persisted
     */
    @PrePersist
    public void prePersist(WalletEntity walletEntity) {
        walletEntity.setBalance(BigDecimal.ZERO);
    }
}
