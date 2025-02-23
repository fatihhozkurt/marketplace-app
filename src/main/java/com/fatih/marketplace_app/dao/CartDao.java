package com.fatih.marketplace_app.dao;

import com.fatih.marketplace_app.entity.CartEntity;
import com.fatih.marketplace_app.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CartDao {

    private final CartRepository cartRepository;

    public CartEntity save(CartEntity cartEntity) {
        return cartRepository.save(cartEntity);
    }

    public Optional<CartEntity> findById(UUID cartId) {
        return cartRepository.findById(cartId);
    }

    public Optional<CartEntity> findByUserId(UUID userId) {
        return cartRepository.findByUser_Id(userId);
    }

    public Page<CartEntity> findAll(Pageable pageable) {
        return cartRepository.findAll(pageable);
    }

    public void delete(CartEntity foundCart) {
        cartRepository.delete(foundCart);
    }

    public List<CartEntity> findByUpdateTimeBefore(LocalDateTime expirationTime) {
        return cartRepository.findByUpdateTimeBefore(expirationTime);
    }
}
