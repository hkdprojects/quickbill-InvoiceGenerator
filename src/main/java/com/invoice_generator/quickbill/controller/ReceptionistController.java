package com.invoice_generator.quickbill.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@EnableMethodSecurity
@RequestMapping("/receptionist")
public class ReceptionistController {

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MANAGER')")
    @GetMapping("/dashboard")
    public String receptionistDashboard() {
        return "receptionist-dashboard"; // receptionist-dashboard.html
    }

    // Create customer, generate invoice, view invoices
}
