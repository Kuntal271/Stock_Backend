package com.spring.service;

import com.spring.dto.CreateUserRequestDTO;
import com.spring.dto.PortfolioResponseDTO;
import com.spring.entity.User;
import com.spring.repository.UserRepository;
import com.spring.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Test
    void testCreateUserWithPortfolio() {
        PortfolioResponseDTO portfolio = new PortfolioResponseDTO();
        CreateUserRequestDTO request = new CreateUserRequestDTO();
        request.setUserName("test@groww");
        request.setEmail("john.doe@example.com");
        PortfolioResponseDTO portfolioResponseDTO = new PortfolioResponseDTO(1L,"AESTHETIK ENGINEERS LTD",100,70.0,68.25,-1.75,-2.5);
        List<PortfolioResponseDTO> portfolioList = new java.util.ArrayList<>();
        portfolioList.add(portfolioResponseDTO);
        request.setHoldings(portfolioList);
        request.setHoldings(Collections.singletonList(portfolio));

        User user = new User(1L, "JohnDoe", "john.doe@example.com");
        when(userRepository.save(any(User.class))).thenReturn(user);

        userServiceImpl.createUserWithPortfolio(request);

        assertNotNull(user);
        assertEquals("JohnDoe", user.getUserName());
        assertEquals("john.doe@example.com", user.getEmail());
    }
}
