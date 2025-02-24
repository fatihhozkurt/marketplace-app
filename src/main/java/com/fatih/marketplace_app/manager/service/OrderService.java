package com.fatih.marketplace_app.manager.service;

import com.fatih.marketplace_app.entity.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface OrderService {

    OrderEntity createOrder(OrderEntity requestedOrder);

    OrderEntity getOrderById(UUID orderId);

    Page<OrderEntity> getAllOrders(Pageable pageable);

    void cancelOrder(UUID orderId);

    Page<OrderEntity> getOrdersByUserId(UUID userId, Pageable pageable);

    String generateRandomOrderNumber();

    OrderEntity getOrderByOrderNumber(String orderNumber);
}
