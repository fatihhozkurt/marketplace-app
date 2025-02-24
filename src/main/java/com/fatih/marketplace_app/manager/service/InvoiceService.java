package com.fatih.marketplace_app.manager.service;

import com.fatih.marketplace_app.entity.InvoiceEntity;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface InvoiceService {
    void exportInvoice(@NotNull UUID orderId);

    void deleteInvoice(UUID invoiceId);

    InvoiceEntity getInvoiceByOrderId(UUID orderId);

    Page<InvoiceEntity> getAllInvoices(Pageable pageable);

    InvoiceEntity getInvoiceById(UUID invoiceId);

    InvoiceEntity getInvoiceByInvoiceNumber(String invoiceNumber);
}
