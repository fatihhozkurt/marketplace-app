package com.fatih.marketplace_app.repository;

import com.fatih.marketplace_app.entity.CartItemEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Repository interface for managing {@link CartItemEntity} persistence operations.
 */
@Repository
public interface CartItemRepository extends JpaRepository<CartItemEntity, UUID> {

    /**
     * Retrieves a paginated list of cart items associated with a specific cart.
     *
     * @param cartId   the unique identifier of the cart.
     * @param pageable the pagination and sorting information.
     * @return a {@link Page} containing {@link CartItemEntity} instances for the specified cart.
     */
    Page<CartItemEntity> findAllByCart_Id(UUID cartId, Pageable pageable);
}
