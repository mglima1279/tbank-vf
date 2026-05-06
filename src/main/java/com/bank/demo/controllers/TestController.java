package com.bank.demo.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.demo.entities.User;
import com.bank.demo.exeptions.CustomException;
import com.bank.demo.services.AuthService;
import com.bank.demo.services.JwtService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {
    private final AuthService authService;
    private final JwtService jwtService;

    @GetMapping
    public ResponseEntity<String> testEndpoint() {
        return ResponseEntity.ok("Server is running!!");
    }

    @GetMapping("/auth")
    public ResponseEntity<String> authTestEndpoint(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            throw new CustomException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }

        User user = authService.loadUserByUsername(userDetails.getUsername());
        String token = jwtService.generateToken(user);
        return ResponseEntity.ok("Authenticated user: " + user.getUsername() + ", Token: " + token);
    }
}
