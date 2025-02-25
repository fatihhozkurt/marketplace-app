package com.fatih.marketplace_app.controller;

import com.fatih.marketplace_app.controller.api.InvoiceApi;
import com.fatih.marketplace_app.dto.request.invoice.CreateInvoiceRequest;
import com.fatih.marketplace_app.dto.response.invoice.InvoiceResponse;
import com.fatih.marketplace_app.entity.InvoiceEntity;
import com.fatih.marketplace_app.manager.service.InvoiceService;
import com.fatih.marketplace_app.mapper.InvoiceMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Controller class for managing invoices.
 * Implements the {@link InvoiceApi} interface.
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class InvoiceController implements InvoiceApi {

    private final InvoiceService invoiceService;

    /**
     * Exports an invoice for a given order.
     *
     * @param createInvoiceRequest The request containing the order ID.
     * @return ResponseEntity with HTTP status CREATED.
     */
    @Override
    public ResponseEntity<HttpStatus> exportInvoice(CreateInvoiceRequest createInvoiceRequest) {

        log.info("Exporting invoice for order ID: {}", createInvoiceRequest.orderId());
        invoiceService.exportInvoice(createInvoiceRequest.orderId());
        log.info("Invoice exported successfully.");

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * Deletes an invoice by its ID.
     *
     * @param invoiceId The ID of the invoice to be deleted.
     * @return ResponseEntity with HTTP status NO_CONTENT.
     */
    @Override
    public ResponseEntity<HttpStatus> deleteInvoice(UUID invoiceId) {

        log.info("Deleting invoice with ID: {}", invoiceId);
        invoiceService.deleteInvoice(invoiceId);
        log.info("Invoice deleted successfully.");

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Retrieves an invoice by its ID.
     *
     * @param invoiceId The ID of the invoice.
     * @return ResponseEntity containing the InvoiceResponse.
     */
    @Override
    public ResponseEntity<InvoiceResponse> getInvoiceById(UUID invoiceId) {

        log.info("Fetching invoice with ID: {}", invoiceId);
        InvoiceEntity foundInvoice = invoiceService.getInvoiceById(invoiceId);
        InvoiceResponse invoiceResponse = InvoiceMapper.INSTANCE.toInvoiceResponse(foundInvoice);
        log.info("Invoice retrieved successfully.");

        return new ResponseEntity<>(invoiceResponse, HttpStatus.FOUND);
    }

    /**
     * Retrieves all invoices in a paginated format.
     *
     * @param pageable The pagination information.
     * @return ResponseEntity containing a paginated list of invoices grouped by ID.
     */
    @Override
    public ResponseEntity<PageImpl<Map<UUID, List<InvoiceResponse>>>> getAllInvoices(Pageable pageable) {

        log.info("Fetching all invoices.");
        Page<InvoiceEntity> invoiceEntities = invoiceService.getAllInvoices(pageable);
        List<InvoiceResponse> invoiceResponses = InvoiceMapper.INSTANCE.toInvoiceResponseList(invoiceEntities.getContent());
        Map<UUID, List<InvoiceResponse>> invoiceMap = invoiceResponses.stream().collect(Collectors.groupingBy(InvoiceResponse::invoiceId));
        log.info("Invoices retrieved successfully.");

        return new ResponseEntity<>(new PageImpl<>(List.of(invoiceMap), pageable, invoiceEntities.getTotalElements()), HttpStatus.OK);
    }

    /**
     * Retrieves an invoice by its associated order ID.
     *
     * @param orderId The ID of the order.
     * @return ResponseEntity containing the InvoiceResponse.
     */
    @Override
    public ResponseEntity<InvoiceResponse> getInvoiceByOrderId(UUID orderId) {

        log.info("Fetching invoice for order ID: {}", orderId);
        InvoiceEntity foundInvoice = invoiceService.getInvoiceByOrderId(orderId);
        InvoiceResponse invoiceResponse = InvoiceMapper.INSTANCE.toInvoiceResponse(foundInvoice);
        log.info("Invoice retrieved successfully.");

        return new ResponseEntity<>(invoiceResponse, HttpStatus.FOUND);
    }

    /**
     * Retrieves an invoice by its invoice number.
     *
     * @param invoiceNumber The invoice number.
     * @return ResponseEntity containing the InvoiceResponse.
     */
    @Override
    public ResponseEntity<InvoiceResponse> getInvoiceByInvoiceNumber(String invoiceNumber) {

        log.info("Fetching invoice with invoice number: {}", invoiceNumber);
        InvoiceEntity foundInvoice = invoiceService.getInvoiceByInvoiceNumber(invoiceNumber);
        InvoiceResponse invoiceResponse = InvoiceMapper.INSTANCE.toInvoiceResponse(foundInvoice);
        log.info("Invoice retrieved successfully.");

        return new ResponseEntity<>(invoiceResponse, HttpStatus.FOUND);
    }
}