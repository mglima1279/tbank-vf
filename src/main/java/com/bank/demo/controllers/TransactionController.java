package com.bank.demo.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.demo.dto.TransactionRequestDTO;
import com.bank.demo.dto.TransactionResponseDTO;
import com.bank.demo.entities.Transaction;
import com.bank.demo.entities.User;
import com.bank.demo.services.AccountService;
import com.bank.demo.services.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {
    private final AccountService accountService;
    private final AuthService authService;

    @PostMapping
    public ResponseEntity<TransactionResponseDTO> createTransaction(@AuthenticationPrincipal UserDetails userDetails,
            @RequestBody TransactionRequestDTO request) {

        User user = authService.loadUserByUsername(userDetails.getUsername());

        Transaction transaction = accountService.createTransaction(user.getId(), request);

        return ResponseEntity.ok(TransactionResponseDTO.fromEntity(transaction));
    }

    @GetMapping
    public ResponseEntity<?> getMyTransactions(
            @AuthenticationPrincipal UserDetails userDetails) {

        User user = authService.loadUserByUsername(userDetails.getUsername());

        List<Transaction> transactions = accountService.getMyTransactions(user.getId());

        return ResponseEntity.ok(transactions.stream()
                .map(TransactionResponseDTO::fromEntity)
                .toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionResponseDTO> getTransactionById(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable UUID id) {
        User user = authService.loadUserByUsername(userDetails.getUsername());

        Transaction transaction = accountService.getTransactionByPublicId(user.getId(), id);

        return ResponseEntity.ok(TransactionResponseDTO.fromEntity(transaction));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@AuthenticationPrincipal UserDetails userDetails,
            @PathVariable UUID id) {
        User user = authService.loadUserByUsername(userDetails.getUsername());
        accountService.deleteTransaction(user.getId(), id);

        return ResponseEntity.noContent().build();
    }
}
