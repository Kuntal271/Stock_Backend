package com.spring.repository;

import com.spring.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testSaveAndFetchUser() {
        User user = new User();
        user.setUserName("john_doe");
        user.setEmail("john@example.com");

        userRepository.save(user);
        Optional<User> retrieved = Optional.ofNullable(userRepository.findByUserName("john_doe"));

        assertTrue(retrieved.isPresent(), "User should be present");
        assertEquals("john_doe", retrieved.get().getUserName(), "Usernames should match");
    }
}
