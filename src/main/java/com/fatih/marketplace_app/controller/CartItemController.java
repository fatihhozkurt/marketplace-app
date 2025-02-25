package com.fatih.marketplace_app.controller;

import com.fatih.marketplace_app.controller.api.CartItemApi;
import com.fatih.marketplace_app.dto.request.cartItem.AddProductToCartItemRequest;
import com.fatih.marketplace_app.dto.request.cartItem.CreateCartItemRequest;
import com.fatih.marketplace_app.dto.request.cartItem.RemoveProductFromCartItemRequest;
import com.fatih.marketplace_app.dto.request.cartItem.UpdateCartItemRequest;
import com.fatih.marketplace_app.dto.response.cartItem.CartItemResponse;
import com.fatih.marketplace_app.entity.CartItemEntity;
import com.fatih.marketplace_app.manager.service.CartItemService;
import com.fatih.marketplace_app.mapper.CartItemMapper;
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
 * Controller for managing cart items.
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class CartItemController implements CartItemApi {

    private final CartItemService cartItemService;

    /**
     * Creates a new cart item.
     *
     * @param createCartItemRequest The request body containing cart item details.
     * @return The created cart item response.
     */
    @Override
    public ResponseEntity<CartItemResponse> creatCartItem(CreateCartItemRequest createCartItemRequest) {

        log.info("Creating new cart item: {}", createCartItemRequest);
        CartItemEntity requestedCartItem = CartItemMapper.INSTANCE.createCartItemRequestToEntity(createCartItemRequest);
        CartItemEntity savedCartItem = cartItemService.createCartItem(requestedCartItem);
        CartItemResponse cartItemResponse = CartItemMapper.INSTANCE.toCartItemResponse(savedCartItem);
        log.info("Cart item created successfully with ID: {}", cartItemResponse.cartItemId());

        return new ResponseEntity<>(cartItemResponse, HttpStatus.CREATED);
    }

    /**
     * Retrieves a cart item by its unique ID.
     *
     * @param cartItemId The unique identifier of the cart item.
     * @return The cart item response.
     */
    @Override
    public ResponseEntity<CartItemResponse> getCartItemById(UUID cartItemId) {

        log.info("Fetching cart item with ID: {}", cartItemId);
        CartItemEntity foundCartItem = cartItemService.getCartItemById(cartItemId);
        CartItemResponse cartItemResponse = CartItemMapper.INSTANCE.toCartItemResponse(foundCartItem);
        log.info("Cart item fetched successfully: {}", cartItemResponse);

        return new ResponseEntity<>(cartItemResponse, HttpStatus.FOUND);
    }

    /**
     * Updates an existing cart item.
     *
     * @param updateCartItemRequest The request body containing updated details.
     * @return The updated cart item response.
     */
    @Override
    public ResponseEntity<CartItemResponse> updateCartItem(UpdateCartItemRequest updateCartItemRequest) {

        log.info("Updating cart item: {}", updateCartItemRequest);
        CartItemEntity requestedCartItem = CartItemMapper.INSTANCE.updateCartItemRequestToEntity(updateCartItemRequest);
        CartItemEntity updatedCartItem = cartItemService.updateCartItem(requestedCartItem);
        CartItemResponse cartItemResponse = CartItemMapper.INSTANCE.toCartItemResponse(updatedCartItem);
        log.info("Cart item updated successfully: {}", cartItemResponse);

        return new ResponseEntity<>(cartItemResponse, HttpStatus.OK);
    }

    /**
     * Retrieves all cart items with pagination.
     *
     * @param pageable Pagination details.
     * @return A paginated list of cart items grouped by cart item ID.
     */
    @Override
    public ResponseEntity<PageImpl<Map<UUID, List<CartItemResponse>>>> getAllCartItems(Pageable pageable) {

        log.info("Fetching all cart items with pageable: {}", pageable);
        Page<CartItemEntity> cartItemEntities = cartItemService.getAllCartItems(pageable);
        List<CartItemResponse> cartItemResponses = CartItemMapper.INSTANCE.toCartItemResponseList(cartItemEntities.getContent());
        Map<UUID, List<CartItemResponse>> cartItemMap = cartItemResponses.stream().collect(Collectors.groupingBy(CartItemResponse::cartItemId));
        log.info("Fetched {} cart items successfully", cartItemResponses.size());

        return new ResponseEntity<>(new PageImpl<>(List.of(cartItemMap), pageable, cartItemEntities.getTotalElements()), HttpStatus.OK);
    }

    /**
     * Deletes a cart item by its unique ID.
     *
     * @param cartItemId The unique identifier of the cart item to be deleted.
     * @return HTTP status indicating the result of the deletion.
     */
    @Override
    public ResponseEntity<HttpStatus> deleteCartItem(UUID cartItemId) {

        log.warn("Deleting cart item with ID: {}", cartItemId);
        cartItemService.deleteCartItem(cartItemId);
        log.info("Cart item with ID {} deleted successfully", cartItemId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Adds a product to a cart item.
     *
     * @param addProductToCartItemRequest The request containing cart item and product details.
     * @return The updated cart item response.
     */
    @Override
    public ResponseEntity<CartItemResponse> addProductToCartItem(AddProductToCartItemRequest addProductToCartItemRequest) {

        log.info("Adding product {} to cart item {}", addProductToCartItemRequest.productId(), addProductToCartItemRequest.cartItemId());
        CartItemEntity updatedCartItem = cartItemService.addProductToCartItem(addProductToCartItemRequest.cartItemId(), addProductToCartItemRequest.productId());
        CartItemResponse cartItemResponse = CartItemMapper.INSTANCE.toCartItemResponse(updatedCartItem);
        log.info("Product added successfully: {}", cartItemResponse);

        return new ResponseEntity<>(cartItemResponse, HttpStatus.OK);
    }

    /**
     * Removes a product from a cart item.
     *
     * @param removeProductFromCartItemRequest The request containing cart item and product details.
     * @return The updated cart item response.
     */
    @Override
    public ResponseEntity<CartItemResponse> removeProductFromCartItem(RemoveProductFromCartItemRequest removeProductFromCartItemRequest) {

        log.info("Removing product {} from cart item {}", removeProductFromCartItemRequest.productId(), removeProductFromCartItemRequest.cartItemId());
        CartItemEntity updatedCartItem = cartItemService.removeProductFromCartItem(removeProductFromCartItemRequest.cartItemId(), removeProductFromCartItemRequest.productId());
        CartItemResponse cartItemResponse = CartItemMapper.INSTANCE.toCartItemResponse(updatedCartItem);
        log.info("Product removed successfully: {}", cartItemResponse);

        return new ResponseEntity<>(cartItemResponse, HttpStatus.OK);
    }

    /**
     * Retrieves all cart items for a given cart ID with pagination.
     *
     * @param cartId   The unique identifier of the cart.
     * @param pageable Pagination details.
     * @return A paginated list of cart items grouped by cart item ID.
     */
    @Override
    public ResponseEntity<PageImpl<Map<UUID, List<CartItemResponse>>>> getCartItemsByCartId(UUID cartId, Pageable pageable) {

        log.info("Fetching cart items for cart ID: {} with pageable: {}", cartId, pageable);
        Page<CartItemEntity> cartItemEntities = cartItemService.getCartItemsByCartId(cartId, pageable);
        List<CartItemResponse> cartItemResponses = CartItemMapper.INSTANCE.toCartItemResponseList(cartItemEntities.getContent());
        Map<UUID, List<CartItemResponse>> cartItemMap = cartItemResponses.stream().collect(Collectors.groupingBy(CartItemResponse::cartItemId));
        log.info("Fetched {} cart items for cart ID: {}", cartItemResponses.size(), cartId);

        return new ResponseEntity<>(new PageImpl<>(List.of(cartItemMap), pageable, cartItemEntities.getTotalElements()), HttpStatus.FOUND);
    }
}
