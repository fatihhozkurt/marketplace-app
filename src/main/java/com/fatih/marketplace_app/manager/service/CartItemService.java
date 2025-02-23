package com.fatih.marketplace_app.manager.service;

import com.fatih.marketplace_app.entity.CartItemEntity;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface CartItemService {

    CartItemEntity createCartItem(CartItemEntity requestedCartItem);

    CartItemEntity getCartItemById(UUID cartItemId);

    CartItemEntity updateCartItem(CartItemEntity requestedCartItem);

    Page<CartItemEntity> getAllCartItems(Pageable pageable);

    void deleteCartItem(UUID cartItemId);

    CartItemEntity removeProductFromCartItem(@NotNull UUID cartItemId, @NotNull UUID productId);

    CartItemEntity addProductToCartItem(@NotNull UUID cartItemId, @NotNull UUID productId);

    Page<CartItemEntity> getCartItemsByCartId(UUID cartId, Pageable pageable);
}
