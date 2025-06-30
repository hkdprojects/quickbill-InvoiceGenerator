package com.invoice_generator.quickbill.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.invoice_generator.quickbill.entity.Customer;
import com.invoice_generator.quickbill.services.CustomerService;

import java.util.List;

@Controller
@EnableMethodSecurity
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MANAGER','ROLE_RECEPTIONIST')")
    // Show all customers
    @GetMapping("/list")
    public String listCustomers(Model model) {
        List<Customer> customers = customerService.getAllCustomers();
        model.addAttribute("customers", customers);
        return "customer-list"; // View: customer-list.html
    }
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MANAGER','ROLE_RECEPTIONIST')")
    // Show form to add new customer
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "customer-form"; // View: customer-form.html
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MANAGER','ROLE_RECEPTIONIST')")
    // Save new or updated customer
    @PostMapping("/save")
    public String saveCustomer(@ModelAttribute("customer") Customer customer) {
        customerService.saveCustomer(customer);
        return "redirect:/customers/list";
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MANAGER')")
    // Show form to edit existing customer
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Customer customer = customerService.getCustomerById(id);
        model.addAttribute("customer", customer);
        return "customer-form"; // View: reuse customer-form.html
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MANAGER')")
    // Delete a customer
    @GetMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return "redirect:/customers/list";
    }
}
