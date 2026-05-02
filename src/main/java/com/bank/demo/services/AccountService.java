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
}
