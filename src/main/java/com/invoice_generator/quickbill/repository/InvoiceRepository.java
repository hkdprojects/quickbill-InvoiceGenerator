package com.invoice_generator.quickbill.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.invoice_generator.quickbill.entity.Invoice;

public interface InvoiceRepository extends JpaRepository<Invoice ,Long>{

    
} 