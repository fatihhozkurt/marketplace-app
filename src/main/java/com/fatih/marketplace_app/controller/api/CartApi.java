package com.fatih.marketplace_app.controller.api;

import com.fatih.marketplace_app.dto.request.cart.CreateCartRequest;
import com.fatih.marketplace_app.dto.request.cart.UpdateCartRequest;
import com.fatih.marketplace_app.dto.response.cart.CartResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.apache.coyote.Response;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.fatih.marketplace_app.constant.UrlConst.*;

@RequestMapping(CART)
public interface CartApi {

    @PostMapping
    ResponseEntity<CartResponse> createCart(@RequestBody @Valid CreateCartRequest createCartRequest);

    @GetMapping(ID)
    ResponseEntity<CartResponse> getCartById(@RequestParam("cartId") @NotNull UUID cartId);

    @GetMapping(USER + ID)
    ResponseEntity<CartResponse> getCartByUserId(@RequestParam("userId") @NotNull UUID userId);

    @GetMapping(ALL)
    ResponseEntity<PageImpl<Map<UUID, List<CartResponse>>>> getAllCarts(Pageable pageable);

    @DeleteMapping
    ResponseEntity<HttpStatus> deleteCart(@RequestParam("cartId") @NotNull UUID cartId);

    @DeleteMapping(CLEAR)
    ResponseEntity<HttpStatus> clearCart(@RequestParam("cartId") @NotNull UUID cartId);

    @PutMapping
    ResponseEntity<CartResponse> updateCart(@RequestBody @Valid UpdateCartRequest updateCartRequest);
}
