package com.fatih.marketplace_app.manager.service;

import com.fatih.marketplace_app.entity.CartEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface CartService {

    CartEntity createCart(CartEntity cartEntity);

    CartEntity getCartById(UUID cartId);

    CartEntity getCartByUserId(UUID userId);

    Page<CartEntity> getAllCarts(Pageable pageable);

    void deleteCart(UUID cartId);

    void clearCart(UUID cartId);

    CartEntity updateCart(CartEntity requestedCart);
}
