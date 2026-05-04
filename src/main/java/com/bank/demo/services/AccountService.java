package com.bank.demo.services;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.bank.demo.entities.Account;
import com.bank.demo.entities.User;
import com.bank.demo.repositories.AccountRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    public Account create(User user, String cpf, String tel) {
        Account account = new Account();
        account.setUser(user);
        account.setBalance(BigDecimal.ZERO);
        account.setCpf(cpf);
        account.setTel(tel);

        return accountRepository.save(account);
    }

    public Account read(long id) {
        return accountRepository.findById(id).orElseThrow(() -> new RuntimeException("Account not found"));
    }

    public Boolean hasBalance(long id, BigDecimal amount) {
        Account account = read(id);

        return account.getBalance().compareTo(amount) >= 0;
    }

    public Account deposit(long id, BigDecimal amount) {
        Account account = read(id);
        account.setBalance(account.getBalance().add(amount));
        return accountRepository.save(account);
    }

    public Account withdraw(long id, BigDecimal amount) {
        Account account = read(id);
        if (!hasBalance(id, amount)) {
            throw new RuntimeException("Insufficient balance");
        }
        account.setBalance(account.getBalance().subtract(amount));
        return accountRepository.save(account);
    }
}
