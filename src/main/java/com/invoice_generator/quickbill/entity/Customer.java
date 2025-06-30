package com.invoice_generator.quickbill.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    
    private Long customerId;

    @NotBlank(message = "customerName is required")
    private String customerName;

    @NotBlank(message = "customerAddress is required")
    private String customerAddress;

    @NotBlank(message = "customerContact is required")
    private String customerContact;

    private LocalDate invoiceDate;

    //getters and setters
    public Long getCustomerId() {
        return customerId;
    }
    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
    public String getCustomerName() {
        return customerName;
    }
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    public String getCustomerAddress() {
        return customerAddress;
    }
    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }
    public String getCustomerContact() {
        return customerContact;
    }
    public void setCustomerContact(String customerContact) {
        this.customerContact = customerContact;
    }
    public LocalDate getInvoiceDate() {
        return invoiceDate;
    }
    public void setInvoiceDate(LocalDate invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    //tostring
    @Override
    public String toString() {
        return "Customer [CustomerId=" + customerId + ", customerName=" + customerName + ", customerAddress="
                + customerAddress + ", customerContact=" + customerContact + ", invoiceDate=" + invoiceDate + "]";
    }    
}
