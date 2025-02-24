package com.fatih.marketplace_app.repository;

import com.fatih.marketplace_app.entity.OrderEntity;
import com.fatih.marketplace_app.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, UUID> {
    List<OrderEntity> user(UserEntity user);

    Page<OrderEntity> findAllByUser_Id(UUID userId, Pageable pageable);

    Optional<OrderEntity> findByOrderNumber(String orderNumber);
}
