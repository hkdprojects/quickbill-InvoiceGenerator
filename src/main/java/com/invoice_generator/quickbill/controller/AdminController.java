package com.invoice_generator.quickbill.controller;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@EnableMethodSecurity
@RequestMapping("/admin")
public class AdminController {

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @GetMapping("/dashboard")
    public String adminDashboard() {
        return "admin-dashboard"; // admin-dashboard.html
    }
}

