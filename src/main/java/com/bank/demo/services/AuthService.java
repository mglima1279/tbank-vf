package com.bank.demo.services;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bank.demo.dto.UserRequestDTO;
import com.bank.demo.entities.User;
import com.bank.demo.exeptions.CustomException;
import com.bank.demo.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final AccountService accountService;

    @Override
    public User loadUserByUsername(String username) {
        User user = repository.findByUsername(username)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "User not found"));

        return user;
    }

    public User registerUser(UserRequestDTO request) {
        if (repository.findByUsername(request.getUsername()).isPresent()) {
            throw new CustomException(HttpStatus.CONFLICT, "Username already exists");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        user = repository.save(user);
        accountService.create(user, request.getCpf(), request.getTel());

        return user;
    }

    public User authenticateUser(UserRequestDTO request) {
        if (!passwordMatches(request)) {
            throw new CustomException(HttpStatus.UNAUTHORIZED, "Invalid Credentials");
        }

        return loadUserByUsername(request.getUsername());
    }

    private Boolean passwordMatches(UserRequestDTO request) {
        String encodedPassword = repository.findByUsername(request.getUsername())
                .orElseThrow(() -> new CustomException(HttpStatus.UNAUTHORIZED, "Invalid Credentials"))
                .getPassword();

        return passwordEncoder.matches(request.getPassword(), encodedPassword);
    }
}
