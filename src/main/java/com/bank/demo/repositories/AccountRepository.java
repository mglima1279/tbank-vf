package com.bank.demo.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.demo.entities.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByUserId(long userId);
}
