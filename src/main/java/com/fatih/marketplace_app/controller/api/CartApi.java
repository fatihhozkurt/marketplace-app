package com.fatih.marketplace_app.controller.api;

import com.fatih.marketplace_app.dto.request.cart.CreateCartRequest;
import com.fatih.marketplace_app.dto.request.cart.UpdateCartRequest;
import com.fatih.marketplace_app.dto.response.cart.CartResponse;
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
 * API interface for managing shopping carts.
 */
@RequestMapping(CART)
public interface CartApi {

    /**
     * Creates a new shopping cart.
     *
     * @param createCartRequest Request body containing cart details.
     * @return The created cart response.
     */
    @PostMapping
    ResponseEntity<CartResponse> createCart(@RequestBody @Valid CreateCartRequest createCartRequest);

    /**
     * Retrieves a cart by its unique ID.
     *
     * @param cartId The unique identifier of the cart.
     * @return The cart response.
     */
    @GetMapping(ID)
    ResponseEntity<CartResponse> getCartById(@RequestParam("cartId") @NotNull UUID cartId);

    /**
     * Retrieves a cart associated with a specific user.
     *
     * @param userId The unique identifier of the user.
     * @return The user's cart response.
     */
    @GetMapping(USER + ID)
    ResponseEntity<CartResponse> getCartByUserId(@RequestParam("userId") @NotNull UUID userId);

    /**
     * Retrieves all shopping carts with pagination.
     *
     * @param pageable Pagination details.
     * @return A paginated list of cart responses grouped by user ID.
     */
    @GetMapping(ALL)
    ResponseEntity<PageImpl<Map<UUID, List<CartResponse>>>> getAllCarts(Pageable pageable);

    /**
     * Deletes a cart by its unique ID.
     *
     * @param cartId The unique identifier of the cart.
     * @return HTTP status indicating the result of the operation.
     */
    @DeleteMapping
    ResponseEntity<HttpStatus> deleteCart(@RequestParam("cartId") @NotNull UUID cartId);

    /**
     * Clears all items from a cart.
     *
     * @param cartId The unique identifier of the cart to be cleared.
     * @return HTTP status indicating the result of the operation.
     */
    @DeleteMapping(CLEAR)
    ResponseEntity<HttpStatus> clearCart(@RequestParam("cartId") @NotNull UUID cartId);

    /**
     * Updates an existing cart.
     *
     * @param updateCartRequest Request body containing updated cart details.
     * @return The updated cart response.
     */
    @PutMapping
    ResponseEntity<CartResponse> updateCart(@RequestBody @Valid UpdateCartRequest updateCartRequest);
}
