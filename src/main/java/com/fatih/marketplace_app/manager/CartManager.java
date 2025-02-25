package com.fatih.marketplace_app.manager;

import com.fatih.marketplace_app.dao.CartDao;
import com.fatih.marketplace_app.entity.CartEntity;
import com.fatih.marketplace_app.entity.UserEntity;
import com.fatih.marketplace_app.exception.DataAlreadyExistException;
import com.fatih.marketplace_app.exception.ResourceNotFoundException;
import com.fatih.marketplace_app.manager.service.CartService;
import com.fatih.marketplace_app.manager.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

/**
 * Manager class responsible for cart operations.
 * Provides functionality for creating, retrieving, updating and deleting carts.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CartManager implements CartService {

    private final CartDao cartDao;
    private final UserService userService;
    private final MessageSource messageSource;

    /**
     * Creates a new cart for a user.
     *
     * @param cartEntity The cart entity to be created
     * @return The created cart entity
     * @throws DataAlreadyExistException if the user already has a cart
     */
    @Transactional
    @Override
    public CartEntity createCart(CartEntity cartEntity) {

        log.info("Creating new cart for user with ID: {}", cartEntity.getUser().getId());
        UserEntity foundUser = userService.getUserById(cartEntity.getUser().getId());

        if (foundUser.getCart() != null) {
            log.warn("User with ID: {} already has a cart", foundUser.getId());
            throw new DataAlreadyExistException(messageSource
                    .getMessage("backend.exceptions.CRT003",
                            new Object[]{},
                            Locale.getDefault()));
        }
        log.info("Cart created successfully");
        return cartDao.save(cartEntity);
    }

    /**
     * Retrieves a cart by its ID.
     *
     * @param cartId The ID of the cart to retrieve
     * @return The found cart entity
     * @throws ResourceNotFoundException if no cart exists with the given ID
     */
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public CartEntity getCartById(UUID cartId) {
        log.info("Retrieving cart with ID: {}", cartId);
        return cartDao.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException(messageSource
                        .getMessage("backend.exceptions.CRT001",
                                new Object[]{cartId},
                                Locale.getDefault())));
    }

    /**
     * Retrieves a cart by user ID.
     *
     * @param userId The ID of the user whose cart to retrieve
     * @return The found cart entity
     * @throws ResourceNotFoundException if the user has no cart
     */
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public CartEntity getCartByUserId(UUID userId) {
        log.info("Retrieving cart for user with ID: {}", userId);

        userService.getUserById(userId);
        return cartDao.findByUserId(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(messageSource.getMessage("backend.exceptions.CRT002",
                                new Object[]{userId},
                                Locale.getDefault())));
    }

    /**
     * Retrieves all carts with pagination.
     *
     * @param pageable Pagination information
     * @return A page of cart entities
     */
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public Page<CartEntity> getAllCarts(Pageable pageable) {
        log.info("Retrieving all carts with pagination. Page: {}, Size: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        return cartDao.findAll(pageable);
    }

    /**
     * Deletes a cart by its ID.
     *
     * @param cartId The ID of the cart to delete
     * @throws ResourceNotFoundException if no cart exists with the given ID
     */
    @Transactional
    @Override
    public void deleteCart(UUID cartId) {
        log.info("Deleting cart with ID: {}", cartId);
        CartEntity foundCart = getCartById(cartId);
        cartDao.delete(foundCart);
        log.info("Cart with ID: {} deleted successfully", cartId);
    }

    /**
     * Clears all items from a cart and resets its price to zero.
     *
     * @param cartId The ID of the cart to clear
     * @throws ResourceNotFoundException if no cart exists with the given ID
     */
    @Transactional
    @Override
    public void clearCart(UUID cartId) {
        log.info("Clearing cart with ID: {}", cartId);

        CartEntity foundCart = getCartById(cartId);
        foundCart.getCartItem().clear();
        foundCart.setCartPrice(BigDecimal.ZERO);
        cartDao.save(foundCart);

        log.info("Cart with ID: {} cleared successfully", cartId);
    }

    /**
     * Updates a cart with new information.
     *
     * @param requestedCart The cart with updated information
     * @return The updated cart entity
     * @throws ResourceNotFoundException if no cart exists with the given ID
     */
    @Transactional
    @Override
    public CartEntity updateCart(CartEntity requestedCart) {
        log.info("Updating cart with ID: {}", requestedCart.getId());

        CartEntity foundCart = getCartById(requestedCart.getId());
        CartEntity updatedCart = checkCartConditions(foundCart, requestedCart);

        return cartDao.save(updatedCart);
    }

    /**
     * Validates and updates cart properties based on requested changes.
     *
     * @param foundCart The existing cart
     * @param requestedCart The cart with requested changes
     * @return The cart with applied changes
     */
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public CartEntity checkCartConditions(CartEntity foundCart, CartEntity requestedCart) {
        log.debug("Checking conditions for cart update. Cart ID: {}", foundCart.getId());

        if (requestedCart.getCartPrice() != null) {
            log.debug("Updating cart price from {} to {}", foundCart.getCartPrice(), requestedCart.getCartPrice());
            foundCart.setCartPrice(requestedCart.getCartPrice());
        }
        return foundCart;
    }

    /**
     * Scheduled task that runs every 15 minutes to clean up expired carts.
     * An expired cart is one that hasn't been updated in the last 15 minutes.
     */
    @Scheduled(cron = "0 0/15 * * * ?")
    @Transactional
    public void cleanUpExpiredCarts() {
        log.info("Starting scheduled cleanup of expired carts");

        LocalDateTime expirationTime = LocalDateTime.now().minusMinutes(15);
        List<CartEntity> expiredCarts = cartDao.findByUpdateTimeBefore(expirationTime);

        log.info("Found {} expired carts to clean up", expiredCarts.size());

        if (!expiredCarts.isEmpty()) {
            expiredCarts.forEach(cartEntity -> {
                if (cartEntity.getCartItem() != null) {
                    log.debug("Cleaning up expired cart with ID: {}", cartEntity.getId());

                    cartEntity.getCartItem().clear();
                    cartEntity.setCartPrice(BigDecimal.ZERO);
                    cartDao.save(cartEntity);
                }
            });
        }
    }
}
