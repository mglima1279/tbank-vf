package com.bank.demo.services;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.bank.demo.dto.AccountResponseDTO;
import com.bank.demo.dto.TransactionResponseDTO;
import com.bank.demo.entities.Account;
import com.bank.demo.entities.User;
import com.bank.demo.repositories.AccountRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final TransactionService transactionService;

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

        dto.setTransactions(transactionService.getMyTransactions(userId).stream()
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
}
