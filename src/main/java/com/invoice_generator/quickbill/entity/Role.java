package com.invoice_generator.quickbill.entity;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // e.g., ROLE_ADMIN, ROLE_MANAGER, ROLE_RECEPTIONIST

    // Constructors
    public Role() {
    }

    public Role(String name) {
        this.name = name;
    }

    public Role(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // equals and hashCode (based on 'name')
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Role role)) return false;
        return Objects.equals(name, role.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    // toString
    @Override
    public String toString() {
        return "Role{" +
               "id=" + id +
               ", name='" + name + '\'' +
               '}';
    }
}
