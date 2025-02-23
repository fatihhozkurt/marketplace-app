package com.fatih.marketplace_app.repository;

import com.fatih.marketplace_app.entity.CartItemEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CartItemRepository extends JpaRepository<CartItemEntity, UUID> {
    Page<CartItemEntity> findAllByCart_Id(UUID cartId, Pageable pageable);
}
