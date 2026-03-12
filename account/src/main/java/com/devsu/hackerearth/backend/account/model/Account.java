package com.devsu.hackerearth.backend.account.model;

import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.Setter;

import lombok.Getter;

@Entity
@Getter
@Setter
public class Account extends Base {

    @Column(unique = true)
    private String number;

    private String type;
    private double initialAmount;
    private boolean isActive;

    @Column(name = "client_id")
    private Long clientId;

}
