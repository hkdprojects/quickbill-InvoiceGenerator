package com.invoice_generator.quickbill.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/manager")
public class ManagerController {

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MANAGER')")
    @GetMapping("/dashboard")
    public String managerDashboard() {
        return "manager-dashboard"; // manager-dashboard.html
    }

    // Add item, update quantity
}
