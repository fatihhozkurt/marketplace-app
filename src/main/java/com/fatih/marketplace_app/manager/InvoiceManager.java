package com.fatih.marketplace_app.manager;

import com.fatih.marketplace_app.dao.InvoiceDao;
import com.fatih.marketplace_app.entity.InvoiceEntity;
import com.fatih.marketplace_app.entity.OrderEntity;
import com.fatih.marketplace_app.exception.BusinessException;
import com.fatih.marketplace_app.exception.ResourceNotFoundException;
import com.fatih.marketplace_app.manager.service.InvoiceService;
import com.fatih.marketplace_app.manager.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InvoiceManager implements InvoiceService {

    private final InvoiceDao invoiceDao;
    private final OrderService orderService;
    private final InvoiceExportManager invoiceExportManager;
    private final MessageSource messageSource;

    @Transactional
    @Override
    public void exportInvoice(UUID orderId) {
        OrderEntity foundOrder = orderService.getOrderById(orderId);

        InvoiceEntity invoiceEntity = InvoiceEntity.builder()
                .order(foundOrder)
                .invoiceNumber(orderService.generateRandomOrderNumber())
                .build();

        invoiceDao.save(invoiceEntity);
        processExport(invoiceEntity);
    }

    @Transactional
    @Override
    public void deleteInvoice(UUID invoiceId) {
        InvoiceEntity foundInvoice = getInvoiceById(invoiceId);
        invoiceDao.delete(foundInvoice);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public InvoiceEntity getInvoiceByOrderId(UUID orderId) {
        return invoiceDao.findByOrderId(orderId)
                .orElseThrow(() -> new ResourceNotFoundException(messageSource
                        .getMessage("backend.exceptions.INV002",
                                new Object[]{orderId},
                                Locale.getDefault())));
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public Page<InvoiceEntity> getAllInvoices(Pageable pageable) {
        return invoiceDao.findAll(pageable);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public InvoiceEntity getInvoiceById(UUID invoiceId) {
        return invoiceDao.findById(invoiceId)
                .orElseThrow(() -> new ResourceNotFoundException(messageSource
                        .getMessage("backend.exceptions.INV001",
                                new Object[]{invoiceId},
                                Locale.getDefault())));
    }

    @Override
    public InvoiceEntity getInvoiceByInvoiceNumber(String invoiceNumber) {
        return invoiceDao.findByInvoiceNumber(invoiceNumber)
                .orElseThrow(() -> new ResourceNotFoundException(messageSource
                        .getMessage("backend.exceptions.INV004",
                                new Object[]{invoiceNumber},
                                Locale.getDefault())));
    }

    private void processExport(InvoiceEntity invoiceEntity) {
        try {
            invoiceExportManager.exportInvoiceToPdf(invoiceEntity);
        } catch (Exception ex) {
            throw new BusinessException(messageSource
                    .getMessage("backend.exceptions.INV003",
                            new Object[]{},
                            Locale.getDefault()));
        }
    }
}
