package com.devsu.hackerearth.backend.account;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.regex.Matcher;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import com.devsu.hackerearth.backend.account.client.modelClient.ClientResponseDto;
import com.devsu.hackerearth.backend.account.controller.AccountController;
import com.devsu.hackerearth.backend.account.helper.ClientServiceClient;
import com.devsu.hackerearth.backend.account.model.Account;
import com.devsu.hackerearth.backend.account.model.Transaction;
import com.devsu.hackerearth.backend.account.model.dto.AccountDto;
import com.devsu.hackerearth.backend.account.model.dto.TransactionDto;
import com.devsu.hackerearth.backend.account.repository.AccountRepository;
import com.devsu.hackerearth.backend.account.repository.TransactionRepository;
import com.devsu.hackerearth.backend.account.service.AccountService;
import com.devsu.hackerearth.backend.account.service.TransactionServiceImpl;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.springframework.http.MediaType;

@SpringBootTest
@AutoConfigureMockMvc
public class sampleTest {

	private AccountService accountService = mock(AccountService.class);
	private AccountController accountController = new AccountController(accountService);

	@Mock
	private TransactionRepository transactionRepository;

	@Mock
	private AccountRepository accountRepository;

	@InjectMocks
	private TransactionServiceImpl transactionServiceImpl;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ClientServiceClient clientServiceClient;

	@Test
	void createTransactionTest() {
		Account account = new Account();
		account.setId(1L);
		account.setInitialAmount(100.00);
		account.setActive(true);

		TransactionDto dto = new TransactionDto();
		dto.setType("DEPOSITO");
		dto.setAmount(500.0);
		dto.setAccountId(1L);

		when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

		Transaction savedTx = new Transaction();
		savedTx.setAmount(500.0);
		savedTx.setAccountId(1L);
		savedTx.setBalance(1500.0);

		when(transactionRepository.save(org.mockito.ArgumentMatchers.any()))
				.thenReturn(savedTx);

		TransactionDto result = transactionServiceImpl.create(dto);

		assertEquals(500.0, result.getAmount());
		assertEquals(1500, result.getBalance());

	}

	@Test
	void createAccountTest() {
		AccountDto newAccount = new AccountDto(1L,
				"98751",
				"AHORRO",
				1500.0,
				true,
				1L);
		AccountDto createdAccount = new AccountDto(
				1L,
				"98751",
				"AHORRO",
				1500.0,
				true,
				1L);
		when(accountService.create(newAccount)).thenReturn(createdAccount);

		ResponseEntity<AccountDto> response = accountController.create(newAccount);

		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(createdAccount, response.getBody());
	}

	@Test
	void createAccountIntegration() throws Exception {

		ClientResponseDto client = new ClientResponseDto();
		client.setId(1L);
		client.setName("Client test");

		when(clientServiceClient.getClientService(1L)).thenReturn(client);

		String json = "{"
				+ "\"number\": \"987654\","
				+ "\"type\": \"AHORRO\","
				+ "\"initialAmount\": 1500,"
				+ "\"active\": true,"
				+ "\"clientId\": 1"
				+ "}";

		mockMvc.perform(post("/api/accounts")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.number").value("987654"))
				.andExpect(jsonPath("$.type").value("AHORRO"));
	}
}
