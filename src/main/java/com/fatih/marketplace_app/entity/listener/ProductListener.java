package com.fatih.marketplace_app.entity.listener;

import com.fatih.marketplace_app.entity.ProductEntity;
import jakarta.persistence.PreUpdate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductListener {

    @PreUpdate
    public void preUpdate(ProductEntity productEntity) {
        if (productEntity.getRecordStatus()) {
            productEntity.setStockQuantity(0L);
        }
    }
}
