package com.invoice_generator.quickbill.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.validation.constraints.NotBlank;


@Entity
public class User implements UserDetails{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Password is required")
    private String password;

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotBlank(message = "Contact is required")
    @Column(unique = true)
    private String contact;

    private String role;

    //  Constructors

    public User() {
    }

    public User( String password, String firstName, String lastName, String contact, String roles) {
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.contact = contact;
        this.role = roles;
    }

    public User(Long id,String password, String firstName, String lastName, String contact, String roles) {
        this.id = id;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.contact = contact;
        this.role = roles;
    }

    //  Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getContact() {
        return contact;
    }
    
    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    // toString, equals, hashCode 

    @Override
    public String toString() {
        return "User{" +
               "id=" + id +
               ", firstName='" + firstName + '\'' +
               ", lastName='" + lastName + '\'' +
               ", contact='" + contact + '\'' +
               ", roles=" + role +
               '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return Objects.equals(id, user.id) &&
               Objects.equals(contact, user.contact);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, contact);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<SimpleGrantedAuthority> authorCollections = new ArrayList<>();
        authorCollections.add(new SimpleGrantedAuthority(role));
        return authorCollections;
    }

    @Override
    public String getUsername() {
        return contact;
    }

}
