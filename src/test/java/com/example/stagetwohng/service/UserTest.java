package com.example.stagetwohng.service;

import com.example.stagetwohng.model.User;
import com.example.stagetwohng.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.hibernate.annotations.AttributeAccessor;
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
    public void testUser() {
        // Create a new user
        User user = new User();
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");

        // Save the user using the repository
        userRepository.save(user);

        // Retrieve the saved user from the database
        User savedUser = userRepository.findById(user.getId()).orElse(null);
        System.out.println(savedUser);

        // Assertions to verify the saved user
        assertNotNull(savedUser);
        assertEquals(user.getName(), savedUser.getName());
        assertEquals(user.getEmail(), savedUser.getEmail());

    }

}
