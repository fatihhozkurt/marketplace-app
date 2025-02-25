package com.fatih.marketplace_app.manager.service;

import com.fatih.marketplace_app.entity.CartItemEntity;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

/**
 * Service interface for managing cart item operations.
 */
public interface CartItemService {

    /**
     * Creates a new cart item.
     *
     * @param requestedCartItem The cart item entity to be created.
     * @return The created cart item entity.
     */
    CartItemEntity createCartItem(CartItemEntity requestedCartItem);

    /**
     * Retrieves a cart item by its unique ID.
     *
     * @param cartItemId The unique identifier of the cart item.
     * @return The cart item entity if found.
     */
    CartItemEntity getCartItemById(UUID cartItemId);

    /**
     * Updates an existing cart item.
     *
     * @param requestedCartItem The updated cart item entity.
     * @return The updated cart item entity.
     */
    CartItemEntity updateCartItem(CartItemEntity requestedCartItem);

    /**
     * Retrieves all cart items with pagination support.
     *
     * @param pageable The pagination information.
     * @return A paginated list of cart item entities.
     */
    Page<CartItemEntity> getAllCartItems(Pageable pageable);

    /**
     * Deletes a cart item by its unique ID.
     *
     * @param cartItemId The unique identifier of the cart item to be deleted.
     */
    void deleteCartItem(UUID cartItemId);

    /**
     * Removes a specific product from a cart item.
     *
     * @param cartItemId The unique identifier of the cart item.
     * @param productId  The unique identifier of the product to be removed.
     * @return The updated cart item entity after product removal.
     */
    CartItemEntity removeProductFromCartItem(@NotNull UUID cartItemId, @NotNull UUID productId);

    /**
     * Adds a specific product to a cart item.
     *
     * @param cartItemId The unique identifier of the cart item.
     * @param productId  The unique identifier of the product to be added.
     * @return The updated cart item entity after adding the product.
     */
    CartItemEntity addProductToCartItem(@NotNull UUID cartItemId, @NotNull UUID productId);

    /**
     * Retrieves cart items by cart ID with pagination support.
     *
     * @param cartId   The unique identifier of the cart.
     * @param pageable The pagination information.
     * @return A paginated list of cart item entities belonging to the specified cart.
     */
    Page<CartItemEntity> getCartItemsByCartId(UUID cartId, Pageable pageable);
}
