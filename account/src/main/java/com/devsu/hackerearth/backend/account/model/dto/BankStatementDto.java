package com.devsu.hackerearth.backend.account.model.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
@AllArgsConstructor
public class BankStatementDto {

	private Date date;
	private String client;
	private String accountNumber;
	private String accountType;
	private double initialAmount;
	private boolean isActive;
	private String transactionType;
	private double amount;
	private double balance;
}
