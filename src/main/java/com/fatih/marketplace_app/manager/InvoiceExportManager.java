package com.fatih.marketplace_app.manager;

import com.fatih.marketplace_app.entity.InvoiceEntity;

import com.fatih.marketplace_app.exception.BusinessException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.util.Locale;

/**
 * Manager class responsible for exporting invoice data to various formats.
 * Currently, supports PDF export functionality.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class InvoiceExportManager {

    private final MessageSource messageSource;

    /**
     * Exports an invoice to a PDF file.
     * Creates a PDF document containing all relevant invoice information including
     * invoice number, customer details, order information, product list, and shipping address.
     *
     * @param invoice The invoice entity to be exported
     * @throws Exception If any error occurs during PDF generation or file writing
     */
    public void exportInvoiceToPdf(InvoiceEntity invoice) throws Exception {
        log.info("Starting PDF export for invoice with number: {}", invoice.getInvoiceNumber());

        String fileName = "INVOICE" + invoice.getInvoiceNumber() + ".pdf";
        log.debug("PDF file will be generated with name: {}", fileName);

        Document document = new Document();

        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(fileName));
        log.debug("PdfWriter instance created successfully");

        document.open();
        log.debug("PDF document opened successfully");

        document.add(new Paragraph("INVOICE"));
        log.trace("Added title to PDF document");

        document.add(new Paragraph("\n"));
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("INVOICE NUMBER: " + invoice.getInvoiceNumber()));
        log.trace("Added invoice number details to PDF document");

        document.add(new Paragraph("\n"));
        document.add(new Paragraph("FULL NAME:" + invoice.getOrder().getUser().getFirstName() + invoice.getOrder().getUser().getLastName()));
        log.trace("Added customer name details to PDF document");

        document.add(new Paragraph("\n"));
        document.add(new Paragraph("ORDER NUMBER: " + invoice.getOrder().getOrderNumber()));
        log.trace("Added order number details to PDF document");

        document.add(new Paragraph("\n"));
        document.add(new Paragraph("PRICE: " + invoice.getOrder().getFinalPrice()));
        log.trace("Added price details to PDF document");

        document.add(new Paragraph("\n"));
        document.add(new Paragraph("PRODUCTS:"));
        log.debug("Adding product list to PDF document");

        invoice.getOrder().getCart().getCartItem().forEach(cartItem -> {
            String productName = cartItem.getProduct().getProductName();
            log.trace("Added product '{}' to PDF document", productName);
            try {
                document.add(new Paragraph(productName));
            } catch (DocumentException e) {
                log.error("Error adding product '{}' to PDF document: {}", productName, e.getMessage());
                throw new BusinessException(messageSource
                        .getMessage("backend.exceptions.INV003",
                                new Object[]{},
                                Locale.getDefault()));
            }
        });

        document.add(new Paragraph("\n"));
        document.add(new Paragraph("ADDRESS: " + invoice.getOrder().getAddress().getCountry() + "\n" +
                invoice.getOrder().getAddress().getCity() + "\n" +
                invoice.getOrder().getAddress().getDistrict() + "\n" +
                invoice.getOrder().getAddress().getNeighbourhood() + "\n" +
                invoice.getOrder().getAddress().getStreet() + "\n" +
                invoice.getOrder().getAddress().getApartment() + "\n" +
                invoice.getOrder().getAddress().getApartment() + "\n" +
                invoice.getOrder().getAddress().getZipCode()));
        log.trace("Added shipping address details to PDF document");

        document.close();
        writer.close();
        log.info("PDF export completed successfully for invoice: {}", invoice.getInvoiceNumber());
    }
}
