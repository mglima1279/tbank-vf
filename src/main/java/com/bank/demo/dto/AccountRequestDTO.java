package com.bank.demo.dto;

import com.bank.demo.entities.Account;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountRequestDTO {

    private String cpf;
    private String tel;

    public Account toEntity() {
        Account account = new Account();
        account.setCpf(this.cpf);
        account.setTel(this.tel);
        return account;
    }
}
