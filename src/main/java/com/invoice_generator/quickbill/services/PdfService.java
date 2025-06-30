package com.invoice_generator.quickbill.services;

import org.springframework.stereotype.Service;

import com.invoice_generator.quickbill.entity.Invoice;
import com.invoice_generator.quickbill.entity.InvoiceLineItem;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import jakarta.servlet.http.HttpServletResponse;

@Service
public class PdfService {
    public void generateInvoicePdf(Invoice invoice, HttpServletResponse response) throws Exception {
        Document document = new Document();
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        document.add(new Paragraph("Invoice #" + invoice.getId()));
        document.add(new Paragraph("Customer: " + invoice.getCustomer().getCustomerName()));
        document.add(new Paragraph("Date: " + invoice.getInvoiceDate()));
        document.add(new Paragraph("Address: "+ invoice.getCustomer().getCustomerAddress()));
        document.add(new Paragraph("Contact: " + invoice.getCustomer().getCustomerContact()));

        PdfPTable table = new PdfPTable(4);
        table.addCell("Item");
        table.addCell("Qty");
        table.addCell("Unit Price");
        table.addCell("Total");

        for (InvoiceLineItem ii : invoice.getInvoiceLineItems()) {
            System.out.println(ii.getItem());
            table.addCell(ii.getItem().getItemName());
            table.addCell(String.valueOf(ii.getQuantity()));
            table.addCell(String.valueOf(ii.getItem().getItemUnitPrice()));
            table.addCell(String.valueOf(ii.getTotalPrice()));
        }

        document.add(table);
        document.add(new Paragraph("Subtotal: ₹" + invoice.getSubTotal()));
        document.add(new Paragraph("GST (18%): ₹" + invoice.getGstAmount()));
        document.add(new Paragraph("Total: ₹" + invoice.getTotalAmount()));

        document.close();
    }
}

