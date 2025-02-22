package com.fatih.marketplace_app.manager.service;

import com.fatih.marketplace_app.entity.WalletEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface WalletService {

    WalletEntity createWallet(UUID userId);

    WalletEntity getWalletById(UUID walletId);

    Page<WalletEntity> getAllWallets(Pageable pageable);
}
