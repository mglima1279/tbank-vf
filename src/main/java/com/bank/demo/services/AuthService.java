package com.bank.demo.services;


import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bank.demo.dto.UserRequestDTO;
import com.bank.demo.entities.User;
import com.bank.demo.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User loadUserByUsername(String username) {
        User user = repository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return user;
    }

    public User registerUser(UserRequestDTO request) {
        if (repository.findByUsername(request.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        return repository.save(user);
    }

    public User authenticateUser(UserRequestDTO request) {
        if (!passwordMatches(request)) {
            throw new IllegalArgumentException("Incorrect username or password");
        }

        return loadUserByUsername(request.getUsername());
    }
    
    private Boolean passwordMatches(UserRequestDTO request) {
        String encodedPassword = repository.findByUsername(request.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("incorrect username or password"))
                .getPassword();

        return passwordEncoder.matches(request.getPassword(), encodedPassword);
    }
}