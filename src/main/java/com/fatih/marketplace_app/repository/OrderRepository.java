package com.fatih.marketplace_app.repository;

import com.fatih.marketplace_app.entity.OrderEntity;
import com.fatih.marketplace_app.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for managing {@link OrderEntity} persistence operations.
 */
@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, UUID> {

    /**
     * Retrieves all orders associated with a specific user.
     *
     * @param user the {@link UserEntity} for whom the orders are retrieved.
     * @return a list of {@link OrderEntity} associated with the user.
     */
    List<OrderEntity> user(UserEntity user);

    /**
     * Finds all orders placed by a specific user with pagination support.
     *
     * @param userId   the unique identifier of the user.
     * @param pageable the pagination information.
     * @return a paginated list of {@link OrderEntity} belonging to the user.
     */
    Page<OrderEntity> findAllByUser_Id(UUID userId, Pageable pageable);

    /**
     * Finds an order by its unique order number.
     *
     * @param orderNumber the unique order number.
     * @return an {@link Optional} containing the {@link OrderEntity} if found, otherwise empty.
     */
    Optional<OrderEntity> findByOrderNumber(String orderNumber);
}
