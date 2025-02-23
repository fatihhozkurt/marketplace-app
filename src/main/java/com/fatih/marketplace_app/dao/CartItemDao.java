package com.fatih.marketplace_app.dao;

import com.fatih.marketplace_app.entity.CartItemEntity;
import com.fatih.marketplace_app.repository.CartItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CartItemDao {

    private final CartItemRepository cartItemRepository;

    public CartItemEntity save(CartItemEntity requestedCartItem) {
        return cartItemRepository.save(requestedCartItem);
    }

    public Optional<CartItemEntity> findById(UUID cartItemId) {
        return cartItemRepository.findById(cartItemId);
    }

    public Page<CartItemEntity> findAll(Pageable pageable) {
        return cartItemRepository.findAll(pageable);
    }

    public void delete(CartItemEntity foundCartItem) {
        cartItemRepository.delete(foundCartItem);
    }

    public Page<CartItemEntity> findAllByCartId(UUID cartId, Pageable pageable) {
        return cartItemRepository.findAllByCart_Id(cartId, pageable);
    }
}