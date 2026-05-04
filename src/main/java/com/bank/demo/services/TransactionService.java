package com.bank.demo.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

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

    public Transaction create(long userId, TransactionRequestDTO request) {
        if (request.getAmount().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Transaction amount must be non-negative");
        }

        Transaction transaction = request.toEntity();

        Account fromAccount = accountService.read(userId);
        Account toAccount = accountService.read(request.getToAccountId());

        if (!accountService.hasBalance(fromAccount.getId(), request.getAmount())) {
            throw new RuntimeException("Insufficient balance");
        }

        transaction.setAmount(request.getAmount());

        transaction.setFromAccount(fromAccount);
        transaction.setToAccount(toAccount);

        accountService.withdraw(fromAccount.getId(), request.getAmount());
        accountService.deposit(toAccount.getId(), request.getAmount());

        transaction.setPublicId(UUID.randomUUID());

        return transactionRepository.save(transaction);
    }

    public List<Transaction> getMyTransactions(long userId) {
        return transactionRepository.findAllByFromAccountUserId(userId);
    }

    public Transaction getById(long userId, long id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        if (transaction.getFromAccount().getUser().getId() != userId) {
            throw new RuntimeException("Unauthorized");
        }
        return transaction;
    }

    public Transaction getByPublicId(long userId, UUID publicId) {
        Transaction transaction = transactionRepository.findByPublicId(publicId)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        if (transaction.getFromAccount().getUser().getId() != userId) {
            throw new RuntimeException("Unauthorized");
        }

        return transaction;
    }

    public void delete(long userId, UUID publicId) {
        Transaction transaction = getByPublicId(userId, publicId);

        accountService.deposit(transaction.getFromAccount().getId(), transaction.getAmount());
        accountService.withdraw(transaction.getToAccount().getId(), transaction.getAmount());

        transactionRepository.deleteById(transaction.getId());
    }
}
