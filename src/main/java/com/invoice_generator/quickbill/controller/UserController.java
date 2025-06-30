package com.invoice_generator.quickbill.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.invoice_generator.quickbill.entity.User;
import com.invoice_generator.quickbill.services.UserService;

@Controller
@EnableMethodSecurity
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @GetMapping("/create")
    public String createNewUser() {
        return "redirect:/auth/signup"; // or wherever your signup mapping is
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MANAGER')")
    @GetMapping("/list")
    public String listUsers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "users-list";
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MANAGER')")
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "signup"; 
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MANAGER')")
    // Delete user
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/user/list"; 
    }

}