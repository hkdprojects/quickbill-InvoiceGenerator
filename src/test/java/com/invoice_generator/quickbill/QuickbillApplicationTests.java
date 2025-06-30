package com.invoice_generator.quickbill;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.invoice_generator.quickbill.entity.User;
import com.invoice_generator.quickbill.repository.UserRepository;
import com.invoice_generator.quickbill.services.UserService;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class QuickbillApplicationTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void settingUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    User testSave() {
        User user = new User(1L,"shivu@123", "Shiva", "Kumar", "shivu@gmail.com", "ROLE_RECEPTIONIST");
        when(userRepository.save(user)).thenReturn(user);
        userService.saveUser(user);
        verify(userRepository).save(user);
        return user;
    }

    @Test
    void testGetAllUsers() {

        User user1 = new User(1L,"adrsha123", "Adarsha", "JP", "adarsha@gmail.com", "ROLE_ADMIN");
        User user2 = new User(1L,"chandan@123", "Chandan", "KM", "chandan@gmail.com", "ROLE_MANAGER");
        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        List<User> users = userService.getAllUsers();
        assertEquals(2, users.size());
        assertEquals("adarsha@gmail.com", users.get(0).getUsername());
        assertEquals("chandan@gmail.com", users.get(1).getUsername());
        verify(userRepository).findAll();
    }

    @Test
    void updateUser() {
        User user = new User(1L, "adrsha123", "Adarsha", "JP", "adarsha@gmail.com", "ROLE_MANAGER");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        User updatedUser = userService.updateUser(user);

        assertEquals("adarsha@gmail.com", updatedUser.getContact());
        verify(userRepository).findById(1L);
        verify(userRepository).save(any(User.class));
    }

    @Test
    void testDelete() {
        User user = new User(1l, "adrsha123", "Adarsha", "JP", "adarsha@gmail.com", "ROLE_ADMIN");
        when(userRepository.findByContact("adarsha@gmail.com")).thenReturn(Optional.of(user));
        userService.deleteUser(1l);
        verify(userRepository).delete(user);
    }

    @Test
    void contextLoads() {
    }
}