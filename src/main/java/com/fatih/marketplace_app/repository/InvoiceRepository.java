package com.fatih.marketplace_app.repository;

import com.fatih.marketplace_app.entity.InvoiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for managing {@link InvoiceEntity} persistence operations.
 */
@Repository
public interface InvoiceRepository extends JpaRepository<InvoiceEntity, UUID> {

    /**
     * Finds an invoice associated with a specific order.
     *
     * @param orderId the unique identifier of the order.
     * @return an {@link Optional} containing the {@link InvoiceEntity} if found, otherwise empty.
     */
    Optional<InvoiceEntity> findByOrder_Id(UUID orderId);

    /**
     * Retrieves an invoice by its unique invoice number.
     *
     * @param invoiceNumber the unique invoice number.
     * @return an {@link Optional} containing the {@link InvoiceEntity} if found, otherwise empty.
     */
    Optional<InvoiceEntity> findByInvoiceNumber(String invoiceNumber);
}
