package com.invoice_generator.quickbill.services;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.invoice_generator.quickbill.entity.Invoice;
import com.invoice_generator.quickbill.entity.InvoiceLineItem;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import jakarta.servlet.http.HttpServletResponse;

@Service
public class PdfService {

    // Inject company details from application.properties
    @Value("${app.company.name}")
    private String companyName;

    @Value("${app.company.address}")
    private String companyAddress;

    @Value("${app.company.contact}")
    private String companyContact;

    // Define fonts for reuse
    private static final Font FONT_TITLE = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20, BaseColor.BLACK);
    private static final Font FONT_HEADER = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, BaseColor.WHITE);
    private static final Font FONT_BODY_BOLD = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, BaseColor.BLACK);
    private static final Font FONT_BODY = FontFactory.getFont(FontFactory.HELVETICA, 10, BaseColor.BLACK);
    
    public void generateInvoicePdf(Invoice invoice, HttpServletResponse response) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        addHeader(document, invoice);

        document.add(new Paragraph("\n"));

        addCustomerInfo(document, invoice);

        document.add(new Paragraph("\n"));
        addItemsTable(document, invoice);

        addTotals(document, invoice);
        
        addFooter(document);

        document.close();
    }

    private void addHeader(Document document, Invoice invoice) throws DocumentException {
        PdfPTable headerTable = new PdfPTable(2);
        headerTable.setWidthPercentage(100);
        headerTable.setSpacingAfter(20f);

        // --- Company Details Cell (Left) ---
        PdfPCell companyCell = new PdfPCell();
        companyCell.setBorder(Rectangle.NO_BORDER);
        companyCell.addElement(new Phrase(companyName, FONT_BODY_BOLD));
        companyCell.addElement(new Phrase(companyAddress, FONT_BODY));
        companyCell.addElement(new Phrase(companyContact, FONT_BODY));
        headerTable.addCell(companyCell);
        
        // --- Invoice Title Cell (Right) ---
        PdfPCell invoiceTitleCell = new PdfPCell();
        invoiceTitleCell.setBorder(Rectangle.NO_BORDER);
        invoiceTitleCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        invoiceTitleCell.addElement(new Phrase("INVOICE", FONT_TITLE));
        invoiceTitleCell.addElement(new Phrase("Invoice #: " + invoice.getId(), FONT_BODY));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        invoiceTitleCell.addElement(new Phrase("Date: " + invoice.getInvoiceDate().format(formatter), FONT_BODY));
        headerTable.addCell(invoiceTitleCell);
        
        document.add(headerTable);
    }
    
    private void addCustomerInfo(Document document, Invoice invoice) throws DocumentException {
        Paragraph billTo = new Paragraph("BILL TO:", FONT_BODY_BOLD);
        document.add(billTo);
        
        document.add(new Paragraph(invoice.getCustomer().getCustomerName(), FONT_BODY));
        document.add(new Paragraph(invoice.getCustomer().getCustomerAddress(), FONT_BODY));
        document.add(new Paragraph(invoice.getCustomer().getCustomerContact(), FONT_BODY));
    }

    private void addItemsTable(Document document, Invoice invoice) throws DocumentException {
        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100);
        table.setWidths(new float[]{3f, 4f, 1f, 2f, 2f}); // Relative column widths

        addTableCell(table, "Item Name", Element.ALIGN_CENTER, FONT_HEADER, BaseColor.DARK_GRAY);
        addTableCell(table, "Description", Element.ALIGN_CENTER, FONT_HEADER, BaseColor.DARK_GRAY);
        addTableCell(table, "Qty", Element.ALIGN_CENTER, FONT_HEADER, BaseColor.DARK_GRAY);
        addTableCell(table, "Unit Price", Element.ALIGN_CENTER, FONT_HEADER, BaseColor.DARK_GRAY);
        addTableCell(table, "Total", Element.ALIGN_CENTER, FONT_HEADER, BaseColor.DARK_GRAY);

        DecimalFormat df = new DecimalFormat("#,##0.00");
        for (InvoiceLineItem lineItem : invoice.getInvoiceLineItems()) {
            addTableCell(table, lineItem.getItem().getItemName(), Element.ALIGN_LEFT, FONT_BODY, null);
            addTableCell(table, lineItem.getItem().getItemDescription(), Element.ALIGN_LEFT, FONT_BODY, null);
            addTableCell(table, String.valueOf(lineItem.getQuantity()), Element.ALIGN_RIGHT, FONT_BODY, null);
            addTableCell(table, "₹" + df.format(lineItem.getItem().getItemUnitPrice()), Element.ALIGN_RIGHT, FONT_BODY, null);
            addTableCell(table, "₹" + df.format(lineItem.getTotalPrice()), Element.ALIGN_RIGHT, FONT_BODY, null);
        }

        document.add(table);
    }
    
    private void addTotals(Document document, Invoice invoice) throws DocumentException {
        PdfPTable totalsTable = new PdfPTable(2);
        totalsTable.setWidthPercentage(50);
        totalsTable.setHorizontalAlignment(Element.ALIGN_RIGHT);
        totalsTable.setSpacingBefore(10f);

        DecimalFormat df = new DecimalFormat("#,##0.00");
        
        // Add Subtotal
        addTotalsCell(totalsTable, "Subtotal:", Element.ALIGN_RIGHT, FONT_BODY);
        addTotalsCell(totalsTable, "₹" + df.format(invoice.getSubTotal()), Element.ALIGN_RIGHT, FONT_BODY);
        
        // Add GST
        addTotalsCell(totalsTable, "GST (18%):", Element.ALIGN_RIGHT, FONT_BODY);
        addTotalsCell(totalsTable, "₹" + df.format(invoice.getGstAmount()), Element.ALIGN_RIGHT, FONT_BODY);
        
        // Add Grand Total
        addTotalsCell(totalsTable, "Grand Total:", Element.ALIGN_RIGHT, FONT_BODY_BOLD);
        addTotalsCell(totalsTable, "₹" + df.format(invoice.getTotalAmount()), Element.ALIGN_RIGHT, FONT_BODY_BOLD);
        
        document.add(totalsTable);
    }
    
    private void addFooter(Document document) throws DocumentException {
        Paragraph footer = new Paragraph("\nThank you for your business!", FONT_BODY);
        footer.setAlignment(Element.ALIGN_CENTER);
        document.add(footer);
    }

    // Helper method to create styled table cells
    private void addTableCell(PdfPTable table, String text, int alignment, Font font, BaseColor backgroundColor) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setHorizontalAlignment(alignment);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setPadding(5);
        if (backgroundColor != null) {
            cell.setBackgroundColor(backgroundColor);
        }
        table.addCell(cell);
    }
    
    // Helper method for the borderless totals table cells
    private void addTotalsCell(PdfPTable table, String text, int alignment, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setHorizontalAlignment(alignment);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setPadding(4);
        table.addCell(cell);
    }
}