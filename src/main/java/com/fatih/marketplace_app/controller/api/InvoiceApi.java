package com.fatih.marketplace_app.controller.api;

import com.fatih.marketplace_app.dto.request.invoice.CreateInvoiceRequest;
import com.fatih.marketplace_app.dto.response.invoice.InvoiceResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.fatih.marketplace_app.constant.UrlConst.*;

/**
 * API interface for managing invoices.
 */
@RequestMapping(INVOICE)
public interface InvoiceApi {

    /**
     * Exports an invoice based on the provided details.
     *
     * @param createInvoiceRequest Request body containing invoice details.
     * @return HTTP status indicating the result of the operation.
     */
    @PostMapping
    ResponseEntity<HttpStatus> exportInvoice(@RequestBody @Valid CreateInvoiceRequest createInvoiceRequest);

    /**
     * Deletes an invoice by its unique ID.
     *
     * @param invoiceId The unique identifier of the invoice.
     * @return HTTP status indicating the result of the operation.
     */
    @DeleteMapping
    ResponseEntity<HttpStatus> deleteInvoice(@RequestParam("invoiceId") @NotNull UUID invoiceId);

    /**
     * Retrieves an invoice by its unique ID.
     *
     * @param invoiceId The unique identifier of the invoice.
     * @return The invoice response.
     */
    @GetMapping(ID)
    ResponseEntity<InvoiceResponse> getInvoiceById(@RequestParam("invoiceId") @NotNull UUID invoiceId);

    /**
     * Retrieves all invoices with pagination.
     *
     * @param pageable Pagination details.
     * @return A paginated list of invoice responses grouped by invoice ID.
     */
    @GetMapping(ALL)
    ResponseEntity<PageImpl<Map<UUID, List<InvoiceResponse>>>> getAllInvoices(Pageable pageable);

    /**
     * Retrieves an invoice by the associated order ID.
     *
     * @param orderId The unique identifier of the order.
     * @return The invoice response linked to the given order ID.
     */
    @GetMapping(ORDER + ID)
    ResponseEntity<InvoiceResponse> getInvoiceByOrderId(@RequestParam("orderId") @NotNull UUID orderId);

    /**
     * Retrieves an invoice by its invoice number.
     *
     * @param invoiceNumber The 12-character invoice number.
     * @return The invoice response associated with the given invoice number.
     */
    @GetMapping(NUMBER)
    ResponseEntity<InvoiceResponse> getInvoiceByInvoiceNumber(@RequestParam("invoiceNumber") @NotNull @Size(min = 12, max = 12) String invoiceNumber);
}