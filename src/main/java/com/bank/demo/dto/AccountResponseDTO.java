package com.bank.demo.dto;

import java.math.BigDecimal;
import java.util.List;

import com.bank.demo.entities.Account;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AccountResponseDTO {
    private String username;
    private String cpf;
    private String tel;
    private BigDecimal balance;
    private List<TransactionResponseDTO> transactions;

    public static AccountResponseDTO fromEntity(Account account) {
        AccountResponseDTO dto = new AccountResponseDTO();
        dto.setCpf(account.getCpf());
        dto.setTel(account.getTel());
        dto.setBalance(account.getBalance());

        return dto;
    }
}
