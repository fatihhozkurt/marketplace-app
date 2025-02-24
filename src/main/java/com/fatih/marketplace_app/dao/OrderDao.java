package com.fatih.marketplace_app.dao;

import com.fatih.marketplace_app.entity.OrderEntity;
import com.fatih.marketplace_app.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class OrderDao {

    private final OrderRepository repository;
    private final OrderRepository orderRepository;

    public OrderEntity save(OrderEntity requestedOrder) {
        return repository.save(requestedOrder);
    }

    public Optional<OrderEntity> findById(UUID orderId) {
        return orderRepository.findById(orderId);
    }

    public Page<OrderEntity> findAll(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }

    public Page<OrderEntity> findAllByUserId(UUID userId, Pageable pageable) {
        return orderRepository.findAllByUser_Id(userId, pageable);
    }

    public void delete(OrderEntity order) {
        orderRepository.delete(order);
    }

    public Optional<OrderEntity> findByOrderNumber(String orderNumber) {
        return orderRepository.findByOrderNumber(orderNumber);
    }
}