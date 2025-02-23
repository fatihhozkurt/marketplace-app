package com.fatih.marketplace_app.entity.listener;

import com.fatih.marketplace_app.entity.OrderEntity;
import com.fatih.marketplace_app.enums.OrderStatus;
import jakarta.persistence.PrePersist;
import org.springframework.stereotype.Component;

@Component
public class OrderListener {

    @PrePersist
    public void prePersist(OrderEntity orderEntity) {
        orderEntity.setOrderStatus(OrderStatus.CONFIRMED);
    }
}
