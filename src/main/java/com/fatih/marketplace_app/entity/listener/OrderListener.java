package com.fatih.marketplace_app.entity.listener;

import com.fatih.marketplace_app.entity.OrderEntity;
import com.fatih.marketplace_app.enums.OrderStatus;
import jakarta.persistence.PrePersist;
import org.springframework.stereotype.Component;

/**
 * Entity listener for {@link OrderEntity} to handle pre-persist operations.
 */
@Component
public class OrderListener {

    /**
     * Sets the initial order status to {@link OrderStatus#FINALIZED} before persisting a new {@link OrderEntity}.
     *
     * @param orderEntity the order entity being persisted
     */
    @PrePersist
    public void prePersist(OrderEntity orderEntity) {
        orderEntity.setOrderStatus(OrderStatus.FINALIZED);
    }
}
