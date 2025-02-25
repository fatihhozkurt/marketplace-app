package com.fatih.marketplace_app.manager.service;

import com.fatih.marketplace_app.entity.InvoiceEntity;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

/**
 * Service interface for managing invoice operations.
 */
public interface InvoiceService {

    /**
     * Exports an invoice for the specified order.
     *
     * @param orderId The unique identifier of the order.
     */
    void exportInvoice(@NotNull UUID orderId);

    /**
     * Deletes an invoice by its unique ID.
     *
     * @param invoiceId The unique identifier of the invoice to be deleted.
     */
    void deleteInvoice(UUID invoiceId);

    /**
     * Retrieves an invoice associated with a specific order ID.
     *
     * @param orderId The unique identifier of the order.
     * @return The invoice entity associated with the given order ID.
     */
    InvoiceEntity getInvoiceByOrderId(UUID orderId);

    /**
     * Retrieves all invoices with pagination support.
     *
     * @param pageable The pagination information.
     * @return A paginated list of invoice entities.
     */
    Page<InvoiceEntity> getAllInvoices(Pageable pageable);

    /**
     * Retrieves an invoice by its unique ID.
     *
     * @param invoiceId The unique identifier of the invoice.
     * @return The invoice entity if found.
     */
    InvoiceEntity getInvoiceById(UUID invoiceId);

    /**
     * Retrieves an invoice by its invoice number.
     *
     * @param invoiceNumber The unique invoice number.
     * @return The invoice entity associated with the given invoice number.
     */
    InvoiceEntity getInvoiceByInvoiceNumber(String invoiceNumber);
}