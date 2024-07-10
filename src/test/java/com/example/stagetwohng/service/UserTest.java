//package com.example.stagetwohng.service;
//
//import com.example.stagetwohng.model.User;
//import com.example.stagetwohng.repository.UserRepository;
//import jakarta.transaction.Transactional;
//import org.hibernate.annotations.AttributeAccessor;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//
//@SpringBootTest
//@Transactional
//public class UserTest {
//
//
//    @Autowired
//    private UserRepository userRepository;
//
//
//    @Test
//    public void testUser() {
//
//        User user = new User();
//        user.setFirstName("John");
//        user.setEmail("john.doe@example.com");
//
//        userRepository.save(user);
//
//        User savedUser = userRepository.findById(user.getId()).orElse(null);
//        System.out.println(savedUser);
//
//        assertNotNull(savedUser);
//        assertEquals(user.getFirstName(), savedUser.getFirstName());
//        assertEquals(user.getEmail(), savedUser.getEmail());
//
//    }
//
//}
