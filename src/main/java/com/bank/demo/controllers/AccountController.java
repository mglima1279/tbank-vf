package com.bank.demo.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.demo.dto.AccountResponseDTO;
import com.bank.demo.entities.User;
import com.bank.demo.services.AccountService;
import com.bank.demo.services.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/me")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final AuthService authService;

    @GetMapping
    public ResponseEntity<AccountResponseDTO> getMyAccount(@AuthenticationPrincipal UserDetails userDetails) {
        try {
            User user = authService.loadUserByUsername(userDetails.getUsername());
            AccountResponseDTO dto = accountService.readDTO(user.getId(), accountService.read(user.getId()));
            dto.setUsername(userDetails.getUsername());

            return ResponseEntity.ok(dto);

        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
