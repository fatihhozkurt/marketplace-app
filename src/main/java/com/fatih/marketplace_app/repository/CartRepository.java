package com.fatih.marketplace_app.repository;

import com.fatih.marketplace_app.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for managing {@link CartEntity} persistence operations.
 */
@Repository
public interface CartRepository extends JpaRepository<CartEntity, UUID> {

    /**
     * Finds a cart associated with a specific user.
     *
     * @param userId the unique identifier of the user.
     * @return an {@link Optional} containing the {@link CartEntity} if found, otherwise empty.
     */
    Optional<CartEntity> findByUser_Id(UUID userId);

    /**
     * Retrieves a list of carts that were last updated before the specified expiration time.
     *
     * @param expirationTime the time threshold for outdated carts.
     * @return a list of {@link CartEntity} instances that have not been updated since the given time.
     */
    List<CartEntity> findByUpdateTimeBefore(LocalDateTime expirationTime);
}
