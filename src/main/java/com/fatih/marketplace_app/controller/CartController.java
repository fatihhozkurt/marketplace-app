package com.fatih.marketplace_app.controller;

import com.fatih.marketplace_app.controller.api.CartApi;
import com.fatih.marketplace_app.dto.request.cart.CreateCartRequest;
import com.fatih.marketplace_app.dto.request.cart.UpdateCartRequest;
import com.fatih.marketplace_app.dto.response.cart.CartResponse;
import com.fatih.marketplace_app.entity.CartEntity;
import com.fatih.marketplace_app.manager.service.CartService;
import com.fatih.marketplace_app.mapper.CartMapper;
import lombok.RequiredArgsConstructor;
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

@RestController
@RequiredArgsConstructor
public class CartController implements CartApi {

    private final CartService cartService;

    @Override
    public ResponseEntity<CartResponse> createCart(CreateCartRequest createCartRequest) {

        CartEntity cartEntity = CartMapper.INSTANCE.createCartRequestToEntity(createCartRequest);
        CartEntity createdCart = cartService.createCart(cartEntity);
        CartResponse cartResponse = CartMapper.INSTANCE.toCartResponse(createdCart);

        return new ResponseEntity<>(cartResponse, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<CartResponse> getCartById(UUID cartId) {

        CartEntity foundCart = cartService.getCartById(cartId);
        CartResponse cartResponse = CartMapper.INSTANCE.toCartResponse(foundCart);

        return new ResponseEntity<>(cartResponse, HttpStatus.FOUND);
    }

    @Override
    public ResponseEntity<CartResponse> getCartByUserId(UUID userId) {

        CartEntity foundCart = cartService.getCartByUserId(userId);
        CartResponse cartResponse = CartMapper.INSTANCE.toCartResponse(foundCart);

        return new ResponseEntity<>(cartResponse, HttpStatus.FOUND);
    }

    @Override
    public ResponseEntity<PageImpl<Map<UUID, List<CartResponse>>>> getAllCarts(Pageable pageable) {

        Page<CartEntity> cartEntities = cartService.getAllCarts(pageable);
        List<CartResponse> cartResponses = CartMapper.INSTANCE.toCartResponseList(cartEntities.getContent());
        Map<UUID, List<CartResponse>> cartMap = cartResponses.stream().collect(Collectors.groupingBy(CartResponse::cartId));

        return new ResponseEntity<>(new PageImpl<>(List.of(cartMap), pageable, cartEntities.getTotalElements()), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<HttpStatus> deleteCart(UUID cartId) {

        cartService.deleteCart(cartId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<HttpStatus> clearCart(UUID cartId) {
        cartService.clearCart(cartId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<CartResponse> updateCart(UpdateCartRequest updateCartRequest) {

        CartEntity requestedCart = CartMapper.INSTANCE.updateCartRequestToEntity(updateCartRequest);
        CartEntity updatedCart = cartService.updateCart(requestedCart);
        CartResponse cartResponse = CartMapper.INSTANCE.toCartResponse(updatedCart);

        return new ResponseEntity<>(cartResponse, HttpStatus.OK);
    }
}