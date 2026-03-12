package com.devsu.hackerearth.backend.account.mapper;

import com.devsu.hackerearth.backend.account.model.Account;
import com.devsu.hackerearth.backend.account.model.dto.AccountDto;

public class AccountMapper {

    public static AccountDto toDto(Account account) {
        if (account == null) {
            return null;
        }

        return new AccountDto(account.getId(),
                account.getNumber(),
                account.getType(),
                account.getInitialAmount(),
                account.isActive(),
                account.getClientId());
    }

    public static Account toEntity(AccountDto accountDto) {
        if (accountDto == null) {
            return null;
        }

        Account account = new Account();
        account.setId(accountDto.getId());
        account.setNumber(accountDto.getNumber());
        account.setType(accountDto.getType());
        account.setInitialAmount(accountDto.getInitialAmount());
        account.setActive(accountDto.isActive());
        account.setClientId(accountDto.getClientId());

        return account;
    }
}
