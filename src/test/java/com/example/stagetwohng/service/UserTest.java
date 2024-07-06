package com.example.stagetwohng.service;

import com.example.stagetwohng.model.User;
import com.example.stagetwohng.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserTest {

    private UserRepository userRepository;

    User user = new User();


    @Test
    public void testUser() {
        user.setEmail("hhhh");
        userRepository.save(user);
    }

}
