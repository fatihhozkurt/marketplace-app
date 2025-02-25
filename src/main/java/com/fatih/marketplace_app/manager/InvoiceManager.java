package com.fatih.marketplace_app.manager;

import com.fatih.marketplace_app.dao.InvoiceDao;
import com.fatih.marketplace_app.entity.InvoiceEntity;
import com.fatih.marketplace_app.entity.OrderEntity;
import com.fatih.marketplace_app.exception.BusinessException;
import com.fatih.marketplace_app.exception.ResourceNotFoundException;
import com.fatih.marketplace_app.manager.service.InvoiceService;
import com.fatih.marketplace_app.manager.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;
import java.util.UUID;

/**
 * Manager class responsible for invoice operations.
 * Provides functionality for creating, retrieving, exporting and deleting invoices.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class InvoiceManager implements InvoiceService {

    private final InvoiceDao invoiceDao;
    private final OrderService orderService;
    private final InvoiceExportManager invoiceExportManager;
    private final MessageSource messageSource;

    /**
     * Creates and exports an invoice for a given order.
     * Generates a new invoice with a random invoice number, saves it to the database,
     * and then processes the export to PDF.
     *
     * @param orderId The ID of the order for which to create an invoice
     * @throws ResourceNotFoundException if no order exists with the given ID
     * @throws BusinessException if the export process fails
     */
    @Transactional
    @Override
    public void exportInvoice(UUID orderId) {
        log.info("Creating and exporting invoice for order with ID: {}", orderId);

        OrderEntity foundOrder = orderService.getOrderById(orderId);
        log.debug("Order found with ID: {}", orderId);

        InvoiceEntity invoiceEntity = InvoiceEntity.builder()
                .order(foundOrder)
                .invoiceNumber(orderService.generateRandomOrderNumber())
                .build();

        invoiceDao.save(invoiceEntity);
        log.debug("Invoice saved to database");

        processExport(invoiceEntity);
        log.info("Invoice successfully created and exported for order with ID: {}", orderId);
    }

    /**
     * Deletes an invoice by its ID.
     *
     * @param invoiceId The ID of the invoice to delete
     * @throws ResourceNotFoundException if no invoice exists with the given ID
     */
    @Transactional
    @Override
    public void deleteInvoice(UUID invoiceId) {
        log.info("Deleting invoice with ID: {}", invoiceId);

        InvoiceEntity foundInvoice = getInvoiceById(invoiceId);
        invoiceDao.delete(foundInvoice);

        log.info("Invoice with ID: {} deleted successfully", invoiceId);
    }

    /**
     * Retrieves an invoice by order ID.
     *
     * @param orderId The ID of the order associated with the invoice
     * @return The found invoice entity
     * @throws ResourceNotFoundException if no invoice exists for the given order ID
     */
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public InvoiceEntity getInvoiceByOrderId(UUID orderId) {
        log.info("Retrieving invoice for order with ID: {}", orderId);

        return invoiceDao.findByOrderId(orderId)
                .orElseThrow(() -> new ResourceNotFoundException(messageSource
                        .getMessage("backend.exceptions.INV002",
                                new Object[]{orderId},
                                Locale.getDefault())));
    }

    /**
     * Retrieves all invoices with pagination.
     *
     * @param pageable Pagination information
     * @return A page of invoice entities
     */
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public Page<InvoiceEntity> getAllInvoices(Pageable pageable) {
        log.info("Retrieving all invoices with pagination. Page: {}, Size: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        return invoiceDao.findAll(pageable);
    }

    /**
     * Retrieves an invoice by its ID.
     *
     * @param invoiceId The ID of the invoice to retrieve
     * @return The found invoice entity
     * @throws ResourceNotFoundException if no invoice exists with the given ID
     */
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public InvoiceEntity getInvoiceById(UUID invoiceId) {
        log.info("Retrieving invoice with ID: {}", invoiceId);

        return invoiceDao.findById(invoiceId)
                .orElseThrow(() -> new ResourceNotFoundException(messageSource
                        .getMessage("backend.exceptions.INV001",
                                new Object[]{invoiceId},
                                Locale.getDefault())));
    }

    /**
     * Retrieves an invoice by its invoice number.
     *
     * @param invoiceNumber The invoice number to search for
     * @return The found invoice entity
     * @throws ResourceNotFoundException if no invoice exists with the given invoice number
     */
    @Override
    public InvoiceEntity getInvoiceByInvoiceNumber(String invoiceNumber) {
        log.info("Retrieving invoice with invoice number: {}", invoiceNumber);

        return invoiceDao.findByInvoiceNumber(invoiceNumber)
                .orElseThrow(() -> new ResourceNotFoundException(messageSource
                        .getMessage("backend.exceptions.INV004",
                                new Object[]{invoiceNumber},
                                Locale.getDefault())));
    }

    /**
     * Processes the export of an invoice to PDF format.
     *
     * @param invoiceEntity The invoice entity to export
     * @throws BusinessException if the export process fails
     */
    private void processExport(InvoiceEntity invoiceEntity) {
        log.info("Processing export for invoice with ID: {}", invoiceEntity.getId());

        try {
            invoiceExportManager.exportInvoiceToPdf(invoiceEntity);
            log.info("Export processed successfully for invoice with ID: {}", invoiceEntity.getId());
        } catch (Exception ex) {
            log.error("Failed to export invoice with ID: {}. Error: {}",
                    invoiceEntity.getId(), ex.getMessage(), ex);
            throw new BusinessException(messageSource
                    .getMessage("backend.exceptions.INV003",
                            new Object[]{},
                            Locale.getDefault()));
        }
    }
}
