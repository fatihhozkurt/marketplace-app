package com.fatih.marketplace_app.controller.api;

import com.fatih.marketplace_app.dto.request.cartItem.AddProductToCartItemRequest;
import com.fatih.marketplace_app.dto.request.cartItem.CreateCartItemRequest;
import com.fatih.marketplace_app.dto.request.cartItem.RemoveProductFromCartItemRequest;
import com.fatih.marketplace_app.dto.request.cartItem.UpdateCartItemRequest;
import com.fatih.marketplace_app.dto.response.cartItem.CartItemResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.fatih.marketplace_app.constant.UrlConst.*;

/**
 * API interface for managing cart items.
 */
@RequestMapping(CARTITEM)
public interface CartItemApi {

    /**
     * Creates a new cart item.
     *
     * @param createCartItemRequest Request body containing cart item details.
     * @return The created cart item response.
     */
    @PostMapping
    ResponseEntity<CartItemResponse> creatCartItem(@RequestBody @Valid CreateCartItemRequest createCartItemRequest);

    /**
     * Retrieves a cart item by its unique ID.
     *
     * @param cartItemId The unique identifier of the cart item.
     * @return The cart item response.
     */
    @GetMapping(ID)
    ResponseEntity<CartItemResponse> getCartItemById(@RequestParam("cartItemId") @NotNull UUID cartItemId);

    /**
     * Updates an existing cart item.
     *
     * @param updateCartItemRequest Request body containing updated cart item details.
     * @return The updated cart item response.
     */
    @PutMapping
    ResponseEntity<CartItemResponse> updateCartItem(@RequestBody @Valid UpdateCartItemRequest updateCartItemRequest);

    /**
     * Retrieves all cart items with pagination.
     *
     * @param pageable Pagination details.
     * @return A paginated list of cart item responses grouped by cart ID.
     */
    @GetMapping(ALL)
    ResponseEntity<PageImpl<Map<UUID, List<CartItemResponse>>>> getAllCartItems(Pageable pageable);

    /**
     * Deletes a cart item by its unique ID.
     *
     * @param cartItemId The unique identifier of the cart item.
     * @return HTTP status indicating the result of the operation.
     */
    @DeleteMapping
    ResponseEntity<HttpStatus> deleteCartItem(@RequestParam("cartItemId") @NotNull UUID cartItemId);

    /**
     * Adds a product to an existing cart item.
     *
     * @param addProductToCartItemRequest Request body containing product and cart item details.
     * @return The updated cart item response after adding the product.
     */
    @PutMapping(ADD)
    ResponseEntity<CartItemResponse> addProductToCartItem(@RequestBody @Valid AddProductToCartItemRequest addProductToCartItemRequest);

    /**
     * Removes a product from an existing cart item.
     *
     * @param removeProductFromCartItemRequest Request body containing product and cart item details.
     * @return The updated cart item response after removing the product.
     */
    @PutMapping(REMOVE)
    ResponseEntity<CartItemResponse> removeProductFromCartItem(@RequestBody @Valid RemoveProductFromCartItemRequest removeProductFromCartItemRequest);

    /**
     * Retrieves all cart items associated with a specific cart ID.
     *
     * @param cartId The unique identifier of the cart.
     * @param pageable Pagination details.
     * @return A paginated list of cart item responses for the given cart ID.
     */
    @GetMapping(CART + ID)
    ResponseEntity<PageImpl<Map<UUID, List<CartItemResponse>>>> getCartItemsByCartId(@RequestParam("cartId") @NotNull UUID cartId, Pageable pageable);
}
