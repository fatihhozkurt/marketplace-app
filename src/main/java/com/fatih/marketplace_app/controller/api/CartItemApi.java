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

@RequestMapping(CARTITEM)
public interface CartItemApi {

    @PostMapping
    ResponseEntity<CartItemResponse> creatCartItem(@RequestBody @Valid CreateCartItemRequest createCartItemRequest);

    @GetMapping(ID)
    ResponseEntity<CartItemResponse> getCartItemById(@RequestParam("cartItemId") @NotNull UUID cartItemId);

    @PutMapping
    ResponseEntity<CartItemResponse> updateCartItem(@RequestBody @Valid UpdateCartItemRequest updateCartItemRequest);

    @GetMapping(ALL)
    ResponseEntity<PageImpl<Map<UUID, List<CartItemResponse>>>> getAllCartItems(Pageable pageable);

    @DeleteMapping
    ResponseEntity<HttpStatus> deleteCartItem(@RequestParam("cartItemId") @NotNull UUID cartItemId);

    @PutMapping(ADD)
    ResponseEntity<CartItemResponse> addProductToCartItem(@RequestBody @Valid AddProductToCartItemRequest addProductToCartItemRequest);

    @PutMapping(REMOVE)
    ResponseEntity<CartItemResponse> removeProductFromCartItem(@RequestBody @Valid RemoveProductFromCartItemRequest removeProductFromCartItemRequest);

    @GetMapping(CART + ID)
    ResponseEntity<PageImpl<Map<UUID, List<CartItemResponse>>>> getCartItemsByCartId(@RequestParam("cartId") @NotNull UUID cartId, Pageable pageable);
}
