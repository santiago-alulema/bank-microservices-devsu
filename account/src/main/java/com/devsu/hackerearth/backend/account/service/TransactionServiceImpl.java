package com.devsu.hackerearth.backend.account.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.devsu.hackerearth.backend.account.client.ClientIntegrationService;
import com.devsu.hackerearth.backend.account.client.modelClient.ClientResponseDto;
import com.devsu.hackerearth.backend.account.exception.BadRequestException;
import com.devsu.hackerearth.backend.account.exception.ResourceNotFoundException;
import com.devsu.hackerearth.backend.account.helper.ClientServiceClient;
import com.devsu.hackerearth.backend.account.mapper.TransactionMapper;
import com.devsu.hackerearth.backend.account.model.Account;
import com.devsu.hackerearth.backend.account.model.Transaction;
import com.devsu.hackerearth.backend.account.model.dto.BankStatementDto;
import com.devsu.hackerearth.backend.account.model.dto.TransactionDto;
import com.devsu.hackerearth.backend.account.repository.AccountRepository;
import com.devsu.hackerearth.backend.account.repository.TransactionRepository;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final ClientIntegrationService clientIntegrationService;
    private final ClientServiceClient clientServiceClient;

    public TransactionServiceImpl(TransactionRepository transactionRepository,
            AccountRepository accountRepository,
            ClientIntegrationService clientIntegrationService,
            ClientServiceClient clientServiceClient) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.clientIntegrationService = clientIntegrationService;
        this.clientServiceClient = clientServiceClient;
    }

    @Override
    public List<TransactionDto> getAll() {
        return transactionRepository.findAll().stream()
                .map(TransactionMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public TransactionDto getById(Long id) {
        Transaction tx = transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));

        return TransactionMapper.toDto(tx);
    }

    @Override
    public TransactionDto create(TransactionDto transactionDto) {
        if (transactionDto.getAccountId() == null) {
            throw new BadRequestException("Id de transaccion invalida");
        }

        Account account = accountRepository.findById(transactionDto.getAccountId())
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));

        double currentBalance = account.getInitialAmount();

        List<Transaction> transactions = transactionRepository
                .findByAccountIdOrderByDateDesc(account.getId());

        if (!transactions.isEmpty()) {
            currentBalance = transactions.get(0).getBalance();
        }

        double newBalance = currentBalance + transactionDto.getAmount();
        if (newBalance < 0) {
            throw new BadRequestException("Saldo no disponible");
        }

        Transaction transaction = TransactionMapper.toEntity(transactionDto);
        transaction.setBalance(newBalance);

        Transaction saved = transactionRepository.save(transaction);

        account.setInitialAmount(newBalance);
        accountRepository.save(account);

        return TransactionMapper.toDto(saved);
    }

    @Override
    public List<BankStatementDto> getAllByAccountClientIdAndDateBetween(Long clientId, Date dateTransactionStart,
            Date dateTransactionEnd) {
        List<Account> accounts = accountRepository.findAll()
                .stream()
                .filter(acc -> acc.getClientId().equals(clientId))
                .collect(Collectors.toList());

        ClientResponseDto client = clientServiceClient.getClientService(clientId);

        List<BankStatementDto> result = new ArrayList<>();
        for (Account acc : accounts) {
            List<Transaction> transactions = transactionRepository.findByAccountIdAndDateBetween(
                    acc.getId(),
                    dateTransactionStart,
                    dateTransactionEnd);

            for (Transaction tx : transactions) {
                BankStatementDto dto = new BankStatementDto(
                        tx.getDate(),
                        client.getName(),
                        acc.getNumber(),
                        acc.getType(),
                        acc.getInitialAmount(),
                        acc.isActive(),
                        tx.getType(),
                        tx.getAmount(),
                        tx.getBalance());
                result.add(dto);
            }
        }

        return result;
    }

    @Override
    public TransactionDto getLastByAccountId(Long accountId) {
        List<Transaction> transactions = transactionRepository.findByAccountIdOrderByDateDesc(accountId);

        if (transactions.isEmpty()) {
            throw new ResourceNotFoundException("Transaction not found");
        }

        Transaction tx = transactions.get(0);

        return TransactionMapper.toDto(tx);
    }

}
