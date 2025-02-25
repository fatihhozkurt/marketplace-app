package com.fatih.marketplace_app.dao;

import com.fatih.marketplace_app.entity.CartEntity;
import com.fatih.marketplace_app.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Data Access Object (DAO) for managing {@link CartEntity} operations.
 */
@Component
@RequiredArgsConstructor
public class CartDao {

    private final CartRepository cartRepository;

    /**
     * Saves the given cart entity to the database.
     *
     * @param cartEntity the cart entity to save
     * @return the saved {@link CartEntity}
     */
    public CartEntity save(CartEntity cartEntity) {
        return cartRepository.save(cartEntity);
    }

    /**
     * Finds a cart by its unique identifier.
     *
     * @param cartId the UUID of the cart
     * @return an {@link Optional} containing the found cart, or empty if not found
     */
    public Optional<CartEntity> findById(UUID cartId) {
        return cartRepository.findById(cartId);
    }

    /**
     * Finds a cart associated with a specific user.
     *
     * @param userId the UUID of the user
     * @return an {@link Optional} containing the found cart, or empty if not found
     */
    public Optional<CartEntity> findByUserId(UUID userId) {
        return cartRepository.findByUser_Id(userId);
    }

    /**
     * Retrieves a paginated list of all carts.
     *
     * @param pageable pagination information
     * @return a {@link Page} of {@link CartEntity} objects
     */
    public Page<CartEntity> findAll(Pageable pageable) {
        return cartRepository.findAll(pageable);
    }

    /**
     * Deletes the given cart entity from the database.
     *
     * @param foundCart the cart entity to delete
     */
    public void delete(CartEntity foundCart) {
        cartRepository.delete(foundCart);
    }

    /**
     * Finds all carts that have an update time before the given expiration time.
     *
     * @param expirationTime the time threshold for filtering outdated carts
     * @return a {@link List} of {@link CartEntity} objects matching the criteria
     */
    public List<CartEntity> findByUpdateTimeBefore(LocalDateTime expirationTime) {
        return cartRepository.findByUpdateTimeBefore(expirationTime);
    }
}
