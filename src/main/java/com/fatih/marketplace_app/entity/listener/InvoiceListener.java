package com.fatih.marketplace_app.entity.listener;

import com.fatih.marketplace_app.entity.InvoiceEntity;
import com.fatih.marketplace_app.enums.InvoiceStatus;
import jakarta.persistence.PrePersist;
import org.springframework.stereotype.Component;

@Component
public class InvoiceListener {

    @PrePersist
    public void prePersist(InvoiceEntity invoiceEntity) {
        invoiceEntity.setInvoiceStatus(InvoiceStatus.PAID);
    }
}
