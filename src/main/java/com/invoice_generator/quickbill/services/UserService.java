package com.invoice_generator.quickbill.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.invoice_generator.quickbill.entity.User;
import com.invoice_generator.quickbill.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private Logger logger = LoggerFactory.getLogger(getClass());

    // @Autowired
    // public UserService(UserRepository userRepository,
    // RoleRepository roleRepository,
    // PasswordEncoder passwordEncoder) {
    // this.userRepository = userRepository;
    // this.roleRepository = roleRepository;
    // this.passwordEncoder = passwordEncoder;
    // }

    // For Registration
    public void saveUser(User user) {

        userRepository.save(user);
    }

    // For Spring Security login (required method)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Delegate to contact-based login (email or phone)
        logger.info(username + " tryuing to find");
        User user = userRepository.findByContact(username).orElse(null);
        if (user == null)
            throw new UsernameNotFoundException(username);
        logger.info(username + " found");
        return user;
    }

    // Read all
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // // Custom login using email or phone number
    // public UserDetails loadUserByContact(String contact) {
    // User user = userRepository.findByContact(contact)
    // .orElseThrow(() -> new ContactNotFoundException("User not found with contact:
    // " + contact));

    // return new org.springframework.security.core.userdetails.User(
    // user.getContact(), // or user.getPhone(), but must match login form
    // user.getPassword(),
    // user.getRole()
    // );
    // }
    // Read one by ID
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
    }

    public void deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow();
        userRepository.delete(user);
    }

    public User updateUser(User updatedUser) {
    User existingUser = userRepository.findById(updatedUser.getId())
            .orElseThrow(() -> new RuntimeException("User not found"));

    existingUser.setFirstName(updatedUser.getFirstName());
    existingUser.setLastName(updatedUser.getLastName());
    existingUser.setContact(updatedUser.getContact());
    existingUser.setRole(updatedUser.getRole());

    return userRepository.save(existingUser);
}


}