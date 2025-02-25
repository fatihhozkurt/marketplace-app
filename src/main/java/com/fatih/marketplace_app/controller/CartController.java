package com.fatih.marketplace_app.controller;

import com.fatih.marketplace_app.controller.api.CartApi;
import com.fatih.marketplace_app.dto.request.cart.CreateCartRequest;
import com.fatih.marketplace_app.dto.request.cart.UpdateCartRequest;
import com.fatih.marketplace_app.dto.response.cart.CartResponse;
import com.fatih.marketplace_app.entity.CartEntity;
import com.fatih.marketplace_app.manager.service.CartService;
import com.fatih.marketplace_app.mapper.CartMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Controller for managing shopping carts.
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class CartController implements CartApi {

    private final CartService cartService;

    /**
     * Creates a new cart.
     *
     * @param createCartRequest The request body containing cart details.
     * @return The created cart response.
     */
    @Override
    public ResponseEntity<CartResponse> createCart(CreateCartRequest createCartRequest) {

        log.info("Creating new cart with request: {}", createCartRequest);
        CartEntity cartEntity = CartMapper.INSTANCE.createCartRequestToEntity(createCartRequest);
        CartEntity createdCart = cartService.createCart(cartEntity);
        CartResponse cartResponse = CartMapper.INSTANCE.toCartResponse(createdCart);
        log.info("Cart created successfully with ID: {}", cartResponse.cartId());

        return new ResponseEntity<>(cartResponse, HttpStatus.CREATED);
    }

    /**
     * Retrieves a cart by its unique ID.
     *
     * @param cartId The unique identifier of the cart.
     * @return The cart response.
     */
    @Override
    public ResponseEntity<CartResponse> getCartById(UUID cartId) {

        log.info("Fetching cart by ID: {}", cartId);
        CartEntity foundCart = cartService.getCartById(cartId);
        CartResponse cartResponse = CartMapper.INSTANCE.toCartResponse(foundCart);
        log.info("Cart fetched successfully: {}", cartResponse);

        return new ResponseEntity<>(cartResponse, HttpStatus.FOUND);
    }

    /**
     * Retrieves a cart by the user ID.
     *
     * @param userId The user identifier.
     * @return The cart response.
     */
    @Override
    public ResponseEntity<CartResponse> getCartByUserId(UUID userId) {

        log.info("Fetching cart for user ID: {}", userId);
        CartEntity foundCart = cartService.getCartByUserId(userId);
        CartResponse cartResponse = CartMapper.INSTANCE.toCartResponse(foundCart);
        log.info("Cart for user {} fetched successfully", userId);

        return new ResponseEntity<>(cartResponse, HttpStatus.FOUND);
    }

    /**
     * Retrieves all carts with pagination.
     *
     * @param pageable Pagination details.
     * @return A paginated list of cart responses grouped by cart ID.
     */
    @Override
    public ResponseEntity<PageImpl<Map<UUID, List<CartResponse>>>> getAllCarts(Pageable pageable) {

        log.info("Fetching all carts with pageable: {}", pageable);
        Page<CartEntity> cartEntities = cartService.getAllCarts(pageable);
        List<CartResponse> cartResponses = CartMapper.INSTANCE.toCartResponseList(cartEntities.getContent());
        Map<UUID, List<CartResponse>> cartMap = cartResponses.stream().collect(Collectors.groupingBy(CartResponse::cartId));
        log.info("Fetched {} carts successfully", cartResponses.size());

        return new ResponseEntity<>(new PageImpl<>(List.of(cartMap), pageable, cartEntities.getTotalElements()), HttpStatus.OK);
    }

    /**
     * Deletes a cart by its unique ID.
     *
     * @param cartId The unique identifier of the cart to be deleted.
     * @return HTTP status indicating the result of the deletion.
     */
    @Override
    public ResponseEntity<HttpStatus> deleteCart(UUID cartId) {

        log.warn("Deleting cart with ID: {}", cartId);
        cartService.deleteCart(cartId);
        log.info("Cart with ID {} deleted successfully", cartId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Clears all items from a cart.
     *
     * @param cartId The unique identifier of the cart to be cleared.
     * @return HTTP status indicating the result of the operation.
     */
    @Override
    public ResponseEntity<HttpStatus> clearCart(UUID cartId) {

        log.warn("Clearing cart with ID: {}", cartId);
        cartService.clearCart(cartId);
        log.info("Cart with ID {} cleared successfully", cartId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Updates an existing cart.
     *
     * @param updateCartRequest The request body containing updated cart details.
     * @return The updated cart response.
     */
    @Override
    public ResponseEntity<CartResponse> updateCart(UpdateCartRequest updateCartRequest) {

        log.info("Updating cart with request: {}", updateCartRequest);
        CartEntity requestedCart = CartMapper.INSTANCE.updateCartRequestToEntity(updateCartRequest);
        CartEntity updatedCart = cartService.updateCart(requestedCart);
        CartResponse cartResponse = CartMapper.INSTANCE.toCartResponse(updatedCart);
        log.info("Cart updated successfully with ID: {}", cartResponse.cartId());

        return new ResponseEntity<>(cartResponse, HttpStatus.OK);
    }
}