package com.bank.demo.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.bank.demo.dto.AccountResponseDTO;
import com.bank.demo.dto.TransactionRequestDTO;
import com.bank.demo.dto.TransactionResponseDTO;
import com.bank.demo.entities.Account;
import com.bank.demo.entities.Transaction;
import com.bank.demo.entities.User;
import com.bank.demo.repositories.AccountRepository;
import com.bank.demo.repositories.TransactionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public Account create(User user, String cpf, String tel) {
        Account account = new Account();
        account.setUser(user);
        account.setBalance(BigDecimal.ZERO);
        account.setCpf(cpf);
        account.setTel(tel);

        return accountRepository.save(account);
    }

    public Account read(long userId) {
        return accountRepository.findByUserId(userId).orElseThrow(() -> new RuntimeException("Account not found"));
    }

    public AccountResponseDTO readDTO(long userId, Account account) {
        AccountResponseDTO dto = AccountResponseDTO.fromEntity(account);

        dto.setTransactions(getMyTransactions(userId).stream()
                .map(TransactionResponseDTO::fromEntity)
                .toList());

        return dto;
    }

    public Boolean hasBalance(long userId, BigDecimal amount) {
        Account account = read(userId);

        return account.getBalance().compareTo(amount) >= 0;
    }

    public Account deposit(long userId, BigDecimal amount) {
        Account account = read(userId);
        account.setBalance(account.getBalance().add(amount));
        return accountRepository.save(account);
    }

    public Account withdraw(long userId, BigDecimal amount) {
        Account account = read(userId);
        if (!hasBalance(userId, amount)) {
            throw new RuntimeException("Insufficient balance");
        }
        account.setBalance(account.getBalance().subtract(amount));
        return accountRepository.save(account);
    }

    // --------------------------------------------------------------------------------

    public Transaction createTransaction(long userId, TransactionRequestDTO request) {
        if (request.getAmount().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Transaction amount must be non-negative");
        }

        Transaction transaction = request.toEntity();

        Account fromAccount = read(userId);
        Account toAccount = read(request.getToAccountId());

        if (!hasBalance(fromAccount.getId(), request.getAmount())) {
            throw new RuntimeException("Insufficient balance");
        }

        transaction.setAmount(request.getAmount());

        transaction.setFromAccount(fromAccount);
        transaction.setToAccount(toAccount);

        withdraw(fromAccount.getId(), request.getAmount());
        deposit(toAccount.getId(), request.getAmount());

        transaction.setPublicId(UUID.randomUUID());

        return transactionRepository.save(transaction);
    }

    public List<Transaction> getMyTransactions(long userId) {
        return transactionRepository.findAllByFromAccountUserId(userId);
    }

    public Transaction getTransactionById(long userId, long id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        if (transaction.getFromAccount().getUser().getId() != userId) {
            throw new RuntimeException("Unauthorized");
        }
        return transaction;
    }

    public Transaction getTransactionByPublicId(long userId, UUID publicId) {
        Transaction transaction = transactionRepository.findByPublicId(publicId)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        if (transaction.getFromAccount().getUser().getId() != userId) {
            throw new RuntimeException("Unauthorized");
        }

        return transaction;
    }

    public void deleteTransaction(long userId, UUID publicId) {
        Transaction transaction = getTransactionByPublicId(userId, publicId);

        deposit(transaction.getFromAccount().getId(), transaction.getAmount());
        withdraw(transaction.getToAccount().getId(), transaction.getAmount());

        transactionRepository.deleteById(transaction.getId());
    }
}
