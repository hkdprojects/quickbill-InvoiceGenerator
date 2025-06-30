package com.invoice_generator.quickbill.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.invoice_generator.quickbill.entity.InvoiceLineItem;

@Repository
public interface InvoiceFormRepository extends JpaRepository<InvoiceLineItem ,Long>{
    
}
