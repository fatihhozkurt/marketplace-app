package com.fatih.marketplace_app.entity.listener;

import com.fatih.marketplace_app.entity.WalletEntity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class WalletListener {

    @PrePersist
    public void prePersist(WalletEntity walletEntity) {
        walletEntity.setBalance(BigDecimal.valueOf(0.0));
    }

    @PreUpdate
    public void preUpdate(WalletEntity walletEntity) {
        if (walletEntity.getRecordStatus()) {
            walletEntity.setBalance(BigDecimal.valueOf(0.0));
        }
    }

}
