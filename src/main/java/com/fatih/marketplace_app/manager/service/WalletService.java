package com.fatih.marketplace_app.manager.service;

import com.fatih.marketplace_app.entity.WalletEntity;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.UUID;

public interface WalletService {

    WalletEntity createWallet(UUID userId);

    WalletEntity getWalletById(UUID walletId);

    Page<WalletEntity> getAllWallets(Pageable pageable);

    void deleteWallet(UUID walletId);

    WalletEntity loadBalance(@NotNull UUID walletId, @NotNull @Positive BigDecimal amount);

    WalletEntity payment(@NotNull UUID walletId, @NotNull @Positive BigDecimal amount);

    WalletEntity changeBalance(@NotNull UUID walletId, @NotNull @PositiveOrZero BigDecimal amount);
}
