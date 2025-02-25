package com.fatih.marketplace_app.repository;

import com.fatih.marketplace_app.entity.WalletEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for managing {@link WalletEntity} persistence operations.
 */
@Repository
public interface WalletRepository extends JpaRepository<WalletEntity, UUID> {

    /**
     * Retrieves a wallet associated with a specific user ID.
     *
     * @param userId the unique identifier of the user
     * @return an {@link Optional} containing the wallet if found, otherwise empty
     */
    Optional<WalletEntity> findByUser_Id(UUID userId);
}
