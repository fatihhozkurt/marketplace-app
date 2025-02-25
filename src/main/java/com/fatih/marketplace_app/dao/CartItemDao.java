package com.fatih.marketplace_app.dao;

import com.fatih.marketplace_app.entity.CartItemEntity;
import com.fatih.marketplace_app.repository.CartItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

/**
 * Data Access Object (DAO) for managing {@link CartItemEntity} operations.
 */
@Component
@RequiredArgsConstructor
public class CartItemDao {

    private final CartItemRepository cartItemRepository;

    /**
     * Saves the given cart item entity to the database.
     *
     * @param requestedCartItem the cart item entity to save
     * @return the saved {@link CartItemEntity}
     */
    public CartItemEntity save(CartItemEntity requestedCartItem) {
        return cartItemRepository.save(requestedCartItem);
    }

    /**
     * Finds a cart item by its unique identifier.
     *
     * @param cartItemId the UUID of the cart item
     * @return an {@link Optional} containing the found cart item, or empty if not found
     */
    public Optional<CartItemEntity> findById(UUID cartItemId) {
        return cartItemRepository.findById(cartItemId);
    }

    /**
     * Retrieves a paginated list of all cart items.
     *
     * @param pageable pagination information
     * @return a {@link Page} of {@link CartItemEntity} objects
     */
    public Page<CartItemEntity> findAll(Pageable pageable) {
        return cartItemRepository.findAll(pageable);
    }

    /**
     * Deletes the given cart item entity from the database.
     *
     * @param foundCartItem the cart item entity to delete
     */
    public void delete(CartItemEntity foundCartItem) {
        cartItemRepository.delete(foundCartItem);
    }

    /**
     * Retrieves a paginated list of all cart items associated with a specific cart.
     *
     * @param cartId   the UUID of the cart
     * @param pageable pagination information
     * @return a {@link Page} of {@link CartItemEntity} objects belonging to the given cart
     */
    public Page<CartItemEntity> findAllByCartId(UUID cartId, Pageable pageable) {
        return cartItemRepository.findAllByCart_Id(cartId, pageable);
    }
}