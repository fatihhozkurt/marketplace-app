package com.fatih.marketplace_app.manager.service;

import com.fatih.marketplace_app.entity.CartEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

/**
 * Service interface for managing cart operations.
 */
public interface CartService {

    /**
     * Creates a new cart.
     *
     * @param cartEntity The cart entity to be created.
     * @return The created cart entity.
     */
    CartEntity createCart(CartEntity cartEntity);

    /**
     * Retrieves a cart by its unique ID.
     *
     * @param cartId The unique identifier of the cart.
     * @return The cart entity if found.
     */
    CartEntity getCartById(UUID cartId);

    /**
     * Retrieves a cart associated with a specific user.
     *
     * @param userId The unique identifier of the user.
     * @return The cart entity associated with the given user ID.
     */
    CartEntity getCartByUserId(UUID userId);

    /**
     * Retrieves all carts with pagination support.
     *
     * @param pageable The pagination information.
     * @return A paginated list of cart entities.
     */
    Page<CartEntity> getAllCarts(Pageable pageable);

    /**
     * Deletes a cart by its unique ID.
     *
     * @param cartId The unique identifier of the cart to be deleted.
     */
    void deleteCart(UUID cartId);

    /**
     * Clears all items from a cart.
     *
     * @param cartId The unique identifier of the cart to be cleared.
     */
    void clearCart(UUID cartId);

    /**
     * Updates an existing cart.
     *
     * @param requestedCart The updated cart entity.
     * @return The updated cart entity.
     */
    CartEntity updateCart(CartEntity requestedCart);
}