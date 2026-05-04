package com.bank.demo.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.demo.dto.TransactionRequestDTO;
import com.bank.demo.dto.TransactionResponseDTO;
import com.bank.demo.entities.Transaction;
import com.bank.demo.entities.User;
import com.bank.demo.services.AuthService;
import com.bank.demo.services.TransactionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;
    private final AuthService authService;

    @PostMapping
    public ResponseEntity<TransactionResponseDTO> createTransaction(@AuthenticationPrincipal UserDetails userDetails,
            @RequestBody TransactionRequestDTO request) {

        try {
            User user = authService.loadUserByUsername(userDetails.getUsername());

            Transaction transaction = transactionService.create(user.getId(), request);

            return ResponseEntity.ok(TransactionResponseDTO.fromEntity(transaction));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<TransactionResponseDTO>> getMyTransactions(
            @AuthenticationPrincipal UserDetails userDetails) {
        try {
            User user = authService.loadUserByUsername(userDetails.getUsername());

            List<Transaction> transactions = transactionService.getMyTransactions(user.getId());

            List<TransactionResponseDTO> response = transactions.stream()
                    .map(TransactionResponseDTO::fromEntity)
                    .toList();

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/by-id")
    public ResponseEntity<TransactionResponseDTO> getTransactionById(@AuthenticationPrincipal UserDetails userDetails,
            @RequestBody UUID id) {
        try {
            User user = authService.loadUserByUsername(userDetails.getUsername());
            Transaction transaction = transactionService.getByPublicId(user.getId(), id);

            return ResponseEntity.ok(TransactionResponseDTO.fromEntity(transaction));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteTransaction(@AuthenticationPrincipal UserDetails userDetails,
            @RequestBody UUID id) {
        try {
            User user = authService.loadUserByUsername(userDetails.getUsername());
            transactionService.delete(user.getId(), id);

            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
