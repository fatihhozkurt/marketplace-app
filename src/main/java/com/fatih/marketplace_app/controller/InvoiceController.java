package com.fatih.marketplace_app.controller;

import com.fatih.marketplace_app.controller.api.InvoiceApi;
import com.fatih.marketplace_app.dto.request.invoice.CreateInvoiceRequest;
import com.fatih.marketplace_app.dto.response.invoice.InvoiceResponse;
import com.fatih.marketplace_app.entity.InvoiceEntity;
import com.fatih.marketplace_app.manager.service.InvoiceService;
import com.fatih.marketplace_app.mapper.InvoiceMapper;
import lombok.RequiredArgsConstructor;
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

@RestController
@RequiredArgsConstructor
public class InvoiceController implements InvoiceApi {

    private final InvoiceService invoiceService;

    @Override
    public ResponseEntity<HttpStatus> exportInvoice(CreateInvoiceRequest createInvoiceRequest) {
        invoiceService.exportInvoice(createInvoiceRequest.orderId());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<HttpStatus> deleteInvoice(UUID invoiceId) {
        invoiceService.deleteInvoice(invoiceId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<InvoiceResponse> getInvoiceById(UUID invoiceId) {

        InvoiceEntity foundInvoice = invoiceService.getInvoiceById(invoiceId);
        InvoiceResponse invoiceResponse = InvoiceMapper.INSTANCE.toInvoiceResponse(foundInvoice);

        return new ResponseEntity<>(invoiceResponse, HttpStatus.FOUND);
    }

    @Override
    public ResponseEntity<PageImpl<Map<UUID, List<InvoiceResponse>>>> getAllInvoices(Pageable pageable) {

        Page<InvoiceEntity> invoiceEntities = invoiceService.getAllInvoices(pageable);
        List<InvoiceResponse> invoiceResponses = InvoiceMapper.INSTANCE.toInvoiceResponseList(invoiceEntities.getContent());
        Map<UUID, List<InvoiceResponse>> invoiceMap = invoiceResponses.stream().collect(Collectors.groupingBy(InvoiceResponse::invoiceId));

        return new ResponseEntity<>(new PageImpl<>(List.of(invoiceMap), pageable, invoiceEntities.getTotalElements()), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<InvoiceResponse> getInvoiceByOrderId(UUID orderId) {

        InvoiceEntity foundInvoice = invoiceService.getInvoiceByOrderId(orderId);
        InvoiceResponse invoiceResponse = InvoiceMapper.INSTANCE.toInvoiceResponse(foundInvoice);

        return new ResponseEntity<>(invoiceResponse, HttpStatus.FOUND);
    }

    @Override
    public ResponseEntity<InvoiceResponse> getInvoiceByInvoiceNumber(String invoiceNumber) {

        InvoiceEntity foundInvoice = invoiceService.getInvoiceByInvoiceNumber(invoiceNumber);
        InvoiceResponse invoiceResponse = InvoiceMapper.INSTANCE.toInvoiceResponse(foundInvoice);

        return new ResponseEntity<>(invoiceResponse, HttpStatus.FOUND);
    }
}