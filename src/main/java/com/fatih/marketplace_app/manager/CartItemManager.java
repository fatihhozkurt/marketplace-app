package com.fatih.marketplace_app.manager;

import com.fatih.marketplace_app.dao.CartItemDao;
import com.fatih.marketplace_app.entity.CartEntity;
import com.fatih.marketplace_app.entity.CartItemEntity;
import com.fatih.marketplace_app.entity.ProductEntity;
import com.fatih.marketplace_app.exception.ResourceNotFoundException;
import com.fatih.marketplace_app.manager.service.CartItemService;
import com.fatih.marketplace_app.manager.service.CartService;
import com.fatih.marketplace_app.manager.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

/**
 * Service class responsible for managing cart items.
 * Provides methods for creating, updating, retrieving, and deleting cart items.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CartItemManager implements CartItemService {

    private final CartItemDao cartItemDao;
    private final MessageSource messageSource;
    private final CartService cartService;
    private final ProductService productService;

    /**
     * Creates or updates a cart item.
     * If the item already exists, it updates the quantity and price.
     * Otherwise, it creates a new cart item.
     *
     * @param requestedCartItem the cart item to be added or updated
     * @return the saved cart item entity
     */
    @Transactional
    @Override
    public CartItemEntity createCartItem(CartItemEntity requestedCartItem) {
        log.info("Creating or updating cart item with product ID: {}, cart ID: {}",
                requestedCartItem.getProduct().getId(), requestedCartItem.getCart().getId());
        ProductEntity foundProduct = productService.getProductById(requestedCartItem.getProduct().getId());
        CartEntity foundCart = cartService.getCartById(requestedCartItem.getCart().getId());

        log.debug("Product found: {}, Cart found: {}", foundProduct, foundCart);

        Optional<CartItemEntity> matchingCartItemOpt = findMatchingCartItem(foundCart, foundProduct);

        if (matchingCartItemOpt.isPresent()) {
            CartItemEntity existingCartItem = matchingCartItemOpt.get();
            log.debug("Matching cart item found: {}", existingCartItem);
            updateExistingCartItem(existingCartItem, requestedCartItem.getProductQuantity(), foundProduct.getProductPrice());
            log.debug("Updated cart item: {}", existingCartItem);
            return cartItemDao.save(existingCartItem);
        } else {
            BigDecimal newCartItemPrice = foundProduct.getProductPrice()
                    .multiply(BigDecimal.valueOf(requestedCartItem.getProductQuantity()));
            requestedCartItem.setCartItemPrice(newCartItemPrice);
            foundCart.setCartPrice(foundCart.getCartPrice().add(newCartItemPrice));
            log.info("Creating new cart item with ID: {} and total price: {}", requestedCartItem.getId(), newCartItemPrice);
            return cartItemDao.save(requestedCartItem);
        }
    }

    /**
     * Finds an existing cart item in the given cart for the specified product.
     *
     * @param cart    the cart entity
     * @param product the product entity
     * @return an optional containing the matching cart item if found
     */
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Optional<CartItemEntity> findMatchingCartItem(CartEntity cart, ProductEntity product) {
        log.debug("Searching for matching cart item in cart ID: {} for product ID: {}", cart.getId(), product.getId());
        return cart.getCartItem().stream()
                .filter(cartItem -> cartItem.getProduct().getId().equals(product.getId()))
                .findFirst();
    }

    /**
     * Updates the quantity and price of an existing cart item.
     *
     * @param existingCartItem  the cart item to be updated
     * @param additionalQuantity the quantity to be added
     * @param productPrice       the price of the product
     */
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public void updateExistingCartItem(CartItemEntity existingCartItem, int additionalQuantity, BigDecimal productPrice) {
        log.debug("Updating existing cart item: {}, additional quantity: {}, product price: {}",
                existingCartItem.getId(), additionalQuantity, productPrice);
        int updatedQuantity = existingCartItem.getProductQuantity() + additionalQuantity;
        existingCartItem.setProductQuantity(updatedQuantity);
        existingCartItem.setCartItemPrice(
                productPrice.multiply(BigDecimal.valueOf(updatedQuantity))
        );
        existingCartItem.getCart().setCartPrice(existingCartItem.getCart().getCartPrice().add(existingCartItem.getCartItemPrice()));
        log.info("Cart item updated successfully. New quantity: {}, New price: {}", updatedQuantity, existingCartItem.getCartItemPrice());
    }

    /**
     * Retrieves a cart item by its ID.
     *
     * @param cartItemId the ID of the cart item
     * @return the cart item entity
     * @throws ResourceNotFoundException if the cart item is not found
     */
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public CartItemEntity getCartItemById(UUID cartItemId) {
        log.info("Fetching cart item by ID: {}", cartItemId);
        return cartItemDao.findById(cartItemId)
                .orElseThrow(() -> new ResourceNotFoundException(messageSource.getMessage(
                        "backend.exceptions.CIT001",
                        new Object[]{cartItemId},
                        Locale.getDefault())));
    }

    /**
     * Updates an existing cart item with new quantity and price.
     *
     * @param requestedCartItem the updated cart item entity
     * @return the updated cart item
     */
    @Transactional
    @Override
    public CartItemEntity updateCartItem(CartItemEntity requestedCartItem) {
        log.info("Updating cart item: {}", requestedCartItem);
        CartItemEntity foundCartItem = getCartItemById(requestedCartItem.getId());
        log.debug("Existing cart item before update: {}", foundCartItem);
        CartItemEntity updatedCartItem = checkCartItemConditions(foundCartItem, requestedCartItem);
        log.debug("Updated cart item after save: {}", updatedCartItem);

        return cartItemDao.save(updatedCartItem);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public Page<CartItemEntity> getAllCartItems(Pageable pageable) {
        log.info("Fetching all cart items with pagination: {}", pageable);
        return cartItemDao.findAll(pageable);
    }

    /**
     * Deletes a cart item by its ID.
     *
     * @param cartItemId the ID of the cart item to be deleted
     */
    @Transactional
    @Override
    public void deleteCartItem(UUID cartItemId) {
        log.info("Deleting cart item by ID: {}", cartItemId);
        CartItemEntity foundCartItem = getCartItemById(cartItemId);
        cartItemDao.delete(foundCartItem);
        log.info("Cart item successfully deleted: {}", cartItemId);
    }

    /**
     * Removes a product from the cart item.
     *
     * @param cartItemId the ID of the cart item
     * @param productId  the ID of the product to be removed
     * @return the updated cart item
     */
    @Transactional
    @Override
    public CartItemEntity removeProductFromCartItem(UUID cartItemId, UUID productId) {

        log.info("Removing one quantity of product ID: {} from cart item ID: {}", productId, cartItemId);
        productService.getProductById(productId);
        CartItemEntity foundCartItem = getCartItemById(cartItemId);

        if (foundCartItem.getProductQuantity() <= 0) {
            log.warn("Product quantity is 1 or less. Deleting cart item.");
            deleteCartItem(cartItemId);
        }

        foundCartItem.setProductQuantity(foundCartItem.getProductQuantity() - 1);
        foundCartItem.setCartItemPrice(
                foundCartItem.getProduct().getProductPrice().multiply(
                        BigDecimal.valueOf(foundCartItem.getProductQuantity())
                )
        );

        log.info("Product removed successfully. New quantity: {}, New total price: {}",
                foundCartItem.getProductQuantity(), foundCartItem.getCartItemPrice());

        return cartItemDao.save(foundCartItem);
    }

    /**
     * Adds a product to the cart item.
     *
     * @param cartItemId the ID of the cart item
     * @param productId  the ID of the product to be added
     * @return the updated cart item
     */
    @Transactional
    @Override
    public CartItemEntity addProductToCartItem(UUID cartItemId, UUID productId) {
        log.info("Adding one quantity of product ID: {} to cart item ID: {}", productId, cartItemId);

        productService.getProductById(productId);
        CartItemEntity foundCartItem = getCartItemById(cartItemId);

        foundCartItem.setProductQuantity(foundCartItem.getProductQuantity() + 1);
        foundCartItem.setCartItemPrice(
                foundCartItem.getProduct().getProductPrice().multiply(
                        BigDecimal.valueOf(foundCartItem.getProductQuantity())
                )
        );

        log.info("Product added successfully. New quantity: {}, New total price: {}",
                foundCartItem.getProductQuantity(), foundCartItem.getCartItemPrice());

        return cartItemDao.save(foundCartItem);
    }

    /**
     * Retrieves a paginated list of cart items associated with a specific cart ID.
     *
     * @param cartId   The unique identifier of the cart.
     * @param pageable The pagination information.
     * @return A paginated list of cart items.
     */
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public Page<CartItemEntity> getCartItemsByCartId(UUID cartId, Pageable pageable) {
        log.info("Fetching cart items for cart ID: {}", cartId);
        cartService.getCartById(cartId);
        log.info("Retrieved car items");
        return cartItemDao.findAllByCartId(cartId, pageable);
    }

    /**
     * Checks and updates the conditions of a cart item based on the requested changes.
     *
     * @param foundCartItem    The existing cart item retrieved from the database.
     * @param requestedCartItem The cart item containing the requested updates.
     * @return The updated cart item with modified quantity or price.
     */
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public CartItemEntity checkCartItemConditions(CartItemEntity foundCartItem, CartItemEntity requestedCartItem) {

        log.info("Checking cart item conditions for item ID: {}", foundCartItem.getId());

        if (requestedCartItem.getProductQuantity() != null) {
            log.debug("Updating product quantity from {} to {}",
                    foundCartItem.getProductQuantity(),
                    requestedCartItem.getProductQuantity());

            foundCartItem.setProductQuantity(requestedCartItem.getProductQuantity());

            foundCartItem.setCartItemPrice(foundCartItem.getProduct().getProductPrice().multiply(BigDecimal.valueOf(requestedCartItem.getProductQuantity())));
        }
        if (requestedCartItem.getCartItemPrice() != null) {
            log.debug("Updating cart item price from {} to {}",
                    foundCartItem.getCartItemPrice(),
                    requestedCartItem.getCartItemPrice());

            foundCartItem.setCartItemPrice(requestedCartItem.getCartItemPrice());
        }
        log.info("Cart item conditions updated successfully for item ID: {}", foundCartItem.getId());

        return foundCartItem;
    }
}
