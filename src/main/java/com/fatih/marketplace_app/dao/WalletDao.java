package com.fatih.marketplace_app.dao;

import com.fatih.marketplace_app.entity.WalletEntity;
import com.fatih.marketplace_app.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

/**
 * Data Access Object (DAO) for managing {@link WalletEntity} operations.
 */
@Component
@RequiredArgsConstructor
public class WalletDao {


    private final WalletRepository walletRepository;

    /**
     * Saves the given wallet entity to the database.
     *
     * @param requestedWallet the wallet entity to save
     * @return the saved {@link WalletEntity}
     */
    public WalletEntity save(WalletEntity requestedWallet) {
        return walletRepository.save(requestedWallet);
    }

    /**
     * Finds a wallet by its unique identifier.
     *
     * @param walletId the UUID of the wallet
     * @return an {@link Optional} containing the found wallet, or empty if not found
     */
    public Optional<WalletEntity> findById(UUID walletId) {
        return walletRepository.findById(walletId);
    }

    /**
     * Retrieves a paginated list of all wallets.
     *
     * @param pageable pagination information
     * @return a {@link Page} of {@link WalletEntity} objects
     */
    public Page<WalletEntity> findAll(Pageable pageable) {
        return walletRepository.findAll(pageable);
    }

    /**
     * Deletes the given wallet entity from the database.
     *
     * @param foundWallet the wallet entity to delete
     */
    public void deleteWallet(WalletEntity foundWallet) {
        walletRepository.delete(foundWallet);
    }

    /**
     * Finds a wallet by the associated user ID.
     *
     * @param userId the UUID of the user
     * @return an {@link Optional} containing the found wallet, or empty if not found
     */
    public Optional<WalletEntity> findByUserId(UUID userId) {
        return walletRepository.findByUser_Id(userId);
    }
}
