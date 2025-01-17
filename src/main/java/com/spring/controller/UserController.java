package com.spring.controller;

import com.spring.dto.CreateUserRequestDTO;
import com.spring.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserServiceImpl userServiceImpl;

    /**
     * Create User with Initial Stock Data:
     * POST /api/user
     */
    @PostMapping
    public ResponseEntity<?> createUserWithPortfolio(@RequestBody CreateUserRequestDTO createUserRequest) {
        try {
            userServiceImpl.createUserWithPortfolio(createUserRequest);
            return ResponseEntity.ok("{\"status\":\"Success\", \"message\":\"User created with portfolio successfully.\"}");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{\"status\":\"Failure\", \"message\":\"" + e.getMessage() + "\"}");
        }
    }
}
