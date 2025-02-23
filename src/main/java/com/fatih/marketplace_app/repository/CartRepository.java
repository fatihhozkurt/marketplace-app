package com.fatih.marketplace_app.repository;

import com.fatih.marketplace_app.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CartRepository extends JpaRepository<CartEntity, UUID> {
    Optional<CartEntity> findByUser_Id(UUID userId);

    List<CartEntity> findByUpdateTimeBefore(LocalDateTime expirationTime);
}
