package com.invoice_generator.quickbill.config;

import com.invoice_generator.quickbill.entity.*;
import com.invoice_generator.quickbill.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class RoleSeeder implements CommandLineRunner {

    private final RoleRepository roleRepository;

    public RoleSeeder(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) {
        createRoleIfNotExists("ROLE_ADMIN");
        createRoleIfNotExists("ROLE_MANAGER");
        createRoleIfNotExists("ROLE_RECEPTIONIST");
    }

    private void createRoleIfNotExists(String roleName) {
        roleRepository.findByName(roleName).orElseGet(() -> {
            Role role = new Role(roleName);
            return roleRepository.save(role);
        });
    }
}
