package com.invoice_generator.quickbill.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.invoice_generator.quickbill.entity.Customer;
import com.invoice_generator.quickbill.repository.CustomerRepository;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private final CustomerRepository customerRepository;

    // Constructor injection (recommended)
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    // CREATE or UPDATE customer
    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    // READ all customers
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    // READ a single customer by ID
    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Customer not found with ID: " + id));
    }

    // DELETE a customer by ID
    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }
}
