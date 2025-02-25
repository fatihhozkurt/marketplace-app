package com.fatih.marketplace_app.entity.listener;

import com.fatih.marketplace_app.entity.CartEntity;
import jakarta.persistence.PrePersist;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;


/**
 * Entity listener for {@link CartEntity} to handle pre-persist operations.
 */
@Component
public class CartListener {

    /**
     * Sets the initial cart price to zero before persisting a new {@link CartEntity}.
     *
     * @param cartEntity the cart entity being persisted
     */

    @PrePersist
    public void prePersist(CartEntity cartEntity) {
        cartEntity.setCartPrice(BigDecimal.ZERO);
    }
}
