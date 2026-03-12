package com.devsu.hackerearth.backend.account.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.devsu.hackerearth.backend.account.exception.BadRequestException;
import com.devsu.hackerearth.backend.account.exception.ResourceNotFoundException;
import com.devsu.hackerearth.backend.account.helper.ClientServiceClient;
import com.devsu.hackerearth.backend.account.mapper.AccountMapper;
import com.devsu.hackerearth.backend.account.model.Account;
import com.devsu.hackerearth.backend.account.model.dto.AccountDto;
import com.devsu.hackerearth.backend.account.model.dto.PartialAccountDto;
import com.devsu.hackerearth.backend.account.repository.AccountRepository;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final ClientServiceClient clientServiceClient;

    public AccountServiceImpl(AccountRepository accountRepository,
            ClientServiceClient clientServiceClient) {
        this.accountRepository = accountRepository;
        this.clientServiceClient = clientServiceClient;
    }

    @Override
    public List<AccountDto> getAll() {
        return accountRepository.findAll()
                .stream()
                .map(AccountMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public AccountDto getById(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));

        return AccountMapper.toDto(account);
    }

    @Override
    public AccountDto create(AccountDto accountDto) {
        if (accountRepository.existsByNumber(accountDto.getNumber())) {
            throw new BadRequestException("Account number already exist");
        }

        clientServiceClient.getClientService(accountDto.getClientId());

        Account account = AccountMapper.toEntity(accountDto);
        Account saved = accountRepository.save(account);
        return AccountMapper.toDto(saved);
    }

    @Override
    public AccountDto update(AccountDto accountDto) {
        Account account = accountRepository.findById(accountDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));

        account.setNumber(accountDto.getNumber());
        account.setType(accountDto.getType());
        account.setActive(accountDto.isActive());

        Account updated = accountRepository.save(account);
        return AccountMapper.toDto(updated);
    }

    @Override
    public AccountDto partialUpdate(Long id, PartialAccountDto partialAccountDto) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));
        account.setActive(partialAccountDto.isActive());

        Account updated = accountRepository.save(account);

        return AccountMapper.toDto(updated);
    }

    @Override
    public void deleteById(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));

        accountRepository.delete(account);
    }

}
