package com.fatih.marketplace_app.dao;

import com.fatih.marketplace_app.entity.InvoiceEntity;
import com.fatih.marketplace_app.repository.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class InvoiceDao {

    private final InvoiceRepository invoiceRepository;

    public void save(InvoiceEntity invoiceEntity) {
        invoiceRepository.save(invoiceEntity);
    }

    public void delete(InvoiceEntity foundInvoice) {
        invoiceRepository.delete(foundInvoice);
    }

    public Optional<InvoiceEntity> findByOrderId(UUID orderId) {
        return invoiceRepository.findByOrder_Id(orderId);
    }

    public Page<InvoiceEntity> findAll(Pageable pageable) {
        return invoiceRepository.findAll(pageable);
    }

    public Optional<InvoiceEntity> findById(UUID invoiceId) {
        return invoiceRepository.findById(invoiceId);
    }

    public Optional<InvoiceEntity> findByInvoiceNumber(String invoiceNumber) {
        return invoiceRepository.findByInvoiceNumber(invoiceNumber);
    }
}