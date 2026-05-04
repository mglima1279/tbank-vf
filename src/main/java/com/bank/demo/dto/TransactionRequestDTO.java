package com.bank.demo.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.bank.demo.entities.Transaction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequestDTO {

    private long toAccountId;

    private BigDecimal amount;

    public Transaction toEntity() {
        Transaction transaction = new Transaction();
        transaction.setAmount(this.amount);
        transaction.setTimestamp(LocalDateTime.now());
        return transaction;
    }
}
