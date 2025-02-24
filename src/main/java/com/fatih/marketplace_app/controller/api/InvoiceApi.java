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

@RequestMapping(INVOICE)
public interface InvoiceApi {

    @PostMapping
    ResponseEntity<HttpStatus> exportInvoice(@RequestBody @Valid CreateInvoiceRequest createInvoiceRequest);

    @DeleteMapping
    ResponseEntity<HttpStatus> deleteInvoice(@RequestParam("invoiceId") @NotNull UUID invoiceId);

    @GetMapping(ID)
    ResponseEntity<InvoiceResponse> getInvoiceById(@RequestParam("invoiceId") @NotNull UUID invoiceId);

    @GetMapping(ALL)
    ResponseEntity<PageImpl<Map<UUID, List<InvoiceResponse>>>> getAllInvoices(Pageable pageable);

    @GetMapping(ORDER + ID)
    ResponseEntity<InvoiceResponse> getInvoiceByOrderId(@RequestParam("orderId") @NotNull UUID orderId);

    @GetMapping(NUMBER)
    ResponseEntity<InvoiceResponse> getInvoiceByInvoiceNumber(@RequestParam("invoiceNumber") @NotNull @Size(min = 12, max = 12) String invoiceNumber);
}