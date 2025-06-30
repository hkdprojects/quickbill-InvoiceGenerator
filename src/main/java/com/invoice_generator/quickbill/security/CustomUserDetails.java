package com.invoice_generator.quickbill.security;

import java.util.Arrays;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.invoice_generator.quickbill.entity.User;

public class CustomUserDetails extends org.springframework.security.core.userdetails.User {
    private final User user;

    public CustomUserDetails(User user) {
        super(user.getUsername(), user.getPassword(),
                Arrays.stream(user.getRole().split(","))
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.trim()))
                        .toList());
        this.user = user;
    }

    public String getFirstName() {
        return user.getFirstName();
    }

    public User getUser() {
        return user;
    }
}
