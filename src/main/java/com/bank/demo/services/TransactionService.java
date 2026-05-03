package com.bank.demo.services;

import java.math.BigDecimal;
import java.util.List;

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
        Account toAccount = accountService.read(request.getToAccountId());

        if (!accountService.hasBalance(fromAccount.getId(), request.getAmount())) {
            throw new RuntimeException("Insufficient balance");
        }

        transaction.setFromAccount(fromAccount);
        transaction.setToAccount(toAccount);

        accountService.withdraw(fromAccount.getId(), request.getAmount());
        accountService.deposit(toAccount.getId(), request.getAmount());

        return transactionRepository.save(transaction);
    }

    public List<Transaction> getMyTransactions(long id) {
        return transactionRepository.findAllByFromAccountUserId(id);
    }

    public Transaction get(long id) {
        return transactionRepository.findById(id).orElseThrow(() -> new RuntimeException("Transaction not found"));
    }

    public void delete(long id) {
        Transaction transaction = get(id);

        accountService.deposit(transaction.getFromAccount().getId(), transaction.getAmount());
        accountService.withdraw(transaction.getToAccount().getId(), transaction.getAmount());

        transactionRepository.deleteById(id);
    }
}
