package com.fatih.marketplace_app.manager.service;

import com.fatih.marketplace_app.entity.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

/**
 * Service interface for managing order operations.
 */

public interface OrderService {

    /**
     * Creates a new order.
     *
     * @param requestedOrder The order entity to be created.
     * @return The created order entity.
     */
    OrderEntity createOrder(OrderEntity requestedOrder);

    /**
     * Retrieves an order by its unique ID.
     *
     * @param orderId The unique identifier of the order.
     * @return The order entity if found.
     */
    OrderEntity getOrderById(UUID orderId);

    /**
     * Retrieves all orders with pagination support.
     *
     * @param pageable The pagination information.
     * @return A paginated list of order entities.
     */
    Page<OrderEntity> getAllOrders(Pageable pageable);

    /**
     * Cancels an order by its unique ID.
     *
     * @param orderId The unique identifier of the order to be canceled.
     */
    void cancelOrder(UUID orderId);

    /**
     * Retrieves all orders associated with a specific user ID.
     *
     * @param userId   The unique identifier of the user.
     * @param pageable The pagination information.
     * @return A paginated list of orders belonging to the specified user.
     */
    Page<OrderEntity> getOrdersByUserId(UUID userId, Pageable pageable);

    /**
     * Generates a random order number.
     *
     * @return A randomly generated order number as a string.
     */
    String generateRandomOrderNumber();

    /**
     * Retrieves an order by its order number.
     *
     * @param orderNumber The unique order number.
     * @return The order entity associated with the given order number.
     */
    OrderEntity getOrderByOrderNumber(String orderNumber);
}
