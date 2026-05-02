package com.bank.demo.services;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.bank.demo.dto.TransactionRequestDTO;
import com.bank.demo.entities.Account;
import com.bank.demo.entities.Transaction;
import com.bank.demo.repositories.TransactionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountService accountService;

    public Transaction create(TransactionRequestDTO request) {
        if (request.getAmount().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Transaction amount must be non-negative");
        }
        Transaction transaction = request.toEntity();

        Account fromAccount = accountService.read(request.getFromAccountId());

        if (!accountService.hasBalance(request.getAmount(), fromAccount.getId())) {
            throw new RuntimeException("Insufficient balance");
        }

        transaction.setFromAccount(fromAccount);
        transaction.setToAccount(accountService.read(request.getToAccountId()));
        fromAccount.setBalance(fromAccount.getBalance().subtract(request.getAmount()));

        return transactionRepository.save(transaction);
    }
}
