package com.fatih.marketplace_app.entity.listener;

import com.fatih.marketplace_app.entity.CartEntity;
import jakarta.persistence.PrePersist;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class CartListener {

    @PrePersist
    public void prePersist(CartEntity cartEntity) {
        cartEntity.setCartPrice(BigDecimal.ZERO);
    }
}
