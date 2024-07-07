package com.example.stagetwohng.service;

import com.example.stagetwohng.model.User;
import com.example.stagetwohng.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.hibernate.annotations.AttributeAccessor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
public class UserTest {


    @Autowired
    private UserRepository userRepository;


    @Test
    @DisplayName("Amazing Test Going Down")
    public void testAnotherUser() {
        User user = new User();
        user.setUserId("uniqueUserId123"); // Ensure this field is set
        user.setFirstName("John");
        user.setLastName("Doe"); // Ensure this field is set
        user.setEmail("jo@example.com");
        user.setPassword("password123"); // Ensure this field is set

        userRepository.save(user);

        User savedUser = userRepository.findById(user.getId()).orElse(null);
        System.out.println(savedUser);

        assertNotNull(savedUser);
        assertEquals(user.getFirstName(), savedUser.getFirstName());
        assertEquals(user.getEmail(), savedUser.getEmail());
    }

}
