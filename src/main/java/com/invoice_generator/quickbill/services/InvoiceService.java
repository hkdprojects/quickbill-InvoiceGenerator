package com.invoice_generator.quickbill.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.invoice_generator.quickbill.entity.Customer;
import com.invoice_generator.quickbill.entity.Invoice;
import com.invoice_generator.quickbill.entity.InvoiceLineItem;
import com.invoice_generator.quickbill.entity.Item;
import com.invoice_generator.quickbill.repository.CustomerRepository;
import com.invoice_generator.quickbill.repository.InvoiceRepository;
import com.invoice_generator.quickbill.repository.ItemRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class InvoiceService {

    private static final BigDecimal GST_RATE = new BigDecimal("0.18");

    @Autowired
    private InvoiceRepository invoiceRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ItemRepository itemRepository;

    @Transactional
    public Invoice createAndSaveInvoice(Long customerId, List<Long> itemIds, List<Integer> quantities) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with ID: " + customerId));

        Invoice invoice = new Invoice();
        invoice.setCustomer(customer);
        invoice.setInvoiceDate(LocalDate.now());

        BigDecimal subTotal = BigDecimal.ZERO;

        for (int i = 0; i < itemIds.size(); i++) {
            Long itemId = itemIds.get(i);
            int quantity = quantities.get(i);

            if (quantity <= 0) continue; // Skip items with no quantity

            Item item = itemRepository.findById(itemId)
                    .orElseThrow(() -> new EntityNotFoundException("Item not found with ID: " + itemId));

            BigDecimal lineItemTotal = item.getItemUnitPrice().multiply(new BigDecimal(quantity));

            InvoiceLineItem lineItem = new InvoiceLineItem();
            lineItem.setItem(item);
            lineItem.setQuantity(quantity);
            lineItem.setTotalPrice(lineItemTotal);
            
            // Use the helper method to establish the bi-directional link
            invoice.addLineItem(lineItem);

            subTotal = subTotal.add(lineItemTotal);
        }

        BigDecimal gstAmount = subTotal.multiply(GST_RATE).setScale(2, RoundingMode.HALF_UP);
        BigDecimal totalAmount = subTotal.add(gstAmount);

        invoice.setSubTotal(subTotal);
        invoice.setGstAmount(gstAmount);
        invoice.setTotalAmount(totalAmount);

        return invoiceRepository.save(invoice);
    }

    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    public Invoice getInvoiceById(Long id) {
        // Fetch line items eagerly for detail view
        return invoiceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Invoice not found with ID: " + id));
    }

    @Transactional
    public void deleteInvoice(Long invoiceId) {
        if (!invoiceRepository.existsById(invoiceId)) {
            throw new EntityNotFoundException("Cannot delete. Invoice not found with ID: " + invoiceId);
        }
        invoiceRepository.deleteById(invoiceId);
    }
}