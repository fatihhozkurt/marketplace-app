package com.fatih.marketplace_app.dao;

import com.fatih.marketplace_app.entity.WalletEntity;
import com.fatih.marketplace_app.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class WalletDao {

    private final WalletRepository walletRepository;

    public WalletEntity save(WalletEntity requestedWallet) {
        return walletRepository.save(requestedWallet);
    }


    public Optional<WalletEntity> findById(UUID walletId) {
        return walletRepository.findById(walletId);
    }

    public Page<WalletEntity> findAll(Pageable pageable) {
        return walletRepository.findAll(pageable);
    }

    public void deleteWallet(WalletEntity foundWallet) {
        walletRepository.delete(foundWallet);
    }

    public Optional<WalletEntity> findByUserId(UUID userId) {
        return walletRepository.findByUser_Id(userId);
    }
}
