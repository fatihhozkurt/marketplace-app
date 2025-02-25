package com.fatih.marketplace_app.manager.service;

import com.fatih.marketplace_app.entity.WalletEntity;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Service interface for managing wallet operations.
 */
public interface WalletService {

    /**
     * Creates a new wallet for a specific user.
     *
     * @param userId The unique identifier of the user.
     * @return The created wallet entity.
     */
    WalletEntity createWallet(UUID userId);

    /**
     * Retrieves a wallet by its unique ID.
     *
     * @param walletId The unique identifier of the wallet.
     * @return The wallet entity if found.
     */
    WalletEntity getWalletById(UUID walletId);

    /**
     * Retrieves all wallets with pagination support.
     *
     * @param pageable The pagination information.
     * @return A paginated list of wallet entities.
     */
    Page<WalletEntity> getAllWallets(Pageable pageable);

    /**
     * Deletes a wallet by its unique ID.
     *
     * @param walletId The unique identifier of the wallet to be deleted.
     */
    void deleteWallet(UUID walletId);

    /**
     * Adds balance to a wallet.
     *
     * @param walletId The unique identifier of the wallet.
     * @param amount The amount to be added (must be positive).
     * @return The updated wallet entity.
     */
    WalletEntity loadBalance(@NotNull UUID walletId, @NotNull @Positive BigDecimal amount);

    /**
     * Processes a payment from the wallet.
     *
     * @param walletId The unique identifier of the wallet.
     * @param amount The amount to be deducted (must be positive).
     * @return The updated wallet entity.
     */
    WalletEntity payment(@NotNull UUID walletId, @NotNull @Positive BigDecimal amount);

    /**
     * Adjusts the wallet balance by adding or setting a specific amount.
     *
     * @param walletId The unique identifier of the wallet.
     * @param amount The new balance amount (must be zero or positive).
     * @return The updated wallet entity.
     */
    WalletEntity changeBalance(@NotNull UUID walletId, @NotNull @PositiveOrZero BigDecimal amount);

    /**
     * Retrieves a wallet by the associated user ID.
     *
     * @param userId The unique identifier of the user.
     * @return The wallet entity if found.
     */
    WalletEntity getWalletByUserId(UUID userId);
}
