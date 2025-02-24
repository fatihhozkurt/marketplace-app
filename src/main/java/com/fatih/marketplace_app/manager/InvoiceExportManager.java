package com.fatih.marketplace_app.manager;

import com.fatih.marketplace_app.entity.InvoiceEntity;

import com.fatih.marketplace_app.exception.BusinessException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class InvoiceExportManager {

    private final MessageSource messageSource;

    public void exportInvoiceToPdf(InvoiceEntity invoice) throws Exception {

        String fileName = "INVOICE" + invoice.getInvoiceNumber() + ".pdf";

        Document document = new Document();

        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(fileName));

        document.open();

        document.add(new Paragraph("INVOICE"));

        document.add(new Paragraph("INVOICE NUMBER: " + invoice.getInvoiceNumber()));

        document.add(new Paragraph("FULL NAME:" + invoice.getOrder().getUser()));

        document.add(new Paragraph("ORDER NUMBER: " + invoice.getOrder().getOrderNumber()));

        document.add(new Paragraph("PRICE: " + invoice.getOrder().getFinalPrice()));

        document.add(new Paragraph("PRODUCTS:"));
        invoice.getOrder().getCart().getCartItem().forEach(cartItem -> {
            String productName = cartItem.getProduct().getProductName();
            try {
                document.add(new Paragraph(productName));
            } catch (DocumentException e) {
                throw new BusinessException(messageSource
                        .getMessage("backend.exceptions.INV003",
                                new Object[]{},
                                Locale.getDefault()));
            }
        });

        document.add(new Paragraph("ADDRESS: " + invoice.getOrder().getAddress()));

        document.close();
        writer.close();
    }
}
