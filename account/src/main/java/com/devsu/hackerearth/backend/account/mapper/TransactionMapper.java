package com.devsu.hackerearth.backend.account.mapper;

import com.devsu.hackerearth.backend.account.model.Transaction;
import com.devsu.hackerearth.backend.account.model.dto.TransactionDto;

public class TransactionMapper {
    public static TransactionDto toDto(Transaction transaction) {
        if (transaction == null) {
            return null;
        }

        return new TransactionDto(
                transaction.getId(),
                transaction.getDate(),
                transaction.getType(),
                transaction.getAmount(),
                transaction.getBalance(),
                transaction.getAccountId());
    }

    public static Transaction toEntity(TransactionDto dto) {
        if (dto == null) {
            return null;
        }
        Transaction transaction = new Transaction();
        transaction.setId(dto.getId());
        transaction.setType(dto.getType());
        transaction.setAmount(dto.getAmount());
        transaction.setBalance(dto.getBalance());
        transaction.setAccountId(dto.getAccountId());
        return transaction;
    }
}
