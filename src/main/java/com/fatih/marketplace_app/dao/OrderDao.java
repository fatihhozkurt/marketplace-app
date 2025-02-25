package com.fatih.marketplace_app.dao;

import com.fatih.marketplace_app.entity.OrderEntity;
import com.fatih.marketplace_app.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

/**
 * Data Access Object (DAO) for managing {@link OrderEntity} operations.
 */
@Component
@RequiredArgsConstructor
public class OrderDao {

    private final OrderRepository orderRepository;

    /**
     * Saves the given order entity to the database.
     *
     * @param requestedOrder the order entity to save
     * @return the saved {@link OrderEntity}
     */
    public OrderEntity save(OrderEntity requestedOrder) {
        return orderRepository.save(requestedOrder);
    }

    /**
     * Finds an order by its unique identifier.
     *
     * @param orderId the UUID of the order
     * @return an {@link Optional} containing the found order, or empty if not found
     */
    public Optional<OrderEntity> findById(UUID orderId) {
        return orderRepository.findById(orderId);
    }

    /**
     * Retrieves a paginated list of all orders.
     *
     * @param pageable pagination information
     * @return a {@link Page} of {@link OrderEntity} objects
     */
    public Page<OrderEntity> findAll(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }

    /**
     * Retrieves a paginated list of orders by user ID.
     *
     * @param userId   the UUID of the user
     * @param pageable pagination information
     * @return a {@link Page} of {@link OrderEntity} objects
     */
    public Page<OrderEntity> findAllByUserId(UUID userId, Pageable pageable) {
        return orderRepository.findAllByUser_Id(userId, pageable);
    }

    /**
     * Deletes the given order entity from the database.
     *
     * @param order the order entity to delete
     */
    public void delete(OrderEntity order) {
        orderRepository.delete(order);
    }

    /**
     * Finds an order by its order number.
     *
     * @param orderNumber the order number
     * @return an {@link Optional} containing the found order, or empty if not found
     */
    public Optional<OrderEntity> findByOrderNumber(String orderNumber) {
        return orderRepository.findByOrderNumber(orderNumber);
    }
}