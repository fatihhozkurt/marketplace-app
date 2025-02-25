package com.fatih.marketplace_app.dao;

import com.fatih.marketplace_app.entity.InvoiceEntity;
import com.fatih.marketplace_app.repository.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

/**
 * Data Access Object (DAO) for managing {@link InvoiceEntity} operations.
 */
@Component
@RequiredArgsConstructor
public class InvoiceDao {

    private final InvoiceRepository invoiceRepository;

    /**
     * Saves the given invoice entity to the database.
     *
     * @param invoiceEntity the invoice entity to save
     */
    public void save(InvoiceEntity invoiceEntity) {
        invoiceRepository.save(invoiceEntity);
    }

    /**
     * Deletes the given invoice entity from the database.
     *
     * @param foundInvoice the invoice entity to delete
     */
    public void delete(InvoiceEntity foundInvoice) {
        invoiceRepository.delete(foundInvoice);
    }

    /**
     * Finds an invoice by the associated order ID.
     *
     * @param orderId the UUID of the order
     * @return an {@link Optional} containing the found invoice, or empty if not found
     */
    public Optional<InvoiceEntity> findByOrderId(UUID orderId) {
        return invoiceRepository.findByOrder_Id(orderId);
    }

    /**
     * Retrieves a paginated list of all invoices.
     *
     * @param pageable pagination information
     * @return a {@link Page} of {@link InvoiceEntity} objects
     */
    public Page<InvoiceEntity> findAll(Pageable pageable) {
        return invoiceRepository.findAll(pageable);
    }

    /**
     * Finds an invoice by its unique identifier.
     *
     * @param invoiceId the UUID of the invoice
     * @return an {@link Optional} containing the found invoice, or empty if not found
     */
    public Optional<InvoiceEntity> findById(UUID invoiceId) {
        return invoiceRepository.findById(invoiceId);
    }

    /**
     * Finds an invoice by its invoice number.
     *
     * @param invoiceNumber the invoice number
     * @return an {@link Optional} containing the found invoice, or empty if not found
     */
    public Optional<InvoiceEntity> findByInvoiceNumber(String invoiceNumber) {
        return invoiceRepository.findByInvoiceNumber(invoiceNumber);
    }
}