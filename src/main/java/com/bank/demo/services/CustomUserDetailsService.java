package com.bank.demo.services;


import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.bank.demo.entities.User;
import com.bank.demo.repositories.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository repository;

    public CustomUserDetailsService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User loadUserByUsername(String username) {
        User user = repository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("User not found"));

        return user;
    }
}