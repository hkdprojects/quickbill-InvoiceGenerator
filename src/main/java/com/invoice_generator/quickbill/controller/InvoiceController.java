package com.invoice_generator.quickbill.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.invoice_generator.quickbill.entity.Invoice;
import com.invoice_generator.quickbill.services.CustomerService;
import com.invoice_generator.quickbill.services.InvoiceService;
import com.invoice_generator.quickbill.services.ItemService;
import com.invoice_generator.quickbill.services.PdfService;

import jakarta.servlet.http.HttpServletResponse;

@Controller
@EnableMethodSecurity
@RequestMapping("/invoices")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;
    @Autowired
    private CustomerService customerService; 
    @Autowired
    private ItemService itemService;  
    @Autowired
    private PdfService pdfService;

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MANAGER','ROLE_RECEPTIONIST')")
    @GetMapping("/new")
    public String newInvoiceForm(Model model) {
        model.addAttribute("customers", customerService.getAllCustomers()); // Assuming this method exists
        model.addAttribute("items", itemService.getAllItems());         // Assuming this method exists
        return "invoice-form";
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MANAGER','ROLE_RECEPTIONIST')")
    @PostMapping("/save")
    public String saveInvoice(@RequestParam Long customerId,
                              @RequestParam List<Long> itemId,
                              @RequestParam List<Integer> itemQuantity) {
        
        // Controller delegates all the complex logic to the service
        invoiceService.createAndSaveInvoice(customerId, itemId, itemQuantity);
        
        return "redirect:/invoices/list";
    }

    @PostMapping("/preview")
    public String previewInvoice(@RequestParam Long customerId,
                                 @RequestParam List<Long> itemId,
                                 @RequestParam List<Integer> itemQuantity,
                                 Model model) {

        // Calls the NEW service method that only builds the object and does NOT save it
        Invoice previewInvoice = invoiceService.createPreviewInvoice(customerId, itemId, itemQuantity);
        
        model.addAttribute("invoice", previewInvoice);
        return "invoice-preview";
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MANAGER','ROLE_RECEPTIONIST')")
    @GetMapping("/list")
    public String listInvoices(Model model) {
        model.addAttribute("invoices", invoiceService.getAllInvoices());
        return "invoice-list";
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MANAGER','ROLE_RECEPTIONIST')")
    @GetMapping("/pdf/{id}")
    public void downloadPdf(@PathVariable Long id, HttpServletResponse response) throws Exception {
        Invoice invoice = invoiceService.getInvoiceById(id);
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=invoice_" + id + ".pdf");
        pdfService.generateInvoicePdf(invoice, response);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MANAGER')")
    @GetMapping("/delete/{id}")
    public String deleteInvoice(@PathVariable("id") Long id) {
        invoiceService.deleteInvoice(id);
        return "redirect:/invoices/list";
    }
}