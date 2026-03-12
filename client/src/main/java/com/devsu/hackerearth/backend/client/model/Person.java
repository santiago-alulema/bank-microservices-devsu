package com.devsu.hackerearth.backend.client.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public class Person extends Base {
	@Column(nullable = false)
	private String name;
	@Column(nullable = false, unique = true)
	private String dni;
	private String gender;
	private int age;
	private String address;
	private String phone;
}
