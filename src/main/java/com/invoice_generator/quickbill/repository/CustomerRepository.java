package com.invoice_generator.quickbill.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.invoice_generator.quickbill.entity.Customer;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    //find by name
    List<Customer> findAllByCustomerName(String name);

    //find by contact
    List<Customer> findAllByCustomerContact(String contact);
}
