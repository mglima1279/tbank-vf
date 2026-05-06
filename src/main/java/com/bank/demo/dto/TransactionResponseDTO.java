package com.bank.demo.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.bank.demo.entities.Transaction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TransactionResponseDTO {

    private UUID publicId;
    private String fromUsername;
    private String toUsername;
    private BigDecimal amount;
    private LocalDateTime timestamp;

    public static TransactionResponseDTO fromEntity(Transaction transaction) {
        TransactionResponseDTO dto = new TransactionResponseDTO();
        dto.setPublicId(transaction.getPublicId());
        dto.setFromUsername(transaction.getFromAccount().getUser().getUsername());
        dto.setToUsername(transaction.getToAccount().getUser().getUsername());
        dto.setAmount(transaction.getAmount());
        dto.setTimestamp(transaction.getTimestamp());
        return dto;
    }
}
