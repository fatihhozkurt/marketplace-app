package com.fatih.marketplace_app.repository;

import com.fatih.marketplace_app.entity.InvoiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface InvoiceRepository extends JpaRepository<InvoiceEntity, UUID> {
    Optional<InvoiceEntity> findByOrder_Id(UUID orderId);

    Optional<InvoiceEntity> findByInvoiceNumber(String invoiceNumber);
}
