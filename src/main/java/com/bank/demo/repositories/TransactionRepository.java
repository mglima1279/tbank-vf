package com.bank.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.demo.entities.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findAllByFromAccountUserId(long userId);
}
